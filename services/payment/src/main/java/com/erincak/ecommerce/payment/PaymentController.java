package com.erincak.ecommerce.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {
private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Integer> createPayment(@RequestBody PaymentRequest request){
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
