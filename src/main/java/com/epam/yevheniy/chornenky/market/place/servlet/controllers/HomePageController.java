package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageController implements PageController{

    private static final String JSP_PATH = "/WEB-INF/jsp/home-page.jsp";

    private final GoodsService goodsService;

    public HomePageController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("goodsViewDTOList", goodsService.getGoodsViewDTOList());
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }
}
