package com.amisadman.aybaylite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    PaymentService paymentService;

    @InjectMocks
    OrderService orderService;

    @Test
    void processOrderTest(){
        when(paymentService.processPayment(5)).thenReturn(true);

        boolean result = orderService.processOrder(5);
        assertTrue(result);
        verify(paymentService, times(1)).processPayment(5);
        verify(paymentService,never()).processPayment(0);
    }


}