package com.epam.yevheniy.chornenky.market.place.servlet.dto;

import java.util.List;

public class OrderViewDTO {
    private final String orderId;
    private final String userId;
    private final String status;
    private final String address;
    private final String phoneNumber;
    private final String price;
    private final List<OrderItemViewDTO> orderItemDTOList;

    public OrderViewDTO(String orderId, String userId, String status, String address, String phoneNumber, String price, List<OrderItemViewDTO> orderItemDTOList) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.orderItemDTOList = orderItemDTOList;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public List<OrderItemViewDTO> getOrderItemDTOList() {
        return orderItemDTOList;
    }

    public static class OrderItemViewDTO {

        private final String orderItemId;
        private final String parentOrderId;
        private final String goodsId;
        private final String quantity;
        private final String price;
        private final String totalPrice;

        public OrderItemViewDTO(String orderItemId, String parentOrderId, String goodsId, String quantity, String price, String totalPrice) {
            this.orderItemId = orderItemId;
            this.parentOrderId = parentOrderId;
            this.goodsId = goodsId;
            this.quantity = quantity;
            this.price = price;
            this.totalPrice = totalPrice;
        }

        public String getOrderItemId() {
            return orderItemId;
        }

        public String getParentOrderId() {
            return parentOrderId;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getPrice() {
            return price;
        }

        public String getTotalPrice() {
            return totalPrice;
        }
    }
}
