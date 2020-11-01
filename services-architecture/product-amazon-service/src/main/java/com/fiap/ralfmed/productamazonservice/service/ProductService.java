package com.fiap.ralfmed.productamazonservice.service;

import com.fiap.ralfmed.productamazonservice.dto.ProductDTO;
import com.fiap.ralfmed.productamazonservice.dto.ProductWishListDTO;
import com.fiap.ralfmed.productamazonservice.entity.Product;

import java.util.List;

public interface ProductService {

    Product create(ProductDTO productDTO);

    Product updatePrice(Long id, Float price);

    Product updateWishList(Long id, Boolean wishLish);

    ProductDTO findById(Long id);

    List<ProductDTO> findByGender(String genre);

    List<ProductDTO> listProductContainsName(String name);

    List<ProductDTO> findByMostViewed(Long numero);

    List<ProductDTO> getWishList();
}