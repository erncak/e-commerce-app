package com.erincak.ecommerce.product;

import java.math.BigDecimal;

import com.erincak.ecommerce.category.Category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
@NotNull
Integer id,
@NotNull(message = "Name cannot be null")
String name,
@NotNull(message = "Description cannot be null")
String description,
@Positive(message = "Quantity should be positive")
double availableQuantity,
@Positive(message = "Price should be positive")
BigDecimal price,
@NotNull(message = "Category cannot be null")
Integer categoryId) {

    
}
