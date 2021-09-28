package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.OrderEntity;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrderRepositoryTest {

    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @Captor
    private ArgumentCaptor<Integer> integerArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @InjectMocks
    private OrderRepository tested;

    @Test
    public void givenOrderEntity_WhenInvokeCreateOrder_ThenPreparedStatementMustHaveSpecificParameters() throws SQLException {
        String orderId = "orderId";
        String userId = "userId";
        OrderEntity.Status status = OrderEntity.Status.UNCONFIRMED;
        String address = "address";
        String phoneNumber = "phoneNumber";
        String price = "price";
        List<OrderEntity.OrderItem> orderItems = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity(orderId, userId, status, address, phoneNumber, price, orderItems);
        Mockito.when(connectionManager.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(preparedStatement);

        tested.createOrder(orderEntity);

        Mockito.verify(preparedStatement, Mockito.times(6)).setString(integerArgumentCaptor.capture(), stringArgumentCaptor.capture());
        List<Integer> allIndexes = integerArgumentCaptor.getAllValues();
        List<String> allValues = stringArgumentCaptor.getAllValues();
        int stringIndex = 1;
        for (Integer index : allIndexes) {
            Assertions.assertThat(index).isEqualTo(stringIndex);
            stringIndex++;
        }
        Assertions.assertThat(allValues.get(0)).isEqualTo(orderId);
        Assertions.assertThat(allValues.get(1)).isEqualTo(userId);
        Assertions.assertThat(allValues.get(2)).isEqualTo(status.toString());
        Assertions.assertThat(allValues.get(3)).isEqualTo(address);
        Assertions.assertThat(allValues.get(4)).isEqualTo(phoneNumber);
        Assertions.assertThat(allValues.get(5)).isEqualTo(price);
    }


}