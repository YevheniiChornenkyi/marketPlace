package com.epam.yevheniy.chornenky.market.place.servlet.controllers;

import com.epam.yevheniy.chornenky.market.place.exceptions.ValidationException;
import com.epam.yevheniy.chornenky.market.place.repositories.UserRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.services.EmailService;
import com.epam.yevheniy.chornenky.market.place.services.PasswordService;
import com.epam.yevheniy.chornenky.market.place.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationControllerTest {

    public static final String URL_TO_LOGIN = "/login";
    public static final String UTL_TO_HOME_PAGE = "/home-page";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private UserService.Authentication authentication;
    @Mock
    private UserService userService;

    private AuthorizationController tested;

    @Before
    public void setUp() throws Exception {
        tested = new AuthorizationController(userService);
    }

    @Test
    public void givenWrongFormatPassword_WhenMethodCheckPasswordFormat_ThenShouldAppendValidationMessageToSessionAndRedirectUser() throws ServletException, IOException {
        Mockito.when(request.getParameter("psw")).thenReturn("login");
        Mockito.when(request.getSession()).thenReturn(session);

        tested.handle(request, response);

        Mockito.verify(session).setAttribute("errorsMap", Map.of("psw", "msg.psw-format-false"));
        Mockito.verify(response).sendRedirect(URL_TO_LOGIN);
    }

    @Test
    public void givenHappyPathUser_WhenMethodCheckUserParameter_ThenShouldSetAuthenticationObjectToSessionRedirectToHomepage() throws ServletException, IOException {
        Mockito.when(request.getParameter("email")).thenReturn("email");
        Mockito.when(request.getParameter("psw")).thenReturn("login1");
        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(userService.authenticate("email", "login1")).thenReturn(authentication);
        UserEntity.Role role = UserEntity.Role.CUSTOMER;
        Mockito.when(authentication.getRole()).thenReturn(role);

        tested.handle(request, response);

        Mockito.verify(session).setAttribute("authentication", authentication);
        Mockito.verify(response).sendRedirect(UTL_TO_HOME_PAGE);
    }
}