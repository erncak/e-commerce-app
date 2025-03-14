package com.erincak.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
     String id,
     @NotNull(message = "Firstname is mandatory")
     String firstname,
     @NotNull(message = "Lastname is mandatory")
     String lastname,
     @NotNull(message = "Email is mandatory")
     @Email(message = "Email should be valid")
     String email,
     Address address
) {

}
