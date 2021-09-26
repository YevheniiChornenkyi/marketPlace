package com.epam.yevheniy.chornenky.market.place.filters;

import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import com.epam.yevheniy.chornenky.market.place.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest {

    @Spy
    private SecurityFilter tested;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private HttpSession httpSession;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private UserService.Authentication authentication;
    @Mock
    private List<String> rolesList;

    //given_when_then
    @Test
    public void givensUnauthorizedUser_WhenPageRequireAdminRole_shouldRedirectToLoginPage() throws IOException, ServletException  {
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getRequestURI()).thenReturn("protected/resource");
        Mockito.when(tested.getFilterConfig()).thenReturn(filterConfig);
        Mockito.when(filterConfig.getInitParameter("GETprotected/resource")).thenReturn("ADMIN");
        Mockito.when(request.getSession()).thenReturn(httpSession);

        tested.doFilter(request, response, chain);

        Mockito.verify(response).sendRedirect("/action/login");
    }

    @Test
    public void givenAdminUser_WhenPageRequireAdminRole_ShouldPassThrough() throws ServletException, IOException {
        UserEntity.Role role = UserEntity.Role.ADMIN;
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getRequestURI()).thenReturn("protected/resource");
        Mockito.when(tested.getFilterConfig()).thenReturn(filterConfig);
        Mockito.when(filterConfig.getInitParameter("GETprotected/resource")).thenReturn("ADMIN");
        Mockito.when(request.getSession()).thenReturn(httpSession);
        Mockito.when(httpSession.getAttribute("authenticate")).thenReturn(authentication);
        Mockito.when(authentication.getRole()).thenReturn(role);
        Mockito.when(rolesList.contains("ADMIN")).thenReturn(true);

        tested.doFilter(request, response, chain);

        Mockito.verify(tested).doFilter(request, response, chain);
    }

    @Test
    public void givenUnauthorizedUser_WhenPageNotRequireRole_ShouldPassThrough() throws ServletException, IOException {
        Mockito.when(request.getMethod()).thenReturn("GET");
        Mockito.when(request.getRequestURI()).thenReturn("free/resources");
        Mockito.when(tested.getFilterConfig()).thenReturn(filterConfig);
        Mockito.when(filterConfig.getInitParameter("GETfree/resources")).thenReturn(null);

        tested.doFilter(request, response, chain);

        Mockito.verify(tested).doFilter(request, response, chain);
    }
}