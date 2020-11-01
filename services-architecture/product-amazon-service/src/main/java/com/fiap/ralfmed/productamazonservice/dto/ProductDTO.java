package com.fiap.ralfmed.productamazonservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.ralfmed.productamazonservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String name;

    private String category;

    private String genre;

    private Float price;

    private String description;

    @JsonIgnore
    private Boolean wishList;

    public ProductDTO(String name, String category, String genre){
        this.name = name;
        this.category = category;
        this.genre = genre;
    }

    public static ProductDTO convertProductDto(Product product){
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getCategory(), product.getGenre(), product.getPrice(), product.getDescription(), product.getWishList());
        return productDTO;
    }
}