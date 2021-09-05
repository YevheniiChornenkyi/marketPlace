package com.epam.yevheniy.chornenky.market.place.servlet;

import com.epam.yevheniy.chornenky.market.place.ContextInitializer;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestDispatcher extends HttpServlet {
    private ContextInitializer.Context context;
    private Map<String, PageController> pageControllers= new HashMap<>();

    @Override
    public void init() throws ServletException {
        ServletConfig servletConfig = getServletConfig();
        context = ContextInitializer.initializeContext(servletConfig);
        this.pageControllers = context.getPageControllers();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        String requestURI = req.getRequestURI();
        String key = method + requestURI;
        PageController pageController = pageControllers.getOrDefault(key, context.getNotFoundPageController());
        pageController.handle(req, resp);
    }
}
