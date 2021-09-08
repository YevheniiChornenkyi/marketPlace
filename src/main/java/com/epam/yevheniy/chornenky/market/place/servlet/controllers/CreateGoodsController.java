package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateGoodsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

public class CreateGoodsController implements PageController{
    private final GoodsService goodsService;

    public CreateGoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String model = req.getParameter("model");
        String price = req.getParameter("price");
        int category = Integer.parseInt(req.getParameter("category"));
        String description = req.getParameter("description");
        int manufacturer = Integer.parseInt(req.getParameter("manufacturer"));
        //TODO need to validate name not empty
        //price not null can parse to big decimal use regex
        //category not null
        //manufacturer not null
        //front-end and back-end implements
        //category is a number validation
        //manufacturer is a number validation

        Part imagePart = req.getPart("image");
        //TODO validation not exe validation. not null validation
        //TODO getname y part

        byte[] image = imagePart.getInputStream().readAllBytes();

        CreateGoodsDTO createGoodsDTO = new CreateGoodsDTO(name, model, price, category, description, manufacturer, image);
        goodsService.createGoods(createGoodsDTO);

        resp.sendRedirect("/goods?created=true");
    }
}
