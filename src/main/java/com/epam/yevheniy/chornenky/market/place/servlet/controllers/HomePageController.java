package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomePageController extends PageController {

    private static final String JSP_PATH = "/WEB-INF/jsp/home-page.jsp";

    private final GoodsService goodsService;

    public HomePageController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sort = req.getParameter("sort");
        String order = req.getParameter("order");
        String[] categories = req.getParameterValues("categories");

        req.setAttribute("categoriesList", goodsService.getCategoriesDtoList());
        req.setAttribute("goodsViewDTOList", goodsService.getSortedGoodsViewDTOList(sort, order, categories));
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }
}
