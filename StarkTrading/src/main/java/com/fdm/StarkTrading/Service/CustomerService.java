package com.fdm.StarkTrading.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.StarkTrading.Repo.CustomerRepo;
import com.fdm.StarkTrading.model.Customer;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	public boolean verifyUser(String username, String pw) {
		Optional<Customer> userOptional = customerRepo.findByCustomerUsername(username);
		if (userOptional.isPresent()) {
			return userOptional.get().getCustomerPassword().equals(pw);
		}
		return false;
	}

	public Customer retrieveCustomer(String username) {
		return customerRepo.findByCustomerUsername(username).get();

	}

	public void registerCustomer(Customer customer) {
		customerRepo.save(customer);
	}

	public boolean customerExists(String username) {
		Optional<Customer> customerOptional = customerRepo.findByCustomerUsername(username);
		return customerOptional.isPresent();
	}

}

