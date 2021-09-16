package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.models.Cart;
import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils.SessionUtils;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AddItemToCartControllerPost extends PageController {
    public static final String URL_TO_HOME_PAGE = "/home-page";
    private final GoodsService goodsService;

    public AddItemToCartControllerPost(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String goodsId = req.getParameter("id");
        Cart cart = SessionUtils.getCartFromRequest(req);
        Optional<GoodsViewDTO> goodsViewDTOOptional = goodsService.getById(goodsId);
        goodsViewDTOOptional.ifPresent(cart::addToCart);
        SessionUtils.saveCartToSession(req, cart);
        resp.sendRedirect(URL_TO_HOME_PAGE);
    }
}
