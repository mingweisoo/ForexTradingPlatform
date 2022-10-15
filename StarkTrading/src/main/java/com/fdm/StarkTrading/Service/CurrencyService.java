package com.fdm.StarkTrading.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdm.StarkTrading.model.Order;
import com.fdm.StarkTrading.model.OrderType;
import com.fdm.StarkTrading.model.RateReader;

import org.springframework.stereotype.Service;

@Service
public class CurrencyService {

	public Map<String, Float> getAllRates(String baseCurrency) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://open.er-api.com/v6/latest/" + baseCurrency))
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper objectMapper = new ObjectMapper();

		RateReader rateReader = objectMapper.readValue(response.body(), RateReader.class);
		Map<String, Float> rateRead = rateReader.getRates();

		return rateRead;

	}

	public float getRate(String baseCurrency, String targetCurrency) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://open.er-api.com/v6/latest/" + baseCurrency))
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		ObjectMapper objectMapper = new ObjectMapper();

		RateReader rateReader = objectMapper.readValue(response.body(), RateReader.class);
		Map<String, Float> rateRead = rateReader.getRates();

		return rateRead.get(targetCurrency);

	}
	
	public void setOrderRate(Order order) throws IOException, InterruptedException {
		
		OrderType type = order.getOrderType();
		float orderRate = order.getOrderRate();
		String fromCurrency = order.getFromCurrency();
		String toCurrency = order.getToCurrency();
		
	    switch (type) {
		    case spotBuy:
		    	CurrencyService currencyService = new CurrencyService();
		        orderRate = currencyService.getRate(fromCurrency, toCurrency);
		        break;
		    case spotSell:
		    	CurrencyService currencyService2 = new CurrencyService();
		    	orderRate = currencyService2.getRate(fromCurrency, toCurrency);
		        break;
		    case limitBuy:
		        break;
		    case limitSell:
		        break;
	    }
	    
	    order.setOrderRate(orderRate);
	    
	}
    
}
