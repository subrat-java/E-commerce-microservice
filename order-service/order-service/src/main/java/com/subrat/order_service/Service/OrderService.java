package com.subrat.order_service.Service;

import com.subrat.Product_Service.Entity.ProductEntity;
import com.subrat.order_service.Client.ProductClient;
import com.subrat.order_service.Entity.OrderEntity;
import com.subrat.order_service.Entity.Repository.OrderRepository;
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

    public String placeOrder(Integer productId, int quantity) {

        ProductEntity product = productClient.getProductById(productId);

        if (product.getStock() < quantity){
            return "Not enough stock available";
        }
        OrderEntity order = new OrderEntity();
        order.setProductId(productId);
        order.setQuantity(quantity);

        double totalPrice = product.getPrice()*quantity;
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);

        return "Order Place successfully";
    }

    public OrderEntity placeOrder(OrderEntity order){
        ProductEntity product = productClient.getProductById(order.getProductId());

        if (product.getStock() < order.getQuantity()){
            throw  new RuntimeException("not enough of stock");
        }
         productClient.reduceStock(order.getProductId(),order.getQuantity());
        return orderRepository.save(order);
    }
}
