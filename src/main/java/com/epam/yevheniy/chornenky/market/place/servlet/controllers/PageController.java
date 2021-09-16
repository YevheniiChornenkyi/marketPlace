package com.epam.yevheniy.chornenky.market.place.servlet.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class PageController {

    protected Logger LOGGER = LogManager.getLogger(this.getClass());

    abstract protected void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        before(req, resp);
        handle(req, resp);
        after(req, resp);
    }

    protected void before(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Start processing new request with URL:{}, and HTTP method: {}", req.getRequestURL(), req.getMethod());
    }

    protected void after(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
    }
}
