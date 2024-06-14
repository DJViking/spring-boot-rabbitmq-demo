package com.example.spring.boot.rabbitmq.demo.messaging;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long customerId;

    private Long paymentId;

    private String paymentStatus;

    private BigDecimal paymentAmount;

    private String error;

}
