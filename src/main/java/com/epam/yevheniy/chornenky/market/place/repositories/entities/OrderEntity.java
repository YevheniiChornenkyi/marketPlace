package com.epam.yevheniy.chornenky.market.place.repositories.entities;

import java.util.List;

/**
 * Order entity. This transfer object characterized by orderId, userId, status, address, phoneNumber, price, orderItems.
 * orderItems a separate class with its own parameters
 * Status enum enumeration of fashionable status's
 * id must be unique.
 */
public class OrderEntity {

    private final String orderId;
    private final String userId;
    private final Status status;
    private final String address;
    private final String phoneNumber;
    private final String price;
    private final List<OrderItem> orderItems;

    public OrderEntity(String orderId, String userId, Status status, String address, String phoneNumber, String price, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.orderItems = orderItems;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public Status getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public enum Status {
        AWAITING_DELIVERY("Awaiting delivery"),
        AWAITING_PICKUP("Awaiting pickup"),
        CANCELLED("Cancelled"),
        UNCONFIRMED("Unconfirmed"),
        PROCESSED("Processed");

        private final String statusView;

        Status(String statusView) {
            this.statusView = statusView;
        }

        public String getStatusView() {
            return statusView;
        }
    }

    /**
     * Part of Order entity
     * This transfer object characterized by orderItemId, parentOrderId, goodsId, quantity, price, totalPrice.
     * orderItemId, parentOrderId must be unique.
     */
    public static class OrderItem {
        private final String orderItemId;
        private final String parentOrderId;
        private final int goodsId;
        private final int quantity;
        private final String price;
        private final String totalPrice;

        public OrderItem(String orderItemId, String parentOrderId, int goodsId, int quantity, String price, String totalPrice) {
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

        public int getGoodsId() {
            return goodsId;
        }

        public int getQuantity() {
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
