package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.CreateGoodsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class CreateGoodsControllerPost implements PageController{

    private static final List<String> EXTENSIONS_ALLOWED = List.of("png", "jpeg", "jpg");
    public static final String IS_NUMBER_REGEX = "^\\d+(?:[.,]\\d{1,2})?$";

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
        //TODO need to validate name not empty
        //price not null can parse to big decimal use regex
        //category not null
        //manufacturer not null
        //front-end and back-end implements
        //category is a number validation
        //manufacturer is a number validation

        Part imagePart = req.getPart("file");
        String fileName = imagePart.getSubmittedFileName();

        try {
            validate(price, fileName);
            InputStream inputStream = imagePart.getInputStream();
            byte[] byteImageArray = inputStream.readAllBytes();
            CreateGoodsDTO createGoodsDTO = new CreateGoodsDTO(name, model, price, category,
                    description, manufacturer, byteImageArray);
            goodsService.createGoods(createGoodsDTO);
            resp.sendRedirect("/goods?created=true");
        } catch (ValidationException e) {
            req.setAttribute("errorsMap", e.getValidationMap());
            req.getRequestDispatcher("/WEB-INF/jsp/goods.jsp").forward(req, resp);
        }



        //TODO validation not exe validation. not null validation
        //TODO getname y part







    }

    private boolean isPicture(String fileName) {
        String[] split = fileName.split("\\.");
        if (split.length > 0) {
            String extension = split[split.length - 1].toLowerCase();
            return EXTENSIONS_ALLOWED.contains(extension);
        }
        return false;
    }

    private void validate(String price, String fileName){
        Map<String, String> validationMap = new HashMap<>();
        if (!price.matches(IS_NUMBER_REGEX)) {
            validationMap.put("price", "Incorrect data, enter the cost of the product.");
        }
        if (!isPicture(fileName)) {
            validationMap.put("image", "Incorrect image extension, select picture with next extensions: png, jpeg, jpg");
        }
        if (!validationMap.isEmpty()) {
            throw new ValidationException(validationMap);
        }
    }

}
