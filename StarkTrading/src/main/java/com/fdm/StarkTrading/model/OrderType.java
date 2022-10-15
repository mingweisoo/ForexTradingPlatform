package com.fdm.StarkTrading.model;

public enum OrderType {
	
	spotBuy("buy"),
	spotSell("sell"),
	limitBuy("buy"),
	limitSell("sell");

	private String side;
	
	OrderType(String side) {
		this.side=side;
	}
	
	public String getSide() {
		return side;
	}
	
}
