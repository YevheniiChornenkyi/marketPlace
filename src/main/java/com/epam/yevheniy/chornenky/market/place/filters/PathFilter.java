package com.epam.yevheniy.chornenky.market.place.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class PathFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/")) {
            response.sendRedirect("action/home-page");
            return;
        }
        if (!requestURI.startsWith("/static/") && !requestURI.startsWith("/action")) {
            String requestParameters = getRequestParametersOrEmpty(request);
            response.sendRedirect("/action" + requestURI + requestParameters);
            return;
        }
        super.doFilter(request, response, chain);
    }

    private String getRequestParametersOrEmpty(HttpServletRequest request) {
        return Optional.ofNullable(request.getQueryString())
                .map(parameters -> "?" + parameters)
                .orElse("");
    }


}





