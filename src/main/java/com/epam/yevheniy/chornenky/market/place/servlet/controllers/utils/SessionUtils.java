package com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils;

import com.epam.yevheniy.chornenky.market.place.models.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtils {

    private static final String CART_KEY = "cart";

    public static Cart getCartFromRequest(HttpServletRequest request) {
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
}
