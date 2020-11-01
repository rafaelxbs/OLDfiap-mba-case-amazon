package com.fiap.ralfmed.orderamazonservice.service.impl;

import com.fiap.ralfmed.orderamazonservice.cache.MySimpleCache;
import com.fiap.ralfmed.orderamazonservice.entity.Order;
import com.fiap.ralfmed.orderamazonservice.entity.OrderLine;
import com.fiap.ralfmed.orderamazonservice.entity.Product;
import com.fiap.ralfmed.orderamazonservice.repository.OrderRepository;
import com.fiap.ralfmed.orderamazonservice.repository.ProductRepository;
import com.fiap.ralfmed.orderamazonservice.service.OrderService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@EnableBinding(Sink.class)
public class OrderServiceImpl implements OrderService {

    private DiscoveryClient discoveryClient;

    private OrderRepository orderRepository;

    private ProductRepository productRepository;

    public OrderServiceImpl(DiscoveryClient discoveryClient, OrderRepository orderRepository, ProductRepository productRepository) {
        this.discoveryClient = discoveryClient;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order createOrder(Order order) {
        OrderLine orderLineNew = new OrderLine();
        Product product = new Product();
        List<OrderLine> products = new ArrayList<>();

        for(OrderLine orderLine: order.getProducts()){

            Product productCache = MySimpleCache.get(orderLine.getProductId());
            Product returnProduct = returnProductBase(product, orderLine);

            if(productCache==null){
                product = loadingProduct(returnProduct, orderLine);

                if(verifyExistingProduct(product.getId())){
                    productRepository.save(product);
                }

                orderLineNew = loadingOrderLine(orderLine, order, products);
                MySimpleCache.put(product);
                order = calculateOrder(order, product, orderLineNew);

            }else{
                orderLineNew = loadingOrderLine(orderLine, order, products);
                order = calculateOrder(order, productCache, orderLineNew);
            }
        }

        orderRepository.save(order);
        return order;
    }

    private OrderLine loadingOrderLine(OrderLine orderLine, Order orderNew, List<OrderLine> products) {
        OrderLine orderLineNew = new OrderLine();
        orderLineNew.setProductId(orderLine.getProductId());
        orderLineNew.setQuantity(orderLine.getQuantity());

        orderLineNew.setOrder_id(orderNew);
        products.add(orderLineNew);
        orderNew.setProducts(products);

        return  orderLineNew;
    }

    private Product returnProductBase(Product product, OrderLine orderLine) {
        List<ServiceInstance> instances = discoveryClient.getInstances("productservice");
        RestTemplate restTemplate = new RestTemplate();
        String uri = String.format("%s/product/%s",
                instances.get(0).getUri().toString(), orderLine.getProductId());
        ResponseEntity<Product> restExchange = restTemplate.exchange(uri,
                HttpMethod.GET, null, Product.class, orderLine.getProductId());
        product = restExchange.getBody();
        return product;
    }

    private Order calculateOrder(Order order, Product product, OrderLine orderLine) {
        order.setTotalOrder(order.getTotalOrder() + (orderLine.getQuantity() * product.getPrice()));
        order.setStatus("Created");
        return order;
    }

    private Product loadingProduct(Product product, OrderLine orderLine){
        product.setId(orderLine.getProductId());
        product.setQuantity(orderLine.getQuantity());
        product.setPrice(product.getPrice());
        product.setDescription(product.getDescription());
        return product;
    }

    private boolean verifyExistingProduct(Integer id) {
        Optional<Product> prod =  productRepository.findById(id);
        if(prod.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public Order findById(Long id) {
        return (Order)orderRepository.findById(id).get();
    }

    @Override
    public List<Order> getListOrder() {
        return orderRepository.findAll();
    }

    public Order calculateDeliveryPrice(Long id, Double distance) {
        Order order = findById(id);
        Double deliveryPrice = 0.0;

        if (distance >= 20) {
            deliveryPrice = 100.0;
        }
        if (distance > 0 && distance < 10) {
            deliveryPrice = 30.0;
        }
        if (distance >= 10 && distance < 20) {
            deliveryPrice = 60.0;
        }
        if (distance <= 0) {
            deliveryPrice = 0.0;
        }
        order.setDeliveryPrice(deliveryPrice);
        orderRepository.save(order);
        return order;
    }

    @StreamListener(target = Sink.INPUT)
    @Override
    public void consumerProductEvent(@Payload Product event) {
        System.out.println("Received a product {} " + event.getId() + " Price: " +
                event.getPrice());
        MySimpleCache.put(event);
    }
}
