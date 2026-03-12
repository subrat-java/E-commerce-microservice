E-commerce Microservice



Building an E-commerce backend using Microservices Architecture with:



Spring Boot

Spring Security

Spring Data JPA

API Gateway

Service Registry (Eureka)

OpenFeign Client

Resilience4j

MySQL

🏗 Architecture

                 Client

                   │

                   ▼

             API Gateway

                   │

                   ▼

           Service Registry

              (Eureka)

                   │

        ┌──────────┼──────────┐

        ▼          ▼          ▼

   Order Service  Product Service  User Service

        │

        ▼

     MySQL Database

🚀 Features Implemented

Core Microservices Features



✔ Service Discovery using Eureka

✔ API Gateway using Spring Cloud Gateway

✔ Service-to-service communication using OpenFeign

✔ Fault tolerance using Resilience4j Circuit Breaker

✔ Clean API design using DTO Layer

✔ Automatic DTO ↔ Entity mapping using ModelMapper

✔ Global Exception Handling

✔ Centralized routing through Gateway



🧩 Microservices

Service	Description

Service Registry	Registers all microservices

API Gateway	Routes all incoming requests

Product Service	Handles product inventory

Order Service	Handles order processing

User Service	Handles user management

📦 Technologies Used

Java

Spring Boot

Spring Cloud

Spring Data JPA

Spring Cloud Gateway

Spring Cloud OpenFeign

Netflix Eureka

Resilience4j

ModelMapper

MySQL

Maven

Postman

🧭 Service Registry (Eureka)



Service Registry allows all microservices to register themselves and discover other services dynamically.



Step 1 — Create Service Registry Project



Create a Spring Boot project named:



service-registry

Step 2 — Add Dependency



Add Eureka Server dependency in pom.xml.



<dependency>

 <groupId>org.springframework.cloud</groupId>

 <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>

</dependency>

Step 3 — Enable Eureka Server

@EnableEurekaServer

@SpringBootApplication

public class ServiceRegistryApplication {



    public static void main(String\[] args) {

        SpringApplication.run(ServiceRegistryApplication.class, args);

    }

}

Step 4 — Configure application.properties

server.port=8761



spring.application.name=SERVICE-REGISTRY



eureka.client.register-with-eureka=false

eureka.client.fetch-registry=false

Step 5 — Start Eureka Server



Open:



http://localhost:8761



This shows the Eureka Dashboard.



🔗 Register Other Microservices as Eureka Clients



All services must register themselves with Eureka.



These services include:



Order Service

Product Service

User Service

API Gateway

Step 1 — Add Eureka Client Dependency



Add in each microservice:



<dependency>

 <groupId>org.springframework.cloud</groupId>

 <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

</dependency>

Step 2 — Configure application.properties



Example: Order Service



spring.application.name=ORDER-SERVICE

server.port=8084



eureka.client.service-url.defaultZone=http://localhost:8761/eureka



Example: Product Service



spring.application.name=PRODUCT-SERVICE

server.port=8081



eureka.client.service-url.defaultZone=http://localhost:8761/eureka



Example: User Service



spring.application.name=USER-SERVICE

server.port=8082



eureka.client.service-url.defaultZone=http://localhost:8761/eureka



Example: API Gateway



spring.application.name=API-GATEWAY

server.port=9090



eureka.client.service-url.defaultZone=http://localhost:8761/eureka

Step 3 — Start Services



Start services in this order:



1\. Service Registry

2\. Product Service

3\. User Service

4\. Order Service

5\. API Gateway

Step 4 — Verify in Dashboard



Open:



http://localhost:8761



You should see:



API-GATEWAY

ORDER-SERVICE

PRODUCT-SERVICE

USER-SERVICE

🌐 API Gateway



API Gateway acts as a single entry point for all client requests.



Instead of:



Client → Order Service

Client → Product Service

Client → User Service



We use:



Client → API Gateway → Microservices

Step 1 — Add Dependencies

<dependency>

 <groupId>org.springframework.cloud</groupId>

 <artifactId>spring-cloud-starter-gateway</artifactId>

</dependency>



<dependency>

 <groupId>org.springframework.cloud</groupId>

 <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>

</dependency>

