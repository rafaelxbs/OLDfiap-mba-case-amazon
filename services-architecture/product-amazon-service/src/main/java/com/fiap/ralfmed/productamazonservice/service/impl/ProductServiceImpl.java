package com.fiap.ralfmed.productamazonservice.service.impl;

import com.fiap.ralfmed.productamazonservice.dto.ProductDTO;
import com.fiap.ralfmed.productamazonservice.entity.Product;
import com.fiap.ralfmed.productamazonservice.repository.ProductRepository;
import com.fiap.ralfmed.productamazonservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableBinding(Source.class)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private Source source;

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product create(ProductDTO productDTO) {
        return productRepository.save(Product.convertToProduct(productDTO));
    }

    @Override
    public Product updatePrice(Long id, Float price) {
        Product product = (Product) productRepository.findById(id).get();
        product.setPrice(price);
        source.output().send(MessageBuilder.withPayload(product).build());
        return productRepository.save(product);
    }

    @Override
    public Product updateWishList(Long id, Boolean wishLish) {
        Product product = (Product) productRepository.findById(id).get();
        product.setWishList(wishLish);
        return productRepository.save(product);
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = (Product) productRepository.findById(id).get();
        productRepository.save(product);
        return ProductDTO.convertProductDto(product);
    }

    @Override
    public List<ProductDTO> getWishList() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productRepository.findByWishList(Boolean.TRUE)){
            returnListProductDto(product, productDTOList);
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findByGender(String genre) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productRepository.findByGenre(genre)){
            returnListProductDto(product, productDTOList);
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> listProductContainsName(String name) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productRepository.findByNameContains(name)){
            returnListProductDto(product, productDTOList);
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> findByMostViewed(Long number) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productRepository.findByMostViewedGreaterThan(number)){
            returnListProductDto(product, productDTOList);
        }
        return productDTOList;
    }

    private List<ProductDTO> returnListProductDto(Product product, List<ProductDTO> productDTOList){
        ProductDTO productDTO = new ProductDTO(product.getName(), product.getCategory(), product.getGenre(), product.getPrice(), product.getDescription(), product.getWishList());
        productDTOList.add(productDTO);
        return productDTOList;
    }
}