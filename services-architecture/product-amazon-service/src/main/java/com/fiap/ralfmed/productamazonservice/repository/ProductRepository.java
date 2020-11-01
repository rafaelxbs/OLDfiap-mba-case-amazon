package com.fiap.ralfmed.productamazonservice.repository;

import com.fiap.ralfmed.productamazonservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByGenre(String genre);

    List<Product> findByNameContains(String name);

    List<Product> findByMostViewedGreaterThan(Long number);

    List<Product> findByWishList(Boolean wish);
}