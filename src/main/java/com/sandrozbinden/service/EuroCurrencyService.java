package com.sandrozbinden.service;

import java.util.Set;

import org.joda.time.LocalDate;

import com.sandrozbinden.entity.Currency;
import com.sandrozbinden.entity.Rate;

public interface EuroCurrencyService {

	Set<Rate> getRates();

	Currency getRates(LocalDate date);

}
