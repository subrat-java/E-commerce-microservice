package com.subrat.order_service.Dto;

public class OrderResponseDTO {

    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Double totalPrice;

    public Integer getUserId() {
        return userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
