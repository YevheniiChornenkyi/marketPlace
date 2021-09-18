package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersTableControllerGet extends PageController{

    private final UserService userService;

    public UsersTableControllerGet(UserService userService) {
        this.userService = userService;
    }

    public static final String JSP_PATH = "/WEB-INF/jsp/users.jsp";

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usersDtoList", userService.getUsersDtoList());
        req.getRequestDispatcher(JSP_PATH).forward(req, resp);
    }
}
