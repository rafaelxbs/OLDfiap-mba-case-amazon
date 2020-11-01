package com.fiap.ralfmed.orderamazonservice.service;

import com.fiap.ralfmed.orderamazonservice.entity.Order;
import com.fiap.ralfmed.orderamazonservice.entity.Product;

import java.util.List;

public interface OrderService {

	Order createOrder(Order order);

	void consumerProductEvent(Product event);

	Order findById(Long id);

	List<Order> getListOrder();

	Order calculateDeliveryPrice(Long id, Double distance);

}
