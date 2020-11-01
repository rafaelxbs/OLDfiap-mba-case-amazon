package com.fiap.ralfmed.orderamazonservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

	@Id
	private Integer id;

	private double price;

	private String description;

	private int quantity;
}

