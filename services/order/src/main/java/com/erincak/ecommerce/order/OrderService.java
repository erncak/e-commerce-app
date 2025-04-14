package com.erincak.ecommerce.order;

import org.springframework.stereotype.Service;

import com.erincak.ecommerce.customer.CustomerClient;
import com.erincak.ecommerce.exception.BusinessException;
import com.erincak.ecommerce.orderline.OrderLineRequest;
import com.erincak.ecommerce.orderline.OrderLineService;
import com.erincak.ecommerce.product.ProductClient;
import com.erincak.ecommerce.product.PurchaseRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    public Integer createOrder(OrderRequest request) {
       //check the customer OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order :: No Customer exist with provided ID"));
       //purchase the product ---> product-ms (RestTemplate)

        this.productClient.purchaseProducts(request.products());
               //persist order

        var order = this.orderRepository.save(mapper.toOrder(request));

       //persist order lines
        for (PurchaseRequest purchaseRequest: request.products()){
                orderLineService.saveOrderLine(
                    new OrderLineRequest(
                        null,
                        order.getId(),
                        purchaseRequest.productId(),
                        purchaseRequest.quantity()
                    )
                );
        }

       // todo start payment process


       // send the order confirmation ->> notification ms(kafka)


        return null;
    }

}
