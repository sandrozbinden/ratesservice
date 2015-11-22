package com.sandrozbinden.entity;

import java.util.List;

public class CurrencyConversionInfo {

	private List<Rate> conversionRates;
	private String currencyName;
	private String conversionDate;

	public CurrencyConversionInfo(String currencyName, String conversionDate, List<Rate> conversionRates) {
		this.currencyName = currencyName;
		this.conversionDate = conversionDate;
		this.conversionRates = conversionRates;
	}

	public String getBase() {
		return currencyName;
	}

	public String getDate() {
		return conversionDate;
	}

	public List<Rate> getRates() {
		return conversionRates;
	}

}
