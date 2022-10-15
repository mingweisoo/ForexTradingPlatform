package com.fdm.StarkTrading.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope("prototype")
public class Account {
	@Id
	@SequenceGenerator(name = "ACCOUNT_SEQ_GNTR", sequenceName = "ACCOUNT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GNTR")
	private long accountNumber;

	public Account() {
		super();
	}

	public long getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}



}

