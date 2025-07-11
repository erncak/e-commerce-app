package com.erincak.ecommerce.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.erincak.ecommerce.exception.BusinessException;

import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductClient {
@Value("${application.config.product-url}")    
private String productUrl;
private final RestTemplate restTemplate;

public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> requestBody){
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody,headers);
    ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(productUrl + "/purchase",
         HttpMethod.POST,
         requestEntity,
         responseType
         );
         if(responseEntity.getStatusCode().isError()){ 
            throw new BusinessException("An error accured while processing the product purchase" + responseEntity.getStatusCode());
         }
        return responseEntity.getBody();
    
  
}
}
