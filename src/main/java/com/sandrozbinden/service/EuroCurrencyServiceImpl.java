package com.sandrozbinden.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sandrozbinden.entity.Currency;
import com.sandrozbinden.entity.Rate;

@Component
public class EuroCurrencyServiceImpl implements EuroCurrencyService {

	private static final Logger logger = LoggerFactory.getLogger(EuroCurrencyServiceImpl.class);
	private CopyOnWriteArraySet<Rate> rates = new CopyOnWriteArraySet<>();

	@Override
	public Currency getRates(LocalDate date) {
		List<Rate> filterdRates = rates.stream().filter(r -> r.getDate().toDateTimeAtStartOfDay().isEqual(date.toDateTimeAtStartOfDay())).collect(Collectors.toList());
		return new Currency("EUR", DateTimeFormat.forPattern("yyyy-MM-dd").print(date), filterdRates);
	}

	@Override
	public Set<Rate> getRates() {
		return rates;
	}

	@PostConstruct
	public void initHistoryRates() {
		addRatesFromDocument(getHistoryRatesDocument());
	}

	@Scheduled(fixedDelay = 10000)
	public void refreshDailyRates() {
		try {
			addRatesFromDocument(getCurrentRatesDocument());
		} catch (IOException e) {
			logger.error("Can't get current rates", e);
		}
	}

	private void addRatesFromDocument(Document document) {
		for (Element timeCubeElement : getCubeRootElement(document.getRootElement()).getChildren()) {
			LocalDate date = getDate(timeCubeElement.getAttribute("time").getValue());
			for (Element cubeRateElement : timeCubeElement.getChildren()) {
				String currency = cubeRateElement.getAttribute("currency").getValue();
				double rate = Double.parseDouble(cubeRateElement.getAttribute("rate").getValue());
				rates.add(new Rate(currency, rate, date));
				if (date.getDayOfWeek() == DateTimeConstants.FRIDAY) {
					rates.add(new Rate(currency, rate, date.plusDays(1)));
					rates.add(new Rate(currency, rate, date.plusDays(2)));
				}
			}
		}
	}

	private LocalDate getDate(String date) {
		return DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(date);
	}

	private Element getCubeRootElement(Element rootElement) {
		return rootElement.getChildren().stream().filter(e -> e.getName().equalsIgnoreCase("Cube")).findFirst().get();
	}

	private Document getHistoryRatesDocument() {
		try {
			String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
			return getDocument(url);
		} catch (IOException e) {
			throw new RuntimeException("Can't get history rates", e);
		}
	}

	private Document getCurrentRatesDocument() throws IOException {
		String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
		return getDocument(url);
	}

	private Document getDocument(String url) throws IOException {
		SAXBuilder jdomBuilder = new SAXBuilder();
		try {
			return jdomBuilder.build(new URL(url));
		} catch (JDOMException e) {
			throw new RuntimeException("Can't create document", e);
		}
	}

}
