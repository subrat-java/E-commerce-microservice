package com.subrat.Product_Service.Service;

import com.subrat.Product_Service.Entity.ProductEntity;
import com.subrat.Product_Service.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductEntity addProduct(ProductEntity productEntity){
        return productRepository.save(productEntity);
    }

    public List<ProductEntity> getAll(){
        return productRepository.findAll();
    }

}
