package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils.SessionUtils;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class AddItemToCartControllerPost extends PageController {
    private static final String URL_TO_HOME_PAGE = "/home-page";
    private static final String URL_TO_CART = "/cart";
    private static final String DEFAULT_ACTION = "";
    private final GoodsService goodsService;
    private final Map<String, CartAction> cartActionsMap;

    public AddItemToCartControllerPost(GoodsService goodsService) {
        cartActionsMap = Map.of("up", this::increaseGoodsCount, "down",
                this::decreaseGoodsCount, "delete", this::deleteGoods);
        this.goodsService = goodsService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String goodsId = req.getParameter("id");
        String action = Optional.ofNullable(req.getParameter("count")).orElse(DEFAULT_ACTION);
        Cart cart = SessionUtils.getCartFromRequestOrNew(req);

        cartActionsMap.getOrDefault(action, this::addToCart).action(req, resp, goodsId, cart);

    }

    private void increaseGoodsCount(HttpServletRequest req, HttpServletResponse resp, String goodsId, Cart cart) throws IOException {
        cart.increaseGoodsCount(Integer.parseInt(goodsId));
        SessionUtils.saveCartToSession(req, cart);
        resp.sendRedirect(URL_TO_CART);
    }

    private void decreaseGoodsCount(HttpServletRequest req, HttpServletResponse resp, String goodsId, Cart cart) throws IOException {
        cart.decreaseGoodsCount(Integer.parseInt(goodsId));
        SessionUtils.saveCartToSession(req, cart);
        resp.sendRedirect(URL_TO_CART);
    }

    private void deleteGoods(HttpServletRequest req, HttpServletResponse resp, String goodsId, Cart cart) throws IOException {
        cart.deleteGoods(Integer.parseInt(goodsId));
        SessionUtils.saveCartToSession(req, cart);
        resp.sendRedirect(URL_TO_CART);
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp, String goodsId, Cart cart) throws IOException {
        Optional<GoodsViewDTO> goodsViewDTOOptional = goodsService.getById(goodsId);
        goodsViewDTOOptional.ifPresent(cart::addToCart);
        SessionUtils.saveCartToSession(req, cart);
        resp.sendRedirect(URL_TO_HOME_PAGE);
    }

    interface CartAction {
        void action(HttpServletRequest request, HttpServletResponse response, String goodsId, Cart cart) throws IOException;
    }
}
