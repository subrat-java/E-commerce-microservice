package com.subrat.order_service.Controllers;

import com.subrat.order_service.Dto.OrderRequestDTO;
import com.subrat.order_service.Dto.OrderResponseDTO;
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
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {

                 System.out.println(orderRequestDTO.getProductId());
                 System.out.println(orderRequestDTO.getQuantity());

            OrderResponseDTO responseDTO = orderService.placeOrder(orderRequestDTO);
        return ResponseEntity.ok(responseDTO);

    }
}
