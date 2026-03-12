package com.subrat.order_service.Service;

import com.subrat.Product_Service.Entity.ProductEntity;
import com.subrat.order_service.Client.ProductClient;
import com.subrat.order_service.Dto.OrderRequestDTO;
import com.subrat.order_service.Dto.OrderResponseDTO;
import com.subrat.order_service.Entity.OrderEntity;
import com.subrat.order_service.Repository.OrderRepository;
import com.subrat.order_service.Exception.ProductNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductClient productClient;

    @Autowired
    private ModelMapper modelMapper;

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

    @CircuitBreaker(name = "PRODUCT-SERVICE", fallbackMethod = "productFallback")
    public OrderResponseDTO placeOrder(OrderRequestDTO request) {

        ProductEntity product = productClient.getProductById(request.getProductId());

        if(product == null){
            throw new ProductNotFoundException("Product not found");
        }

        if (product.getStock() < request.getQuantity()){
            throw new RuntimeException("Not enough stock available");
        }

        productClient.reduceStock(request.getProductId(),request.getQuantity());

        //This is before ModelMapper implementation

//        OrderEntity order = new OrderEntity();
//        order.setProductId(request.getProductId());
//        order.setQuantity(request.getQuantity());
//

//        order.setTotalPrice(totalPrice);

//      OrderResponseDTO responseDTO = new OrderResponseDTO();
//      responseDTO.setUserId(savedOrder.getUserId());
//      responseDTO.setQuantity(order.getQuantity());
//      responseDTO.setProductId(order.getProductId());
//      responseDTO.setTotalPrice(order.getTotalPrice());

        OrderEntity order = modelMapper.map(request,OrderEntity.class);
        double totalPrice = product.getPrice() * request.getQuantity();
        order.setTotalPrice(totalPrice);
        OrderEntity savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder,OrderResponseDTO.class);


    }

    public OrderResponseDTO productFallback(OrderRequestDTO requestDTO,Exception ex){
        OrderResponseDTO responseDTO = modelMapper.map(requestDTO,OrderResponseDTO.class);
        responseDTO.setTotalPrice(0.0);
        return responseDTO;
    }


}
