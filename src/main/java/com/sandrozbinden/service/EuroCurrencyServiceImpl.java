package com.sandrozbinden.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.jdom2.Document;
import org.jdom2.Element;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sandrozbinden.entity.CurrencyConversionInfo;
import com.sandrozbinden.entity.Rate;

@Component
public class EuroCurrencyServiceImpl implements EuroCurrencyService {

	@Value("${rateservice.ecb.daily.url}")
	private String ecbDailyURL;

	@Value("${rateservice.ecb.history.url}")
	private String ecbHistoryURL;

	@Autowired
	private JDomService jDomService;

	private final Logger logger = LoggerFactory.getLogger(EuroCurrencyServiceImpl.class);

	private CopyOnWriteArraySet<Rate> rates = new CopyOnWriteArraySet<>();

	@Override
	public CurrencyConversionInfo getRates(LocalDate date) {
		List<Rate> filterdRates = rates.stream().filter(r -> r.getDate().toDateTimeAtStartOfDay().isEqual(date.toDateTimeAtStartOfDay())).sorted().collect(Collectors.toList());
		return new CurrencyConversionInfo("EUR", DateTimeFormat.forPattern("yyyy-MM-dd").print(date), filterdRates);
	}

	@PostConstruct
	private void initHistoryRates() throws IOException {
		addRatesFromDocument(jDomService.loadDocument(new URL(ecbHistoryURL)));
	}

	@Scheduled(fixedDelay = 10000)
	private void refreshDailyRates() {
		try {
			addRatesFromDocument(jDomService.loadDocument(new URL(ecbDailyURL)));
		} catch (IOException e) {
			logger.error("Can't get current rates", e);
		}
	}

	private void addRatesFromDocument(Document document) {
		for (Element timeElemnt : getCubeRootElement(document.getRootElement()).getChildren()) {
			for (Element rateElement : timeElemnt.getChildren()) {
				Rate rate = createRate(timeElemnt, rateElement);
				rates.add(rate);
				if (rate.getDate().getDayOfWeek() == DateTimeConstants.FRIDAY) {
					rates.add(new Rate(rate.getCurrency(), rate.getRate(), rate.getDate().plusDays(1)));
					rates.add(new Rate(rate.getCurrency(), rate.getRate(), rate.getDate().plusDays(2)));
				}
			}
		}
	}

	private Rate createRate(Element timeCubeElement, Element cubeRateElement) {
		LocalDate date = getDate(timeCubeElement.getAttribute("time").getValue());
		String currency = cubeRateElement.getAttribute("currency").getValue();
		double rate = Double.parseDouble(cubeRateElement.getAttribute("rate").getValue());
		return new Rate(currency, rate, date);
	}

	private Element getCubeRootElement(Element rootElement) {
		return rootElement.getChildren().stream().filter(e -> e.getName().equalsIgnoreCase("Cube")).findFirst().get();
	}

	private LocalDate getDate(String date) {
		return DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(date);
	}

}
