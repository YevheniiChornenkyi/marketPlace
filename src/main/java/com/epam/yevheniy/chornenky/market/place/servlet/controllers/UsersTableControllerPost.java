package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class UsersTableControllerPost extends PageController{

    public static final String URL_TO_USERS = "/users";
    private final UserService userService;

    public UsersTableControllerPost(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String banFlag = req.getParameter("is_active");
        if (Objects.nonNull(banFlag)) {
            userService.blockDispatcher(banFlag, req.getParameter("userId"));
        }
        resp.sendRedirect(URL_TO_USERS);
    }
}
