package com.fiap.ralfmed.orderamazonservice.controller;

import com.fiap.ralfmed.orderamazonservice.entity.Order;
import com.fiap.ralfmed.orderamazonservice.entity.ResponseOrder;
import com.fiap.ralfmed.orderamazonservice.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderServiceController {

	private OrderService orderService;

	public OrderServiceController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@HystrixCommand(fallbackMethod  = "singleOrderFallback")
	public String createOrder(@RequestBody Order order) {
		Order createOrder = orderService.createOrder(order); // item 22.b
		return ResponseOrder.convert(order);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Order findById(@PathVariable(name = "id") Long id){
		return orderService.findById(id);
	}

	@GetMapping("/getListOrder")
	@ResponseStatus(HttpStatus.OK)
	public List<Order> getListOrder(){
		return orderService.getListOrder();
	}

	@GetMapping("/calculateDeliveryPrice/{id}/{distance}")
	@ResponseStatus(HttpStatus.OK)
	public Order calculateDeliveryPrice(@PathVariable(name = "id") Long id, @PathVariable(name = "distance") Double distance){
		return orderService.calculateDeliveryPrice(id, distance);
	}

	public Order singleOrderFallback(Order order) {
		return order;
	}
}
