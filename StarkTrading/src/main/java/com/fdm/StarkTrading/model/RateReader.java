package com.fdm.StarkTrading.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RateReader {

	private Map<String, Float> rates;

	public RateReader() {
		super();

	}

	public Map<String, Float> getRates() {
		return rates;
	}

	public void setRates(Map<String, Float> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "RateReader [rates=" + rates + "]";
	}

}
