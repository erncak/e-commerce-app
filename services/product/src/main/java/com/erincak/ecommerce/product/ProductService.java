package com.erincak.ecommerce.product;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
     }

    public ProductResponse findById(Integer productId) {
      return  repository.findById(productId)
      .map(mapper::toProductResponse).orElseThrow(() -> new  EntityNotFoundException("Product with product Id not found::" + productId));

      
    }

    public List<ProductResponse> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'purchaseProduct'");
    }

}
