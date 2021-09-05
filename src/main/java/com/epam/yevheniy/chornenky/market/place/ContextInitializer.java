package com.epam.yevheniy.chornenky.market.place;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.repositories.InMemoryUserRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.MySQLUserRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.UserRepository;
import com.epam.yevheniy.chornenky.market.place.services.UserService;
import com.epam.yevheniy.chornenky.market.place.servlet.controllers.*;

import javax.servlet.ServletConfig;
import java.util.HashMap;
import java.util.Map;

public class ContextInitializer {
    private ContextInitializer() {
    }

    public static Context initializeContext(ServletConfig servletConfig) {
        return new Context(servletConfig);
    }

    public static class Context {
        private final ServletConfig servletConfig;
        private final AuthorizationController authorizationController;
        private final HomePageController homePageController;
        private final LoginPageController loginPageController;
        private final NotFoundPageController notFoundPageController;
        private final UserRegistrationController userRegistrationController;
        private final RegistrationPageController registrationPageController;
        private final ConnectionManager connectionManager;

        private final Map<String, PageController> pageControllers;

        private Context(ServletConfig servletConfig) {
            this.servletConfig = servletConfig;
            connectionManager = instantiateConnectionManager();
            UserRepository userRepository = new MySQLUserRepository(connectionManager);
            UserService userService = new UserService(userRepository);
            authorizationController = new AuthorizationController(userService);

            homePageController = new HomePageController();

            loginPageController = new LoginPageController();

            notFoundPageController = new NotFoundPageController();

            userRegistrationController = new UserRegistrationController(userService);

            registrationPageController = new RegistrationPageController();

            pageControllers = new HashMap<>();

            pageControllers.put("GET/action/login", loginPageController);
            pageControllers.put("GET/action/home-page", homePageController);
            pageControllers.put("GET/action/registration", registrationPageController);
            pageControllers.put("POST/action/login", authorizationController);
            pageControllers.put("POST/action/registration", userRegistrationController);
        }

        public Map<String, PageController> getPageControllers() {
            return pageControllers;
        }

        public NotFoundPageController getNotFoundPageController() {
            return notFoundPageController;
        }

        private ConnectionManager instantiateConnectionManager() {
            String dbDriver = servletConfig.getInitParameter("dbDriver");
            String dbURL = servletConfig.getInitParameter("dbURL");
            String dbName = servletConfig.getInitParameter("dbName");
            String dbUserName = servletConfig.getInitParameter("dbUserName");
            String dbPassword = servletConfig.getInitParameter("dbPassword");
            return new ConnectionManager(dbDriver, dbURL, dbName, dbUserName, dbPassword);
        }
    }
}

