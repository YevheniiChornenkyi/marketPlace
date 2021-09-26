package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.UserDoesNotAuthorizedException;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.OrderEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.services.OrderService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils.SessionUtils;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.OrderViewDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OrdersControllerGet extends PageController{

    public static final String JSP_PATH_TO_LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String JSP_PATH_TO_ORDERS = "/WEB-INF/jsp/orders.jsp";
    private final OrderService orderService;

    public OrdersControllerGet(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            UserEntity.Role userRole = SessionUtils.getRoleFromRequestOrThrow(req);
            List<OrderViewDTO> orderViewDTOList;
            if (userRole.getId().equals("1")) {
                orderViewDTOList = orderService.getOrderViewDTOList();
                List<OrderEntity.Status> statuses = orderService.getAllStatuses();
                req.getSession().setAttribute("statuses", statuses);
            } else {
                String userId = SessionUtils.getIdIfAuthorized(req);
                orderViewDTOList = orderService.getOrderViewDTOListById(userId);
            }
            req.getSession().setAttribute("orderViewDTOList", orderViewDTOList);
            req.getRequestDispatcher(JSP_PATH_TO_ORDERS).forward(req, resp);
        } catch (UserDoesNotAuthorizedException e) {
            req.getSession().setAttribute("errorsMap", Map.of("authorization", "false"));
            req.getRequestDispatcher(JSP_PATH_TO_LOGIN).forward(req, resp);
        }
    }
}
