package com.sandrozbinden.entity;

import java.util.List;

public class Currency {

	private String base;
	private String date;
	private List<Rate> rates;

	public Currency(String base, String date, List<Rate> rates) {
		this.base = base;
		this.date = date;
		this.rates = rates;
	}

	public String getBase() {
		return base;
	}

	public String getDate() {
		return date;
	}

	public List<Rate> getRates() {
		return rates;
	}

}
