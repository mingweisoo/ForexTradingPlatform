package com.fdm.StarkTrading.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope("prototype")
public class Wallet {

	@Id
	@SequenceGenerator(name = "WALLET_SEQ_GNTR", sequenceName = "WALLET_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WALLET_SEQ_GNTR")
	private long walletId;
	private String currency;
	private double amount;

	@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name = "FK_Account_ID")
	private Account account;

	public Wallet() {
		super();

	}

	public Wallet(long walletId, Account account, String currency, double amount) {
		super();
		this.walletId = walletId;
		this.account = account;
		this.currency = currency;
		this.amount = amount;
	}

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
