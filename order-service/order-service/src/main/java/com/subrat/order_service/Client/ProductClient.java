package com.subrat.order_service.Client;

import com.subrat.order_service.Dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/product/{id}")
    ProductDto getProductById(@PathVariable Integer id);

    @PutMapping("product/reduce/{id}/{quantity}")
    void reduceStock(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity);

}
