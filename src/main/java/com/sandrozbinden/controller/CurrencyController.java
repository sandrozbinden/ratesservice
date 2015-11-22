package com.sandrozbinden.controller;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandrozbinden.entity.Currency;
import com.sandrozbinden.service.EuroCurrencyService;

@RestController
public class CurrencyController {

	@Autowired
	private EuroCurrencyService euroCurrencyService;

	@RequestMapping("/api/rates/eur")
	public Currency getCurrency() {
		return euroCurrencyService.getRates(LocalDate.now());
	}

}
