package com.fiap.ralfmed.productamazonservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fiap.ralfmed.productamazonservice.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private String genre;

    private Float price;

    private String description;

    @JsonIgnore
    private Boolean wishList;

    @JsonInclude(Include.NON_NULL)
    private Long mostViewed;

    public Product(ProductDTO productDTO){
        this.name = productDTO.getName();
        this.category = productDTO.getCategory();
        this.genre = productDTO.getGenre();
    }


    public static Product convertToProduct(ProductDTO productDTO){
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setGenre(productDTO.getGenre());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setMostViewed(0L);
        product.setWishList(Boolean.FALSE);
        return product;
    }
}