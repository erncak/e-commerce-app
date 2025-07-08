package com.erincak.ecommerce.order;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.erincak.ecommerce.customer.CustomerClient;
import com.erincak.ecommerce.exception.BusinessException;
import com.erincak.ecommerce.kafka.OrderConfirmation;
import com.erincak.ecommerce.kafka.OrderProducer;
import com.erincak.ecommerce.orderline.OrderLineRequest;
import com.erincak.ecommerce.orderline.OrderLineService;
import com.erincak.ecommerce.payment.PaymentClient;
import com.erincak.ecommerce.payment.PaymentRequest;
import com.erincak.ecommerce.product.ProductClient;
import com.erincak.ecommerce.product.PurchaseRequest;

import jakarta.persistence.EntityNotFoundException;
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
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;
    public Integer createOrder(OrderRequest request) {
       //check the customer OpenFeign
        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order :: No Customer exist with provided ID"));
       //purchase the product ---> product-ms (RestTemplate)

       var purchasedProducts =  this.productClient.purchaseProducts(request.products());
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
        var paymentRequest = new PaymentRequest(
            request.amount(),
            request.paymentMethod(),
            order.getId(),
            order.getReference(),
            customer
        );
        paymentClient.requestOrderPayment(paymentRequest);
       // todo start payment process
        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts
        ));


       // send the order confirmation ->> notification ms(kafka)


        return order.getId();
    }
    public List<OrderResponse> findAll() {
        // TODO Auto-generated method stub
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder) 
                .collect(Collectors.toList());
        }
    public OrderResponse findById(Integer orderId) {
        // TODO Auto-generated method stub
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order with order Id not found::" + orderId));}

}
