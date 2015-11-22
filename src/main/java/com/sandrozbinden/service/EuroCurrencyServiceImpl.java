package com.sandrozbinden.service;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.sandrozbinden.entity.Rate;

@Component
public class EuroCurrencyServiceImpl implements EuroCurrencyService {

	@Override
	public Set<Rate> getRates() {
		return loadRates();
	}

	private Set<Rate> loadRates() {
		Set<Rate> rates = new HashSet<>();
		Element rootElement = getDocument().getRootElement();
		for (Element timeCubeElement : getCubeRootElement(rootElement).getChildren()) {
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
		return rates;
	}

	private LocalDate getDate(String date) {
		return DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(date);
	}

	private Element getCubeRootElement(Element rootElement) {
		return rootElement.getChildren().stream().filter(e -> e.getName().equalsIgnoreCase("Cube")).findFirst().get();
	}

	private Document getDocument() {
		String url = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-hist-90d.xml";
		try {
			SAXBuilder jdomBuilder = new SAXBuilder();
			return jdomBuilder.build(new URL(url));
		} catch (JDOMException | IOException e) {
			throw new RuntimeException("Can't get rates from url: " + url, e);
		}
	}

}
