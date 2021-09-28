package com.epam.yevheniy.chornenky.market.place.services;

import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.repositories.OrderRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.OrderEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateOrderDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.OrderViewDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * convert received createOrderDto to OrderEntity and send to repository lvl
     * @param createOrderDTO order parameters
     */
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

    /**
     * Convert orderEntity received from repository level to orderViewDto
     * @return List<OrderViewDTO>
     */
    public List<OrderViewDTO> getOrderViewDTOList() {
        List<OrderEntity> orderEntities = orderRepository.getOrderEntitiesList();

        return orderEntities
                .stream()
                .map(this::orderEntityToOrderViewDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convert orderEntity to orderViewDto
     * @param orderEntity orderEntity
     * @return OrderViewDTO
     */
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

    /**
     * convert OrderItem to OrderItemViewDTO
     * @param orderItem orderItem
     * @return OrderItemViewDTO
     */
    private OrderViewDTO.OrderItemViewDTO orderItemToOrderItemViewDTO(OrderEntity.OrderItem orderItem) {
        String orderItemId = orderItem.getOrderItemId();
        String parentOrderId = orderItem.getParentOrderId();
        String goodsId = String.valueOf(orderItem.getGoodsId());
        String quantity = String.valueOf(orderItem.getQuantity());
        String price = orderItem.getPrice();
        String totalPrice = orderItem.getTotalPrice();


        return new OrderViewDTO.OrderItemViewDTO(orderItemId, parentOrderId, goodsId, quantity, price, totalPrice);
    }

    /**
     * Convert orderEntity received from repository level to orderViewDto
     * @param userId user id whose orders we are looking for
     * @return List<OrderViewDTO>
     */
    public List<OrderViewDTO> getOrderViewDTOListById(String userId) {
        List<OrderEntity> orderEntities = orderRepository.getOrderEntityListById(userId);

        return orderEntities.stream()
                .map(this::orderEntityToOrderViewDTO)
                .collect(Collectors.toList());
    }

    /**
     * return statuses list received from repository lvl
     * @return List<OrderEntity.Status>
     */
    public List<OrderEntity.Status> getAllStatuses() {
        return orderRepository.getAllStatusesEnum();
    }

    /**
     * send to repository id order and his new status
     * @param orderId order id
     * @param newStatus new status
     */
    public void changeOrderStatusById(String orderId, String newStatus) {
        orderRepository.changeOrderStatusById(orderId, newStatus);
    }
}
