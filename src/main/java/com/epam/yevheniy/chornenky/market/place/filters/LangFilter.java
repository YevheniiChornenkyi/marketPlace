package com.epam.yevheniy.chornenky.market.place.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class LangFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String lang = request.getParameter("lang");
        if (Objects.nonNull(lang)) {
            request.getSession().setAttribute("lang", lang);
            Locale.setDefault(new Locale(lang));
        }
        super.doFilter(request, response, chain);
    }
}
