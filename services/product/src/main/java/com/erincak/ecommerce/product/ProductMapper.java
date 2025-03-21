package com.erincak.ecommerce.product;

import com.erincak.ecommerce.category.Category;

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
}
