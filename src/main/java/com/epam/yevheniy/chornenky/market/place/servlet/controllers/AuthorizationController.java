package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.AuthenticationException;
import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.services.UserService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators.RegistrarValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * used when requesting POST/action/login
 * receive from request email
 * receive from request psw
 * send received data to service
 * received from service authentication object set to session attribute
 * send redirect to homePage
 * if service throw exception set to session exceptionMap and redirect to login page
 */
public class AuthorizationController extends PageController {


    private static final String URL_HOME_PAGE = "/home-page";
    public static final String URL_TO_LOGIN = "/login";

    private final UserService userService;

    public AuthorizationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String psw = req.getParameter("psw");
        HttpSession session = req.getSession();
        try {
            RegistrarValidator.pswValidateThrow(psw);
            UserService.Authentication authentication = userService.authenticate(email, psw);
            isActive(authentication);
            session.setAttribute("authentication", authentication);
            resp.sendRedirect(URL_HOME_PAGE);
        } catch (ValidationException e ) {
            session.setAttribute("errorsMap", e.getValidationMap());
            resp.sendRedirect(URL_TO_LOGIN);
        } catch (AuthenticationException e) {
            session.setAttribute("errorsMap", Map.of("email", e.getMessage()));
            resp.sendRedirect(URL_TO_LOGIN);
        }
    }

    private void isActive(UserService.Authentication authentication) {
        if (authentication.getRole().getId().equals("3")) {
            throw new ValidationException(Map.of("activation", "false"));
        }
    }
}

