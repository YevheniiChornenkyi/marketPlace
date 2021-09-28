package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators.GoodsValidator;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateGoodsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 *  used when requesting POST/action/goods
 *  receive from request new goods parameter and create createGoodsDTO
 *  make simple backend validation if fail set to session attribute errorMap and redirect to goods page
 *  send redirect to order page with created=true parameters
 */
public class CreateGoodsControllerPost extends PageController {

    public static final String URL_TO_GOODS = "/goods";

    private final GoodsService goodsService;

    public CreateGoodsControllerPost(GoodsService goodsService) {
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

        Part imagePart = req.getPart("file");
        String fileName = imagePart.getSubmittedFileName();

        try {
            GoodsValidator.validatePriceImage(price, fileName);
            InputStream inputStream = imagePart.getInputStream();
            byte[] byteImageArray = inputStream.readAllBytes();
            CreateGoodsDTO createGoodsDTO = new CreateGoodsDTO(name, model, price, category,
                    description, manufacturer, byteImageArray);
            goodsService.createGoods(createGoodsDTO);
            resp.sendRedirect("/goods?created=true");
        } catch (ValidationException e) {
            req.getSession().setAttribute("errorsMap", e.getValidationMap());
            resp.sendRedirect(URL_TO_GOODS);
        }
    }

}
