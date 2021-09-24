package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.repositories.OrderRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.OrderEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateOrderDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.OrderViewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(CreateOrderDTO createOrderDTO) {
        Cart cart = createOrderDTO.getCart();
        List<OrderEntity.OrderItem> orderItems = new ArrayList<>();
        String orderId = UUID.randomUUID().toString();
        BigDecimal orderPriceBigDecimal = new BigDecimal("0");

        for (Map.Entry<GoodsViewDTO, Integer> goods : cart.getCartEntrySet()) {
            int goodsId = goods.getKey().getId();
            String orderItemId = orderId + goodsId;
            Integer quantity = goods.getValue();
            String price = goods.getKey().getPrice();
            BigDecimal bigDecimalPrice = new BigDecimal(price);
            BigDecimal totalPriceBigDecimal = bigDecimalPrice.multiply(BigDecimal.valueOf(quantity));
            orderPriceBigDecimal = orderPriceBigDecimal.add(totalPriceBigDecimal);
            OrderEntity.OrderItem orderItem = new OrderEntity.OrderItem(orderItemId, orderId, goodsId, quantity, price, totalPriceBigDecimal.toString());
            orderItems.add(orderItem);
        }

        String userId = createOrderDTO.getUserId();
        String address = createOrderDTO.getAddress().equals("") ? "self-pickup" : createOrderDTO.getAddress();
        String phoneNumber = createOrderDTO.getPhoneNumber();
        OrderEntity.Status status = OrderEntity.Status.UNCONFIRMED;
        String orderPrice = orderPriceBigDecimal.toString();

        OrderEntity orderEntity = new OrderEntity(orderId, userId, status, address, phoneNumber, orderPrice, orderItems);
        orderRepository.createOrder(orderEntity);
    }

    public List<OrderViewDTO> getOrderViewDTOList() {
        List<OrderEntity> orderEntities = orderRepository.getOrderEntitiesList();

        return orderEntities
                .stream()
                .map(this::orderEntityToOrderViewDTO)
                .collect(Collectors.toList());
    }

    private OrderViewDTO orderEntityToOrderViewDTO(OrderEntity orderEntity) {
        String orderId = orderEntity.getOrderId();
        String userId = orderEntity.getUserId();
        String status = orderEntity.getStatus().getStatusView();
        String address = orderEntity.getAddress();
        String phoneNumber = orderEntity.getPhoneNumber();
        String price = orderEntity.getPrice();
        List<OrderViewDTO.OrderItemViewDTO> orderItemViewDTOList = orderEntity.getOrderItems()
                .stream()
                .map(this::orderItemToOrderItemViewDTO)
                .collect(Collectors.toList());
        return new OrderViewDTO(orderId, userId, status, address, phoneNumber, price, orderItemViewDTOList);
    }

    private OrderViewDTO.OrderItemViewDTO orderItemToOrderItemViewDTO(OrderEntity.OrderItem orderItem) {
        String orderItemId = orderItem.getOrderItemId();
        String parentOrderId = orderItem.getParentOrderId();
        String goodsId = String.valueOf(orderItem.getGoodsId());
        String quantity = String.valueOf(orderItem.getQuantity());
        String price = orderItem.getPrice();
        String totalPrice = orderItem.getTotalPrice();


        return new OrderViewDTO.OrderItemViewDTO(orderItemId, parentOrderId, goodsId, quantity, price, totalPrice);
    }

    public List<OrderViewDTO> getOrderViewDTOListById(String userId) {
        List<OrderEntity> orderEntities = orderRepository.getOrderEntityListById(userId);

        return orderEntities.stream()
                .map(this::orderEntityToOrderViewDTO)
                .collect(Collectors.toList());
    }

    public List<OrderEntity.Status> getAllStatuses() {
        return orderRepository.getAllStatusesEnum();
    }

    private String extractStatusNameFromStatusEnum(OrderEntity.Status status) {
        return status.getStatusView();
    }

    public void changeOrderStatusById(String orderId, String newStatus) {
        orderRepository.changeOrderStatusById(orderId, newStatus);
    }
}
