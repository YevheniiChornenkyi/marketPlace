package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.ImageService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/**
 * used when requesting GET/action/image
 */
public class ImageController extends PageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public void before(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String imageName = req.getParameter("id").equals("") ? null : req.getParameter("id");
            byte[] imageByteArray = imageService.getImageById(imageName);
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.write(imageByteArray);
        } catch (FileNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void after(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Response already sent. So we cannot modify content type.
    }
}
