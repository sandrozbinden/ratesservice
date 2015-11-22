package com.sandrozbinden.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;

public class Rate {

	private LocalDate date;
	private String currency;
	private double rate;

	public Rate(String currency, double rate, LocalDate date) {
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

	public LocalDate getDate() {
		return date;
	}

	@Override
	public boolean equals(Object rhs) {
		return EqualsBuilder.reflectionEquals(this, rhs);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
