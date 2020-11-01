package com.fiap.ralfmed.orderamazonservice.repository;

import com.fiap.ralfmed.orderamazonservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
