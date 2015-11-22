package com.sandrozbinden.service;

import org.joda.time.LocalDate;

import com.sandrozbinden.entity.CurrencyConversionInfo;

public interface EuroCurrencyService {

	CurrencyConversionInfo getRates(LocalDate date);

}
