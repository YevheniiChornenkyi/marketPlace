package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.AuthenticationException;
import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AuthorizationController implements PageController{

    private static final String JSP_PATH_TO_LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";
    private static final String URL_HOME_PAGE = "/home-page";

    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String psw = req.getParameter("psw");
        try {
            UserService.Authentication authentication = userService.authenticate(email, psw);
            HttpSession session = req.getSession();
            session.setAttribute("authentication", authentication);
            resp.sendRedirect(URL_HOME_PAGE);
        } catch (ValidationException e ) {
            req.setAttribute("errorsMap", e.getValidationMap());
            req.getRequestDispatcher(JSP_PATH_TO_LOGIN_PAGE).forward(req, resp);
        } catch (AuthenticationException e) {
            req.setAttribute("errorsMap", Map.of("email", e.getMessage()));
            req.getRequestDispatcher(JSP_PATH_TO_LOGIN_PAGE).forward(req, resp);
        }

        System.out.printf("");
    }
}

