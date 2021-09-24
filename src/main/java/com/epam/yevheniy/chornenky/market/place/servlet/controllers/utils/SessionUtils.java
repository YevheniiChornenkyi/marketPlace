package com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils;

import com.epam.yevheniy.chornenky.market.place.exceptions.CartDoesNotExistException;
import com.epam.yevheniy.chornenky.market.place.exceptions.OrderException;
import com.epam.yevheniy.chornenky.market.place.exceptions.UserDoesNotAuthorizedException;
import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

public class SessionUtils {

    private static final String CART_KEY = "cart";

    public static Cart getCartFromRequestOrNew(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Optional.ofNullable(session.getAttribute(CART_KEY))
                .filter(Cart.class::isInstance)
                .map(Cart.class::cast)
                .orElseGet(Cart::new);
    }

    public static void saveCartToSession(HttpServletRequest req, Cart cart) {
        HttpSession session = req.getSession();
        session.setAttribute(CART_KEY, cart);
    }

    public static String getUserIdFromSession(HttpSession session) {
        return Optional.ofNullable(session).map((httpSession) -> httpSession.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(UserService.Authentication.class::cast)
                .map(UserService.Authentication::getUserId)
                .orElseThrow(() -> new OrderException("User does not authorized"));
    }

    public static String getIdIfAuthorized(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(UserService.Authentication.class::cast)
                .map(UserService.Authentication::getUserId)
                .orElseThrow(UserDoesNotAuthorizedException::new);
    }

    public static Cart getCartFromRequestOrThrow(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("cart"))
                .filter(Cart.class::isInstance)
                .map(Cart.class::cast)
                .orElseThrow(CartDoesNotExistException::new);
    }

    public static UserEntity.Role getRoleFromRequestOrThrow(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(authentication -> ((UserService.Authentication) authentication).getRole())
                .orElseThrow(UserDoesNotAuthorizedException::new);
    }

    public static Optional<UserEntity.Role> getRoleOptionalFromRequest(HttpServletRequest request) {
        return Optional.of(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(UserService.Authentication.class::cast)
                .map(UserService.Authentication::getRole);
    }
}
