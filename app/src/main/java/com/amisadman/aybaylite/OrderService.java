package com.amisadman.aybaylite;


public class OrderService {
    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    public boolean processOrder(int orderId){
        if(orderId != 0) return paymentService.processPayment(orderId);

        return false;
    }
}

