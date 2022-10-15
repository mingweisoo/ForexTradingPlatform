package com.fdm.StarkTrading.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.StarkTrading.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
	
	Account findAccountByAccountNumber(long accountNumber);

}
