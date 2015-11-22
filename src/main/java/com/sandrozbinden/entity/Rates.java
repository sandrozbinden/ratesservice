package com.sandrozbinden.entity;

import java.util.ArrayList;
import java.util.List;

public class Rates {

	private List<Rate> rates = new ArrayList<>();

	private String baseCurrency;

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void add(Rate rate) {
		rates.add(rate);
	}

}
