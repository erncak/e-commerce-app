package com.erincak.ecommerce.kafka;

import java.math.BigDecimal;
import java.util.List;

import com.erincak.ecommerce.customer.CustomerResponse;
import com.erincak.ecommerce.order.PaymentMethod;
import com.erincak.ecommerce.product.PurchaseResponse;

public record OrderConfirmation(
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products

) 
 {

}
