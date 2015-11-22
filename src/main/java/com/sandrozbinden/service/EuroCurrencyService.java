package com.sandrozbinden.service;

import org.joda.time.LocalDate;

import com.sandrozbinden.entity.Currency;

public interface EuroCurrencyService {

	Currency getRates(LocalDate date);

}
