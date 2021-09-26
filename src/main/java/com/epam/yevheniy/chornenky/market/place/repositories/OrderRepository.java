package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.exceptions.DBException;
import com.epam.yevheniy.chornenky.market.place.exceptions.OrderException;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.OrderEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private static final Logger LOGGER = LogManager.getLogger(OrderRepository.class);
    public static final String INSERT_ORDER_ITEM_QUERY = "INSERT INTO order_item (order_item_id, parent_order_id, goods_id, quantity, price, total_price) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_ORDER_QUERY = "INSERT INTO orders (order_id, user_id, status, address, phone_number, price) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ALL_ORDERS_QUERY = "SELECT * FROM orders";
    public static final String SELECT_ORDERS_ITEM_BY_ORDER_ID = "SELECT * FROM order_item WHERE parent_order_id=?";
    public static final String SELECT_ALL_STATUSES_QUERY = "SELECT * FROM statuses";
    public static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE orders SET orders.status=? WHERE orders.order_id=?";


    private final ConnectionManager connectionManager;

    public OrderRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void createOrder(OrderEntity orderEntity) {
        String orderId = orderEntity.getOrderId();
        String userId = orderEntity.getUserId();
        String status = orderEntity.getStatus().toString();
        String address = orderEntity.getAddress();
        String phoneNumber = orderEntity.getPhoneNumber();
        String price = orderEntity.getPrice();

        try(Connection connection = connectionManager.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_QUERY);
            preparedStatement.setString(1, orderId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, status);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setString(6, price);
            preparedStatement.executeUpdate();
            fillOrderByOrdersItem(connection, orderEntity);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.error("Error creating order at repository level", e);
            throw new OrderException("Error creating order at repository level");
        }
    }

    private void fillOrderByOrdersItem(Connection connection, OrderEntity orderEntity) throws SQLException {
        for (OrderEntity.OrderItem orderItem : orderEntity.getOrderItems()) {
            String orderItemId = orderItem.getOrderItemId();
            String parentOrderId = orderItem.getParentOrderId();
            int goodsId = orderItem.getGoodsId();
            int quantity = orderItem.getQuantity();
            String price = orderItem.getPrice();
            String totalPrice = orderItem.getTotalPrice();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_QUERY);
            preparedStatement.setString(1, orderItemId);
            preparedStatement.setString(2, parentOrderId);
            preparedStatement.setInt(3, goodsId);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setString(5, price);
            preparedStatement.setString(6, totalPrice);
            preparedStatement.executeUpdate();
        }
    }

    public List<OrderEntity> getOrderEntitiesList() {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractOrderEntityFromResultSet(resultSet, connection);
        } catch (SQLException e) {
            LOGGER.error("Error int getOrderEntitiesList method", e);
            throw new OrderException("Error in get order list method");
        }
    }

    private List<OrderEntity.OrderItem> getOrderItemsByOrderId(String orderId, Connection connection) throws SQLException {
        List<OrderEntity.OrderItem> orderItems = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_ITEM_BY_ORDER_ID);
        preparedStatement.setString(1, orderId);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String orderItemId = resultSet.getString("order_item_id");
            int goodsId = resultSet.getInt("goods_id");
            int quantity = resultSet.getInt("quantity");
            String price = resultSet.getString("price");
            String totalPrice = resultSet.getString("total_price");
            OrderEntity.OrderItem orderItem = new OrderEntity.OrderItem(orderItemId, orderId, goodsId, quantity, price, totalPrice);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private List<OrderEntity> extractOrderEntityFromResultSet(ResultSet resultSet, Connection connection) throws SQLException {
        List<OrderEntity> orderEntities = new ArrayList<>();
        while (resultSet.next()) {
            String orderId = resultSet.getString("order_id");
            String userId = resultSet.getString("user_id");
            OrderEntity.Status status = OrderEntity.Status.valueOf(resultSet.getString("status"));
            String address = resultSet.getString("address");
            String phoneNumber = resultSet.getString("phone_number");
            String price = resultSet.getString("price");
            List<OrderEntity.OrderItem> orderItems = getOrderItemsByOrderId(orderId, connection);
            OrderEntity orderEntity = new OrderEntity(orderId, userId, status, address, phoneNumber, price, orderItems);
            orderEntities.add(orderEntity);
        }
        return orderEntities;
    }

    public List<OrderEntity> getOrderEntityListById(String userId) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM orders WHERE orders.user_id=?");
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return extractOrderEntityFromResultSet(resultSet, connection);
        } catch (SQLException e) {
            LOGGER.error("Cant find orders user with id {}", userId);
            throw new DBException();
        }
    }

    public List<OrderEntity.Status> getAllStatusesEnum() {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STATUSES_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderEntity.Status> statuses = new ArrayList<>();
            while (resultSet.next()) {
                statuses.add(OrderEntity.Status.valueOf(resultSet.getString("status")));
            }
            return statuses;
        } catch (SQLException e) {
            LOGGER.error("Cant find all statuses");
            throw new DBException();
        }
    }

    public void changeOrderStatusById(String orderId, String newStatus) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ID);
            preparedStatement.setString(1, newStatus);
            preparedStatement.setString(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cant find order by id, or incorrect new status");
            throw new DBException();
        }
    }
}
