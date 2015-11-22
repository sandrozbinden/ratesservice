package com.sandrozbinden.controller;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandrozbinden.entity.CurrencyConversionInfo;
import com.sandrozbinden.service.EcbEuroCurrencyService;

@RestController
public class CurrencyInfoController {

	@Autowired
	private EcbEuroCurrencyService euroCurrencyService;

	@RequestMapping("/api/rates/eur")
	public CurrencyConversionInfo getCurrency() {
		return euroCurrencyService.getRates(LocalDate.now());
	}

	@RequestMapping("/api/rates/history/eur/{date}")
	public CurrencyConversionInfo getCurrency(@PathVariable String date) {
		return euroCurrencyService.getRates(DateTimeFormat.forPattern("yyyy-MM-dd").parseLocalDate(date));
	}
}
