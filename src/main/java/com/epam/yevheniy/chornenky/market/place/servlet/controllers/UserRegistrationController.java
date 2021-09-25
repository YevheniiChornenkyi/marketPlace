package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.services.PasswordService;
import com.epam.yevheniy.chornenky.market.place.services.UserService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.validators.RegistrarValidator;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.UserRegistrationDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserRegistrationController extends PageController {

    private static final String URL_LOGIN_PAGE = "/login";
    private static final String URL_REGISTER_PAGE = "/registration";

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surName = req.getParameter("surname");
        String email = req.getParameter("email");
        String psw = req.getParameter("psw");
        String pswRepeat = req.getParameter("psw-repeat");
        try {
            validateRegistration(email, psw, pswRepeat);
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(name, surName, email, psw);
            userService.createUser(userRegistrationDTO);
            resp.sendRedirect(URL_LOGIN_PAGE + "?registration=true");
        }
        catch (ValidationException ex) {
            req.getSession().setAttribute("errorsMap", ex.getValidationMap());
            resp.sendRedirect(URL_REGISTER_PAGE);
        }
    }

    private void validateRegistration(String email, String psw, String pswRepeat) {
        Map<String, String> validationMap = new HashMap<>();
        if (!RegistrarValidator.loginValidate(email)) {
            validationMap.put("email", "msg.email-format-false");
        }
        if (!RegistrarValidator.pswValidate(psw)) {
            validationMap.put("psw", "msg.psw-format-false");
        }
        if (!RegistrarValidator.pswRepeatValidate(psw, pswRepeat)) {
            validationMap.put("pswRepeat", "msg.psw-mismatch");
        }
        if (!validationMap.isEmpty()) {
            throw new ValidationException(validationMap);
        }
    }
}
