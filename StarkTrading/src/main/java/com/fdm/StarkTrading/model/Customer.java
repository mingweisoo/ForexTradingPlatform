package com.fdm.StarkTrading.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope("prototype")
public class Customer {
	@Id
	@SequenceGenerator(name = "CUSTOMER_SEQ_GNTR", sequenceName = "CUSTOMER_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GNTR")
	private long customerId;
	private String customerUsername;
	@Length(min = 7, message = "Password entered must be at least 7 characters")
	private String customerPassword;
	private String customerConfirmPassword;
	private String customerFirstName;
	private String customerLastName;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FK_ACCOUNT_ID")
	private Account account;

	public Customer() {
		super();
	}
	
	public Customer(long customerId, String customerUsername,
			String customerPassword,String customerConfirmPassword,String customerFirstName,String customerLastName, Account account) {
		super();
		this.customerId = customerId;
		this.customerUsername = customerUsername;
		this.customerPassword = customerPassword;
		this.customerConfirmPassword = customerConfirmPassword;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.account = account;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerUsername() {
		return customerUsername;
	}

	public void setCustomerUsername(String customerUsername) {
		this.customerUsername = customerUsername;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	
	public String getCustomerConfirmPassword() {
		return customerConfirmPassword;
	}

	public void setCustomerConfirmPassword(String customerConfirmPassword) {
		this.customerConfirmPassword = customerConfirmPassword;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerUsername=" + customerUsername + ", customerPassword="
				+ customerPassword + ", customerConfirmPassword=" + customerConfirmPassword + ", customerFirstName="
				+ customerFirstName + ", customerLastName=" + customerLastName + ", account=" + account + "]";
	}


}

