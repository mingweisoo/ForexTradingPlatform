package com.fdm.StarkTrading.model;

public enum StatusType {
	
	success(1,"success"),
	pending(0,"pending"),
	expired(2,"expired");

	private int rank;
	private String status;
	
	StatusType(int rank, String status) {
		this.rank = rank;
		this.status = status;
	}
	
	public int getRank() {
		return rank;
	}
	
	public String getStatus() {
		return status;
	}
	
}
