package com.subrat.order_service.Service;

import com.subrat.order_service.Entity.OrderEntity;
import com.subrat.order_service.Entity.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public OrderEntity create(OrderEntity orderEntity){
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAll(){
        return orderRepository.findAll();
    }
}
