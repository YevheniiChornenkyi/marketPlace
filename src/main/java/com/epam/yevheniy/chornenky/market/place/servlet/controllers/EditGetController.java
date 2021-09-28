package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CategoryDto;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.ManufacturerDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * used when requesting GET/action/edit
 * received from request goodsId
 * send to service request for all category, all manufacturer, goodsDto with received id
 * set received data to session attribute
 * make forward to edit.jsp
 */
public class EditGetController extends PageController{
    public static final String JSP_EDIT = "/WEB-INF/jsp/edit.jsp";
    private final GoodsService goodsService;

    public EditGetController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("goodsId");
        LOGGER.info("Start editing goods with id {}", id);
        List<CategoryDto> categoriesDtoList = goodsService.getCategoriesDtoList();
        List<ManufacturerDTO> manufacturersDtoList = goodsService.getManufacturersDtoList();
        Optional<GoodsViewDTO> goodsViewDTOOptional = goodsService.getById(id);

        HttpSession session = req.getSession();
        goodsViewDTOOptional.ifPresent(dto -> session.setAttribute("goodsToEdit", dto));
        session.setAttribute("categoryList", categoriesDtoList);
        session.setAttribute("manufacturersList", manufacturersDtoList);
        req.getRequestDispatcher(JSP_EDIT).forward(req, resp);
    }
}
