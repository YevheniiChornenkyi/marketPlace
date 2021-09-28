package com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils;

import com.epam.yevheniy.chornenky.market.place.exceptions.CartDoesNotExistException;
import com.epam.yevheniy.chornenky.market.place.exceptions.UserDoesNotAuthorizedException;
import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Contains methods for operations on the session
 */
public class SessionUtils {

    private static final String CART_KEY = "cart";

    /**
     * Extract cart from session
     * return new cart if cart does not exist
     * @param request HttpServletRequest
     * @return cart stored in session or new cart
     */
    public static Cart getCartFromRequestOrNew(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return Optional.ofNullable(session.getAttribute(CART_KEY))
                .filter(Cart.class::isInstance)
                .map(Cart.class::cast)
                .orElseGet(Cart::new);
    }

    /**
     * Save cart to session attribute
     * @param req HttpServletRequest
     * @param cart Cart
     */
    public static void saveCartToSession(HttpServletRequest req, Cart cart) {
        HttpSession session = req.getSession();
        session.setAttribute(CART_KEY, cart);
    }

    /**
     * extract user id from session if user authorized
     * @param request HttpServletRequest
     * @return user id
     */
    public static String getIdIfAuthorized(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(UserService.Authentication.class::cast)
                .map(UserService.Authentication::getUserId)
                .orElseThrow(UserDoesNotAuthorizedException::new);
    }

    /**
     * Extract cart from session
     * throw CartDoesNotExistException if not
     * @param request HttpServletRequest
     * @return cart
     */
    public static Cart getCartFromRequestOrThrow(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("cart"))
                .filter(Cart.class::isInstance)
                .map(Cart.class::cast)
                .orElseThrow(CartDoesNotExistException::new);
    }

    /**
     * Extract role from session
     * throw UserDoesNotAuthorizedException if not
     * @param request HttpServletRequest
     * @return Role
     */
    public static UserEntity.Role getRoleFromRequestOrThrow(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(authentication -> ((UserService.Authentication) authentication).getRole())
                .orElseThrow(UserDoesNotAuthorizedException::new);
    }

    /**
     * Extract role from session
     * return Optional<UserEntity.Role> null if session not have role
     * @param request HttpServletRequest
     * @return Optional<UserEntity.Role> empty if session not have role
     */
    public static Optional<UserEntity.Role> getRoleOptionalFromRequest(HttpServletRequest request) {
        return Optional.of(request)
                .map(HttpServletRequest::getSession)
                .map(session -> session.getAttribute("authentication"))
                .filter(UserService.Authentication.class::isInstance)
                .map(UserService.Authentication.class::cast)
                .map(UserService.Authentication::getRole);
    }
}
