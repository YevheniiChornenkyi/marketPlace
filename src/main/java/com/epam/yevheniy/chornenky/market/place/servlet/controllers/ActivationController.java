package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.ActivationException;
import com.epam.yevheniy.chornenky.market.place.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActivationController extends PageController{

    public static final String JSP_TO_ACTIVATION = "/WEB-INF/jsp/activation.jsp";
    private final UserService userService;

    public ActivationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key");
        try {
            userService.activateUser(key);
            req.getSession().setAttribute("activation", "true");
            req.getRequestDispatcher(JSP_TO_ACTIVATION).forward(req, resp);
        } catch (ActivationException e) {
            req.getSession().setAttribute("activation", "false");
            req.getRequestDispatcher(JSP_TO_ACTIVATION).forward(req, resp);
        }
    }
}
