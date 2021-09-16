package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.exceptions.DBException;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.epam.yevheniy.chornenky.market.place.repositories.entities.UserEntity.*;

public class UserRepository {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);
    private static final String GET_USER_BY_EMAIL_QUERY = "SELECT users.id, users.name, users.surname, users.psw, users.email, users.is_active, roles.role_name FROM users LEFT JOIN roles ON users.role_id=roles.id WHERE users.email=?";
    private static final String CREATE_NEW_USER_QUERY = "INSERT INTO users (id, name, surname, psw, role_id, email) VALUE (?, ?, ?, ?, ?, ?)";

    private final ConnectionManager connectionManager;

    public UserRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void createUser(UserEntity user) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_USER_QUERY);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurName());
            preparedStatement.setString(4, user.getPsw());
            preparedStatement.setString(5, user.getRole().getId());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot create new customer", e);
            throw new DBException();
        }
    }

    public Optional<UserEntity> findByEmail(String emailForSearch) {
        try (Connection con = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = con.prepareStatement(GET_USER_BY_EMAIL_QUERY);
            preparedStatement.setString(1, emailForSearch);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String surName = resultSet.getString("surname");
                String psw = resultSet.getString("psw");
                String roleName = resultSet.getString("role_name");
                Role role = Role.valueOf(roleName);
                String activity = resultSet.getString("is_active");
                boolean isActive = activity.equals("1");
                UserEntity userEntity = new UserEntity(name, surName, psw, emailForSearch, id, role, isActive);
                return Optional.of(userEntity);
            }
        } catch (SQLException e) {
            LOGGER.error(String.format("Database problem. Cannot perform search by email :%s", emailForSearch), e);
        }
        return Optional.empty();
    }
}



