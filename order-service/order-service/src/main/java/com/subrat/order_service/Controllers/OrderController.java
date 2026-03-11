package com.subrat.order_service.Controllers;

import com.subrat.order_service.Entity.OrderEntity;
import com.subrat.order_service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<OrderEntity> create(OrderEntity orderEntity) {
        return ResponseEntity.ok(orderService.create(orderEntity));
    }

    @GetMapping
    public ResponseEntity<List<OrderEntity>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(@RequestParam Integer productId, @RequestParam int quantity) {
        return ResponseEntity.ok(orderService.placeOrder(productId, quantity));

    }
}
