package com.sandrozbinden.entity;

public class Rate {

	private String date;
	private String currency;
	private double rate;

	public Rate(String currency, double rate, String date) {
		this.currency = currency;
		this.rate = rate;
		this.date = date;
	}

	public String getCurrency() {
		return currency;
	}

	public double getRate() {
		return rate;
	}

	public String getDate() {
		return date;
	}

}
