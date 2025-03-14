package com.erincak.ecommerce.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.erincak.ecommerce.exceptions.CustomerNotFoundException;
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest customerRequest) {
        var customer = repository.save(mapper.toCustomer(customerRequest));

        return customer.getId();
    }
    public void updateCustomer(CustomerRequest customerRequest) {
        var customer = this.repository.findById(customerRequest.id())
        .orElseThrow(() -> new CustomerNotFoundException(
            String.format("Cannot update customer:: No customer found with the provided ID: %s", customerRequest.id())
        ));

        
        mergeCustomer(customer,customerRequest);
                repository.save(customer);
            }
    private void mergeCustomer(Customer customer, CustomerRequest customerRequest) {
                // TODO Auto-generated method stub
                if(StringUtils.isNotBlank(customerRequest.firstname())){
                    customer.setFirstname(customerRequest.firstname());
                }
                if(StringUtils.isNotBlank(customerRequest.lastname())){
                    customer.setLastname(customerRequest.lastname());
                }
                if(StringUtils.isNotBlank(customerRequest.email())){
                    customer.setEmail(customerRequest.email());
                }
                if(customerRequest.address() != null){
                    customer.setAddress(customerRequest.address());
                }
            }

        public List<CustomerResponse> findallCustomers() {
            // TODO Auto-generated method stub
            return repository.findAll()
            .stream()
            .map(mapper::fromCustomer)
            .collect(Collectors.toList());
        }
        public Boolean existById(String customerId) {
            // TODO Auto-generated method stub 
            return repository.findById(customerId)
                                .isPresent();
        }
        public CustomerResponse findById(String customerId) {
            // TODO Auto-generated method stub
            return repository.findById(customerId)
            .map(mapper::fromCustomer)
            .orElseThrow(() -> new CustomerNotFoundException(
                String.format("No customer found with the provided ID: %s", customerId)
            ));
        }
        public void deleteCustomer(String customerId) {
            // TODO Auto-generated method stub
            repository.deleteById(customerId);
        }
    
}
