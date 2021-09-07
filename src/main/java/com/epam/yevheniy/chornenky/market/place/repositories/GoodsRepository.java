package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.exceptions.DBException;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.CategoryEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.GoodsEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.ManufacturerEntity;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsRepository {

    public static final String FIND_ALL_QUERY = "SELECT goods.id AS goods_id, goods.model, goods.price, categories.category_name, categories.id AS category_id, goods.icon_path, goods.description, manufacturers.manufacturer_name, manufacturers.id AS manufacturer_id , goods.created FROM goods LEFT JOIN categories ON goods.category=categories.id LEFT JOIN manufacturers ON goods.manufacturer=manufacturers.id";
    private static final Logger LOGGER = Logger.getLogger(GoodsRepository.class);

    private final ConnectionManager connectionManager;

    public GoodsRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<GoodsEntity> findAll() {
        List<GoodsEntity> goodsEntityList = new ArrayList<>();

        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CategoryEntity categoryEntity = extractCategory(resultSet);
                ManufacturerEntity manufacturerEntity = extractManufacturer(resultSet);
                GoodsEntity goodsEntity = createGoodsEntity(resultSet, categoryEntity, manufacturerEntity);
                goodsEntityList.add(goodsEntity);
            }
        } catch (SQLException e) {
            LOGGER.error("Problem in goods repository", e);
            throw new DBException();
        }
        return goodsEntityList;
    }

    private GoodsEntity createGoodsEntity(ResultSet resultSet, CategoryEntity category,
                                          ManufacturerEntity manufacturer) throws SQLException {

        String model = resultSet.getString("model");
        int id = resultSet.getInt("goods_id");
        String price = resultSet.getString("price");
        String iconPath = resultSet.getString("icon_path");
        String description = resultSet.getString("description");
        Timestamp created = resultSet.getTimestamp("created");

        return new GoodsEntity(model, id, price, category, iconPath, description, manufacturer, created);
    }

    private CategoryEntity extractCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("category_id");
        String name = resultSet.getString("category_name");
        return new CategoryEntity(id, name);
    }

    private ManufacturerEntity extractManufacturer(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("manufacturer_id");
        String name = resultSet.getString("manufacturer_name");
        return new ManufacturerEntity(id, name);
    }
}
