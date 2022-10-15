package com.fdm.StarkTrading.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity(name = "Transaction")
@Component
@Scope("prototype")
public class Order {
	@Id
	@SequenceGenerator(name = "ORDER_SEQ_GNTR", sequenceName = "ORDER_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_SEQ_GNTR")
	private long orderId;
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	private String fromCurrency;
	private String toCurrency;
	private int volume;
	private float orderRate;
	private LocalDateTime expiryDate;
	@ManyToOne
	@JoinColumn(name = "FK_ACCOUNT_ID")
	private Account account;
	@Enumerated(EnumType.STRING)
	private StatusType status;

	public Order() {
		super();
	}

	public Order(long orderId, OrderType orderType, String fromCurrency, String toCurrency, int volume, float orderRate,
			LocalDateTime expiryDate, Account account, StatusType status) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.volume = volume;
		this.orderRate = orderRate;
		this.expiryDate = expiryDate;
		this.account = account;
		this.status = status;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;

	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;

	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public float getOrderRate() {
		return orderRate;
	}

	public void setOrderRate(float orderRate) {
		this.orderRate = orderRate;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

}
