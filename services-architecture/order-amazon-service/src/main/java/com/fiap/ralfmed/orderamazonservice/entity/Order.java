package com.fiap.ralfmed.orderamazonservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchase_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double totalOrder;

	private String status;

	private double deliveryPrice;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order_id", fetch=FetchType.LAZY)
	private List<OrderLine> products = new ArrayList<>();
}
