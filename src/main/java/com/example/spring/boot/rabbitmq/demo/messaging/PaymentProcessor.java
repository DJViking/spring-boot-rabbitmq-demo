package com.example.spring.boot.rabbitmq.demo.messaging;

import java.math.BigDecimal;
import java.util.function.Function;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentProcessor {

    private final Counter processCounter;
    private final Timer processTimer;

    public PaymentProcessor(
        final MeterRegistry registry
    ) {
        this.processCounter = Counter.builder("payment.process.count")
            .register(registry);
        this.processTimer = Timer.builder("payment.process.timer")
            .register(registry);
    }

    @Bean
    public Function<PaymentRequest, PaymentResponse> processPayment() {
        return this::processRequest;
    }

    private PaymentResponse processRequest(final PaymentRequest request) {
        log.debug("Process Payment Request: {}", request);
        final PaymentResponse response = new PaymentResponse();
        processCounter.increment();
        processTimer.record(() -> recordProcessRequest(request, response));
        log.debug("Payment Request Processed: {}", request);
        return response;
    }

    private void recordProcessRequest(final PaymentRequest request, final PaymentResponse response) {
        try {
            // TODO Do some payment processing here.
            response.setCustomerId(request.getCustomerId());
            response.setPaymentId(234567654L);
            response.setPaymentStatus("Paid");
            response.setPaymentAmount(BigDecimal.valueOf(150500));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setError(e.getMessage());
        }
    }

}
