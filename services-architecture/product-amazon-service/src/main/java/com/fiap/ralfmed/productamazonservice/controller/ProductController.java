package com.fiap.ralfmed.productamazonservice.controller;

import com.fiap.ralfmed.productamazonservice.dto.ProductDTO;
import com.fiap.ralfmed.productamazonservice.dto.ProductPriceDTO;
import com.fiap.ralfmed.productamazonservice.dto.ProductWishListDTO;
import com.fiap.ralfmed.productamazonservice.entity.Product;
import com.fiap.ralfmed.productamazonservice.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @HystrixCommand(fallbackMethod  = "singleProductFallback",
            commandProperties=
                    {@HystrixProperty(
                            name="execution.isolation.thread.timeoutInMilliseconds",value="20000")})
    public Product createProduct(@RequestBody ProductDTO productDTO){
        return productService.create(productDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "singleProductFallback",
            commandProperties=
                    {@HystrixProperty(
                            name="execution.isolation.thread.timeoutInMilliseconds",value="20000")})
    public ProductDTO findById(@PathVariable(name = "id") Long id) {
        return productService.findById(id);
    }

    @GetMapping("/wishList")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "singleProductFallback",
            commandProperties=
                    {@HystrixProperty(
                            name="execution.isolation.thread.timeoutInMilliseconds",value="20000")})
    public List<ProductDTO> getWishList() {
        return productService.getWishList();
    }

    @GetMapping("/findByGenre")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "listFallbackReturn")
    public List<ProductDTO> findByGenre(@RequestParam String genre){
        return productService.findByGender(genre);
    }

    @GetMapping("/findByKeyWord")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "listFallbackReturn")
    public List<ProductDTO> findByKeyWord(@RequestParam String keyword){
        return productService.listProductContainsName(keyword);
    }

    @GetMapping("/findByMostViewed")
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod  = "listFallbackReturn")
    public List<ProductDTO> findByMostViewed(@RequestParam Long number){
        return productService.findByMostViewed(number);
    }

    @PutMapping("/updatePrice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @HystrixCommand(fallbackMethod  = "singleProductFallback",commandProperties=
            {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="20000")})
    public Product updatePrice(@PathVariable(name = "id") Long id, @RequestBody ProductPriceDTO price){
        return productService.updatePrice(id, price.getPrice());
    }

    @PutMapping("/wishList/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @HystrixCommand(fallbackMethod  = "singleProductFallback",commandProperties=
            {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="20000")})
    public Product wishList(@PathVariable(name = "id") Long id, @RequestBody ProductWishListDTO wishList){
        return productService.updateWishList(id, wishList.getWishList());
    }

    public Product singleProductFallback() {
        ProductDTO productDTO = new ProductDTO("Erro ao cadastrar.", "Inválido", "Tente novamente mais tarde ou contate o administrador do sistema.");
        return new Product(productDTO);
    }

    public List<ProductDTO> listFallbackReturn(){
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(new ProductDTO("Erro ao solicitar informação.", "Inválido", "Tente novamente mais tarde ou contate o administrador do sistema."));
        return productDTOList;
    }
}