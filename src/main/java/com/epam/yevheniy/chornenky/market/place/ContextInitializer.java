package com.epam.yevheniy.chornenky.market.place;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.repositories.GoodsRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.OrderRepository;
import com.epam.yevheniy.chornenky.market.place.repositories.UserRepository;
import com.epam.yevheniy.chornenky.market.place.services.*;
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
        private final ImageController imageController;
        private final CreateGoodsControllerPost createGoodsControllerPost;
        private final CreateGoodsControllerGet createGoodsControllerGET;
        private final AddItemToCartControllerPost addItemToCartControllerPost;
        private final CartGetController cartGetController;
        private final LogoutController logoutController;
        private final EditGetController editGetController;
        private final EditPOSTController editPOSTController;
        private final UsersTableControllerGet usersTableControllerGet;
        private final UsersTableControllerPost usersTableControllerPost;
        private final OrdersControllerPost ordersControllerPost;
        private final EmptyCartController emptyCartController;
        private final OrdersControllerGet ordersControllerGet;
        private final ActivationController activationController;

        private final Map<String, PageController> pageControllers;

        private Context(ServletConfig servletConfig) {
            this.servletConfig = servletConfig;
            connectionManager = instantiateConnectionManager();


            UserRepository userRepository = new UserRepository(connectionManager);
            GoodsRepository goodsRepository = new GoodsRepository(connectionManager);
            OrderRepository orderRepository = new OrderRepository(connectionManager);

            ImageService imageService = new ImageService(servletConfig.getInitParameter("iconHomeDirectoryPath"));
            EmailService emailService = new EmailService(servletConfig.getInitParameter("marketEmail"),
                    servletConfig.getInitParameter("marketEmailPass"),
                    servletConfig.getInitParameter("smtpHost"),
                    servletConfig.getInitParameter("hostPort"));
            UserService userService = new UserService(userRepository, emailService);
            GoodsService goodsService = new GoodsService(goodsRepository, imageService);
            OrderService orderService = new OrderService(orderRepository);

            authorizationController = new AuthorizationController(userService);
            homePageController = new HomePageController(goodsService);
            loginPageController = new LoginPageController();
            notFoundPageController = new NotFoundPageController();
            userRegistrationController = new UserRegistrationController(userService);
            registrationPageController = new RegistrationPageController();
            imageController = new ImageController(imageService);
            createGoodsControllerPost = new CreateGoodsControllerPost(goodsService);
            createGoodsControllerGET = new CreateGoodsControllerGet(goodsService);
            addItemToCartControllerPost = new AddItemToCartControllerPost(goodsService);
            cartGetController = new CartGetController();
            logoutController = new LogoutController();
            editGetController = new EditGetController(goodsService);
            editPOSTController = new EditPOSTController(goodsService);
            usersTableControllerGet = new UsersTableControllerGet(userService);
            usersTableControllerPost = new UsersTableControllerPost(userService);
            ordersControllerPost = new OrdersControllerPost(orderService);
            emptyCartController = new EmptyCartController();
            ordersControllerGet = new OrdersControllerGet(orderService);
            activationController = new ActivationController(userService);

            pageControllers = new HashMap<>();
            pageControllers.put("GET/action/login", loginPageController);
            pageControllers.put("GET/action/home-page", homePageController);
            pageControllers.put("GET/action/registration", registrationPageController);
            pageControllers.put("POST/action/login", authorizationController);
            pageControllers.put("POST/action/registration", userRegistrationController);
            pageControllers.put("GET/action/goods", createGoodsControllerGET);
            pageControllers.put("GET/action/image", imageController);
            pageControllers.put("POST/action/goods", createGoodsControllerPost);
            pageControllers.put("POST/action/home-page", addItemToCartControllerPost);
            pageControllers.put("GET/action/cart", cartGetController);
            pageControllers.put("GET/action/logout", logoutController);
            pageControllers.put("GET/action/edit", editGetController);
            pageControllers.put("POST/action/edit", editPOSTController);
            pageControllers.put("GET/action/users", usersTableControllerGet);
            pageControllers.put("POST/action/users", usersTableControllerPost);
            pageControllers.put("POST/action/orders", ordersControllerPost);
            pageControllers.put("GET/action/empty-cart", emptyCartController);
            pageControllers.put("GET/action/orders", ordersControllerGet);
            pageControllers.put("GET/action/activation", activationController);
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

