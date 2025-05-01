package com.erincak.ecommerce.orderline;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest){
        var order = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(order).getId();
        

    }

    public List<OrderLineResponse> findAllByOrderById(Integer orderId) {
        // TODO Auto-generated method stub
        return orderLineRepository.findAllByOrderId(orderId)
            .stream()
            .map(orderLineMapper::toOrderLineResponse)
            .collect(Collectors.toList());
    }
}
