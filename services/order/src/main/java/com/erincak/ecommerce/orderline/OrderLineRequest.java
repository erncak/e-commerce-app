package com.erincak.ecommerce.orderline;

public record OrderLineRequest(
Integer id,
Integer orderId,
Integer productId,
double quantity


) {

}
