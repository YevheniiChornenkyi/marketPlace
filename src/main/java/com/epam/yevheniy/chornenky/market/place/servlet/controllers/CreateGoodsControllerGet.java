package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CategoryDto;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.ManufacturerDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * used when requesting GET/action/goods
 * send to service request for all category and all manufacturer
 * set received data to session attribute
 * make forward to goods.jsp
 */
public class CreateGoodsControllerGet extends PageController {
    private final GoodsService goodsService;

    public static final String JSP_PATH = "/WEB-INF/jsp/goods.jsp";

    public CreateGoodsControllerGet(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CategoryDto> categoriesList = goodsService.getCategoriesDtoList();
        req.setAttribute("categoryList", categoriesList);
        List<ManufacturerDTO> manufacturersList = goodsService.getManufacturersDtoList();
        req.setAttribute("manufacturersList", manufacturersList);

        req.getRequestDispatcher(JSP_PATH).forward(req,resp);
    }
}
