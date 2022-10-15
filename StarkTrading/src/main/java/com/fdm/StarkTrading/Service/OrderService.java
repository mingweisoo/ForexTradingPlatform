package com.fdm.StarkTrading.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.StarkTrading.Repo.OrderRepo;
import com.fdm.StarkTrading.model.Order;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	public List<Order> getOrderList(){
		return orderRepo.findAll();
	}

	public void saveOrder(Order order) {
		orderRepo.save(order);
	}

	public Order getOrder(long OrderId) {
		return orderRepo.findById(OrderId).get();
	}

	public void updateOrder(Order order) {
		orderRepo.saveAndFlush(order);
	}

}
