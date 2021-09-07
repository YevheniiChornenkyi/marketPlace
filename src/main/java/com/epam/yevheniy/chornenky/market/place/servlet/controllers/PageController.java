package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PageController {

    void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    default void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        before(req, resp);
        handle(req, resp);
        after(req, resp);
    }

    default void before(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    default void after(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
    }
}
