package com.epam.yevheniy.chornenky.market.place.filters;

import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.utils.SessionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SecurityFilter extends HttpFilter {

    public static final String URL_TO_LOGIN = "/action/login";
    public static final String NOT_FOUND = "/action/not-found";

    /**
     * This filter performs validation of user role for protected resources
     * @param request - HTTP servlet request
     * @param response - HTTP servlet response
     * @param chain - Filter chain
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String key = method + requestURI;
        String requiredRoles = getInitParameter(key);
        if (Objects.isNull(requiredRoles)) {
            super.doFilter(request, response, chain);
            return;
        }
        Optional<UserEntity.Role> role = SessionUtils.getRoleOptionalFromRequest(request);

        if (role.isEmpty()) {
            response.sendRedirect(URL_TO_LOGIN);
            return;
        }
        String[] rolesArray = requiredRoles.split(" ");
        List<String> rolesList = Arrays.asList(rolesArray);
        if (rolesList.contains(role.get().toString())) {
            super.doFilter(request, response, chain);
            return;
        }
        response.sendRedirect(NOT_FOUND);
    }
}