Step 2 — Configure Gateway

server.port=9090

spring.application.name=API-GATEWAY



eureka.client.service-url.defaultZone=http://localhost:8761/eureka

Step 3 — Configure Routes

spring.cloud.gateway.routes\[0].id=ORDER-SERVICE

spring.cloud.gateway.routes\[0].uri=lb://ORDER-SERVICE

spring.cloud.gateway.routes\[0].predicates\[0]=Path=/order/\*\*



spring.cloud.gateway.routes\[1].id=PRODUCT-SERVICE

spring.cloud.gateway.routes\[1].uri=lb://PRODUCT-SERVICE

spring.cloud.gateway.routes\[1].predicates\[0]=Path=/product/\*\*



spring.cloud.gateway.routes\[2].id=USER-SERVICE

spring.cloud.gateway.routes\[2].uri=lb://USER-SERVICE

spring.cloud.gateway.routes\[2].predicates\[0]=Path=/user/\*\*



lb:// tells Gateway to use load balancing with Eureka.



🔗 Inter-Service Communication (OpenFeign)



Order Service communicates with Product Service using OpenFeign Client.



Step 1 — Add Dependency

<dependency>

 <groupId>org.springframework.cloud</groupId>

 <artifactId>spring-cloud-starter-openfeign</artifactId>

</dependency>

Step 2 — Enable Feign Clients

@EnableFeignClients

@SpringBootApplication

public class OrderServiceApplication {

}

Step 3 — Create Feign Client

@FeignClient(name = "PRODUCT-SERVICE")

public interface ProductClient {



    @GetMapping("/product/{id}")

    ProductEntity getProductById(@PathVariable("id") Integer id);



    @PutMapping("/product/reduce/{id}/{quantity}")

    void reduceStock(@PathVariable("id") Integer id,

                     @PathVariable("quantity") Integer quantity);

}

🔄 Circuit Breaker (Resilience4j)



Prevents cascading failures if Product Service becomes unavailable.



Step 1 — Add Dependency

<dependency>

 <groupId>org.springframework.cloud</groupId>

 <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>

</dependency>

Step 2 — Add Configuration

resilience4j.circuitbreaker.instances.productService.slidingWindowSize=5

resilience4j.circuitbreaker.instances.productService.failureRateThreshold=50

resilience4j.circuitbreaker.instances.productService.waitDurationInOpenState=10s

resilience4j.circuitbreaker.instances.productService.minimumNumberOfCalls=5

Step 3 — Add Circuit Breaker Annotation

@CircuitBreaker(name = "productService", fallbackMethod = "productFallback")

Step 4 — Fallback Method

public OrderResponseDTO productFallback(OrderRequestDTO request, Exception ex){



    OrderResponseDTO response = modelMapper.map(request, OrderResponseDTO.class);



    response.setTotalPrice(0.0);



    return response;

}

📄 DTO Layer

OrderRequestDTO

public class OrderRequestDTO {



    private Integer productId;

    private Integer quantity;



}



Example Request



{

 "productId":1,

 "quantity":2

}

OrderResponseDTO

public class OrderResponseDTO {



    private Integer productId;

    private Integer quantity;

    private Double totalPrice;



}



Example Response



{

 "productId":1,

 "quantity":2,

 "totalPrice":1000

}

🔁 ModelMapper Configuration

Step 1 — Add Dependency

<dependency>

 <groupId>org.modelmapper</groupId>

 <artifactId>modelmapper</artifactId>

 <version>3.2.0</version>

</dependency>

Step 2 — Create Configuration

@Configuration

public class ModelMapperConfig {



    @Bean

    public ModelMapper modelMapper(){

        return new ModelMapper();

    }

}

🖥 Ports and Endpoints

Service	Port

Eureka Server	8761

API Gateway	9090

Product Service	8081

Order Service	8084

User Service	8082

Product Service APIs

POST   /product

GET    /product

GET    /product/{id}

PUT    /product/reduce/{id}/{quantity}

Order Service APIs

POST   /order/add

GET    /order

POST   /order/place

User Service APIs

POST   /user

GET    /user

👨‍💻 Author



Subrat Kumar Patra



Java Backend Developer

Spring Boot | Microservices | REST APIs

