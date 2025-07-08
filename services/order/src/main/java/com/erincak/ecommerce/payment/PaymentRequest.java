package com.erincak.ecommerce.payment;

import java.math.BigDecimal;

import com.erincak.ecommerce.customer.CustomerResponse;
import com.erincak.ecommerce.order.PaymentMethod;

public record PaymentRequest(
    BigDecimal amount,
    PaymentMethod paymentMethod,
    Integer orderId,
    String orderReference,
    CustomerResponse customer
) {

}
