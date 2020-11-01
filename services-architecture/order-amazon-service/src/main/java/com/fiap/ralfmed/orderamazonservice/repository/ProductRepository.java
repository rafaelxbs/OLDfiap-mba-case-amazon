package com.fiap.ralfmed.orderamazonservice.repository;

import com.fiap.ralfmed.orderamazonservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

