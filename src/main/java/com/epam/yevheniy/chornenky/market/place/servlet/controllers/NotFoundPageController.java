package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NotFoundPageController extends PageController {

    private static final String JSP_PATH = "/WEB-INF/jsp/not-found.jsp";

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }
}
