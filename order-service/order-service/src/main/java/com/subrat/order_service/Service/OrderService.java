package com.subrat.order_service.Service;

import com.subrat.Product_Service.Entity.ProductEntity;
import com.subrat.order_service.Client.ProductClient;
import com.subrat.order_service.Entity.OrderEntity;
import com.subrat.order_service.Entity.Repository.OrderRepository;
import com.subrat.order_service.Exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductClient productClient;

    public OrderEntity create(OrderEntity orderEntity){
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAll(){
        return orderRepository.findAll();
    }

    public String createOrder(Integer id){
        ProductEntity product = productClient.getProductById(id);

        if(product.getStock() <= 0){
            return "Product Out Of Stock";
        }

        return "Order Placed Successfully";
    }

    public String placeOrder(Integer productId, Integer quantity) {

        ProductEntity product = productClient.getProductById(productId);

        if(product == null){
            throw new ProductNotFoundException("Product not found");
        }

        if (product.getStock() < quantity){
            throw new RuntimeException("Not enough stock available");
        }

        productClient.reduceStock(productId, quantity);

        OrderEntity order = new OrderEntity();
        order.setProductId(productId);
        order.setQuantity(quantity);

        double totalPrice = product.getPrice() * quantity;
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        return "Order placed successfully";
    }
}
