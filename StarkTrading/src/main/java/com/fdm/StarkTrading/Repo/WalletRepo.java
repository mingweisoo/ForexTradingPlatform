package com.fdm.StarkTrading.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.StarkTrading.model.Account;
import com.fdm.StarkTrading.model.Wallet;

@Repository
public interface WalletRepo extends JpaRepository<Wallet, Long> {
	
	@Query("SELECT e FROM Wallet e WHERE e.account = :account")
	List<Wallet> findWalletByAccount(@Param("account")Account account);
    
    @Query("SELECT e FROM Wallet e WHERE e.account = :account AND e.currency = :currency")
    Wallet getWallet(Account account, String currency);

}
