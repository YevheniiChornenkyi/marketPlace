package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.CartDoesNotExistException;
import com.epam.yevheniy.chornenky.market.place.exceptions.OrderException;
import com.epam.yevheniy.chornenky.market.place.exceptions.UserDoesNotAuthorizedException;
import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.services.OrderService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils.SessionUtils;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateOrderDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class OrdersControllerPost extends PageController {

    public static final String URL_TO_LOGIN = "/login";
    public static final String ERROR = "/error-page";
    public static final String URL_TO_EMPTY_CART = "/empty-cart";

    private final OrderService orderService;

    public OrdersControllerPost(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            String userId = SessionUtils.getIdIfAuthorized(req);
            String action = req.getParameter("orderNote");
            if (action.equals("newOrder")) {
                Cart cart = SessionUtils.getCartFromRequestOrThrow(req);
                String address = req.getParameter("address");
                String phoneNumber = req.getParameter("phoneNumber");
                CreateOrderDTO createOrderDTO = new CreateOrderDTO(userId, address, phoneNumber, cart);
                orderService.createOrder(createOrderDTO);
                session.removeAttribute("cart");
                session.removeAttribute("orderNote");
                resp.sendRedirect("/cart?order=true");
            } else if (action.equals("newStatus")) {
                String newStatus = req.getParameter("status");
                String orderId = req.getParameter("orderId");
                orderService.changeOrderStatusById(orderId, newStatus);
                resp.sendRedirect("/orders");
            }
        } catch (UserDoesNotAuthorizedException e) {
            session.setAttribute("errorsMap", Map.of("authorization", "false"));
            resp.sendRedirect(URL_TO_LOGIN);
        } catch (CartDoesNotExistException e) {
            resp.sendRedirect(URL_TO_EMPTY_CART);
        } catch (OrderException e) {
            LOGGER.error(e.getMessage());
            resp.sendRedirect(ERROR);
        }
    }
}
