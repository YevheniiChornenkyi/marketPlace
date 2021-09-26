package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.services.OrderService;
import com.epam.yevheniy.chornenky.market.place.services.UserService;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.OrderViewDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OrdersControllerGetTest {

    public static final String JSP_PATH_TO_ORDERS = "/WEB-INF/jsp/orders.jsp";

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private UserService.Authentication authentication;
    @Mock
    private OrderService orderService;
    @Mock
    private List<OrderViewDTO> orderViewDTOList;
    @Mock
    private javax.servlet.RequestDispatcher requestDispatcher;

    @InjectMocks
    private OrdersControllerGet ordersControllerGet;

    @Test
    public void givenAdminUser_WhenAdminUserRequestOrderList_ThenOrderServiceMustCallGetOrderViewDTOListMethodAndForwardedToOrdersPage() throws ServletException, IOException {
        UserEntity.Role admin = UserEntity.Role.ADMIN;
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("authentication")).thenReturn(authentication);
        Mockito.when(authentication.getRole()).thenReturn(admin);
        Mockito.when(orderService.getOrderViewDTOList()).thenReturn(orderViewDTOList);
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        ordersControllerGet.handle(request, response);

        Mockito.verify(orderService).getOrderViewDTOList();
        Mockito.verify(request).getRequestDispatcher(JSP_PATH_TO_ORDERS);
    }

    @Test
    public void givenCommonUser_WhenCommonUserRequestOrderList_ThenOrderServiceMustCallGetOrderViewDTOListByIdMethodAndForwardedToOrdersPage() throws ServletException, IOException {
        UserEntity.Role customer = UserEntity.Role.CUSTOMER;
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(session.getAttribute("authentication")).thenReturn(authentication);
        Mockito.when(authentication.getRole()).thenReturn(customer);
        Mockito.when(orderService.getOrderViewDTOListById(Mockito.anyString())).thenReturn(orderViewDTOList);
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        Mockito.when(authentication.getUserId()).thenReturn("id");

        ordersControllerGet.handle(request, response);

        Mockito.verify(orderService).getOrderViewDTOListById(Mockito.anyString());
        Mockito.verify(orderService, Mockito.atLeast(0)).getOrderViewDTOList();
        Mockito.verify(request).getRequestDispatcher(JSP_PATH_TO_ORDERS);
    }
}