package com.fdm.StarkTrading.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.StarkTrading.model.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
	
}
