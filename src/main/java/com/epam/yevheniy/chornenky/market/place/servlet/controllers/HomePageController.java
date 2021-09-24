package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateSiteFilterDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

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
        String minPrice = req.getParameter("minPrice");
        String maxPrice = req.getParameter("maxPrice");
        String[] categories = req.getParameterValues("categories");
        CreateSiteFilterDTO createSiteFilterDTO = getCreateSiteFilterDto(sort, order, categories, minPrice, maxPrice);

        req.setAttribute("categoriesList", goodsService.getCategoriesDtoList());
        req.setAttribute("goodsViewDTOList", goodsService.getSortedGoodsViewDTOList(createSiteFilterDTO));
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }

    private CreateSiteFilterDTO getCreateSiteFilterDto(String sort, String order, String[] categories, String minPrice, String maxPrice) {
        minPrice = Objects.nonNull(minPrice) ? minPrice : "0";
        maxPrice = Objects.nonNull(maxPrice) ? maxPrice : "";
        if (!maxPrice.equals("") && Integer.parseInt(maxPrice) < Integer.parseInt(minPrice)) {
            maxPrice = minPrice;
        }
        return new CreateSiteFilterDTO(sort, order, minPrice, maxPrice, categories);
    }


}
