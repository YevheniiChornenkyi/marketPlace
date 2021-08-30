package com.epam.yevheniy.chornenky.market.place.servlet;

import com.epam.yevheniy.chornenky.market.place.servlet.controllers.LoginPageController;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.NotFoundPageController;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.PageController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher extends HttpServlet {
    private static Map<String, PageController> pageControllers= new HashMap<>();
    private static final PageController pageNotFoundController = new NotFoundPageController();

    static {
        pageControllers.put("GET/action/login", new LoginPageController());
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String requestURI = req.getRequestURI();
        String key = method + requestURI;
        PageController pageController = pageControllers.getOrDefault(key, pageNotFoundController);
        pageController.handle(req, resp);

    }
}
