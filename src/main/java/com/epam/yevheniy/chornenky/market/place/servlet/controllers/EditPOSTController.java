package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.services.GoodsService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators.GoodsValidator;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.EditGoodsDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * used when requesting POST/action/edit
 * receive from request goods new parameters and create goodsCreateDTO
 * make simple image and price validation if fail set attribute to session exceptionMap. Send redirect to edit page
 * send to goodsService created goodsCreateDTO
 * send redirect to edit with parameter created=true
 */
public class EditPOSTController extends PageController {

    public static final String URL_TO_EDIT = "/edit";
    private final GoodsService goodsService;

    public EditPOSTController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        LOGGER.info("Start editing goods with id: {}", id);
        String name = req.getParameter("name");
        String model = req.getParameter("model");
        String price = req.getParameter("price");
        String oldImageName = req.getParameter("oldImageName");
        int category = Integer.parseInt(req.getParameter("category"));
        String description = req.getParameter("description");
        int manufacturer = Integer.parseInt(req.getParameter("manufacturer"));
        Part part = req.getPart("file");
        String fileName = part.getSubmittedFileName();
        byte[] byteImageArray = null;
        try {
            if (fileName.equals("")) {
                GoodsValidator.validatePrice(price);
            } else {
                GoodsValidator.validatePriceImage(price, fileName);
                InputStream inputStream = part.getInputStream();
                byteImageArray = inputStream.readAllBytes();
            }
            EditGoodsDTO editGoodsDTO = new EditGoodsDTO(id, name, model, price, category, description, manufacturer, byteImageArray, oldImageName);
            goodsService.editGoods(editGoodsDTO);
            LOGGER.info("Successfully completed editing goods with id: {}", id);
            resp.sendRedirect("/edit?created=true");
        } catch (ValidationException e) {
            LOGGER.info("Cannot finish editing goods with id: {}", id);
            req.getSession().setAttribute("errorsMap", e.getValidationMap());
            resp.sendRedirect(URL_TO_EDIT);
        }
    }
}
