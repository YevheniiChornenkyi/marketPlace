package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * used when requesting GET/action/cart
 * make forward to cart.jsp
 */
public class CartGetController extends PageController{

    public static final String JSP_PATH = "/WEB-INF/jsp/cart.jsp";

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }
}
