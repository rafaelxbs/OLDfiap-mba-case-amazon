package com.fiap.ralfmed.orderamazonservice.entity;

public class ResponseOrder {

    public static String convert(Order order){
        return "Status do pedido = " + order.getStatus() + ". O total do pedido é: " + order.getTotalOrder();
    }

}
