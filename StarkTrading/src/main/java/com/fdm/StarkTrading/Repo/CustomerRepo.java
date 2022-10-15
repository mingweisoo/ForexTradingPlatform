package com.fdm.StarkTrading.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.StarkTrading.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

	Optional<Customer> findByCustomerUsername(String username);

//	@Query("SELECT e FROM Customer e WHERE e.account = :account")
//	Account findAccountByCustomer(@Param("account")Account account);

}
