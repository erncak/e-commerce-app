package com.erincak.ecommerce.product;

import org.springframework.stereotype.Component;

import com.erincak.ecommerce.category.Category;

import jakarta.validation.constraints.NotNull;
@Component
public class ProductMapper {


    public Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .id(productRequest.id())
                .description(productRequest.description())
                .name(productRequest.name())
                .price(productRequest.price())
                .availableQuantity(productRequest.availableQuantity())
                .category(Category.builder()
                .id(productRequest.categoryId()).build())
                .build();

    }
    public ProductResponse toProductResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(),  product.getDescription(), product.getAvailableQuantity(), product.getPrice(), product.getCategory().getId(), product.getCategory().getName(), product.getCategory().getDescription());
    }
    public ProductPurchaseResponse toProductPurchaseResponse(Product product,
            double quantity) {
        // TODO Auto-generated method stub

                    return new ProductPurchaseResponse(product.getId(),
                     product.getName(),
                      product.getDescription(), 
                      product.getPrice(), 
                      quantity);
    } 
}
