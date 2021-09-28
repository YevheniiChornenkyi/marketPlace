package com.epam.yevheniy.chornenky.market.place.db;

import com.epam.yevheniy.chornenky.market.place.exceptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static boolean dbDriverLoaded = false;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    private final String dpDriver;
    private final String dbURL;
    private final String dbName;
    private final String dbUsername;
    private final String dbPassword;

    public ConnectionManager(String dpDriver, String dbURL, String dbName, String dbUsername, String dbPassword) {
        this.dpDriver = dpDriver;
        this.dbURL = dbURL;
        this.dbName = dbName;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        LOGGER.debug("New connection manager has been created");
    }

    /**
     * dbDriver/dbURL/dbName/dbUsername/dbPassword indicate in web.xml
     * @return new connection go database.
     */
    public Connection getConnection() {
        try {
            if (!dbDriverLoaded) {
                Class.forName(dpDriver);
                dbDriverLoaded = true;
            }
            return DriverManager.getConnection(dbURL + dbName, dbUsername, dbPassword);
        } catch (SQLException | ClassNotFoundException throwables) {
            throw new DBException();
        }
    }
}
