package com.subrat.Product_Service.Controllers;

import com.subrat.Product_Service.Entity.ProductEntity;
import com.subrat.Product_Service.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity productEntity){
        return ResponseEntity.ok(productService.addProduct(productEntity));
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/reduce/{id}/{quantity}")
    public ResponseEntity<String> reduceStock(@PathVariable Integer id,@PathVariable Integer quantity){
        productService.reduceStock(id,quantity);
        return  ResponseEntity.ok("Stock reduced ssuccessfully");

    }
}
