package com.sandrozbinden.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sandrozbinden.RatesserviceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RatesserviceApplication.class)
public class EuroCurrencyServiceImplTest {

	@Autowired
	private EuroCurrencyService rateSerivce;

	@Test
	@Ignore
	public void test90USDRates() {
		assertThat(rateSerivce.getRates().stream().filter(r -> r.getCurrency().equalsIgnoreCase("USD")).count(), is(90));
	}

}
