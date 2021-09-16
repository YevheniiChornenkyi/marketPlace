package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.exceptions.DBException;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.CategoryEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.GoodsEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.ManufacturerEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GoodsRepository {

    public static final String FIND_ALL_QUERY = "SELECT goods.name, goods.id AS goods_id, goods.model, goods.price, categories.category_name, categories.id AS category_id, goods.image_name, goods.description, manufacturers.manufacturer_name, manufacturers.id AS manufacturer_id , goods.created FROM goods LEFT JOIN categories ON goods.category=categories.id LEFT JOIN manufacturers ON goods.manufacturer=manufacturers.id";
    public static final String FIND_GOODS_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE goods.id=?";
    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);
    public static final String INSERT_GOODS_QUERY = "INSERT INTO goods (name, model, price, category, image_name, description, manufacturer) VALUE (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_CATEGORY_BY_ID_QUERY = "SELECT categories.category_name, categories.id FROM categories WHERE id=?";
    public static final String FIND_MANUFACTURER_BY_ID_QUERY = "SELECT manufacturers.id, manufacturers.manufacturer_name FROM manufacturers WHERE id=?";
    public static final String FIND_ALL_CATEGORIES_QUERY = "SELECT * FROM categories";
    public static final String FIND_ALL_MANUFACTURERS_QUERY = "SELECT manufacturers.id, manufacturers.manufacturer_name FROM manufacturers";
    public static final String EDIT_GOODS_QUERY = "UPDATE goods SET name=?, model=?, price=?, category=?, image_name=?, description=?, manufacturer=?, created=? WHERE id=?";

    private final ConnectionManager connectionManager;

    public GoodsRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<GoodsEntity> findAll() {
        List<GoodsEntity> goodsEntityList = new ArrayList<>();

        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CategoryEntity categoryEntity = extractCategory(resultSet);
                ManufacturerEntity manufacturerEntity = extractManufacturer(resultSet);
                GoodsEntity goodsEntity = createGoodsEntity(resultSet, categoryEntity, manufacturerEntity);
                goodsEntityList.add(goodsEntity);
            }
        } catch (SQLException e) {
            LOGGER.debug("Problem in goods repository", e);
            throw new DBException();
        }
        return goodsEntityList;
    }

    private GoodsEntity createGoodsEntity(ResultSet resultSet, CategoryEntity category,
                                          ManufacturerEntity manufacturer) throws SQLException {

        String name = resultSet.getString("name");
        String model = resultSet.getString("model");
        int id = resultSet.getInt("goods_id");
        String price = resultSet.getString("price");
        String imageName = resultSet.getString("image_name");
        String description = resultSet.getString("description");
        Timestamp created = resultSet.getTimestamp("created");

        return new GoodsEntity(name, model, id, price, category, imageName, description, manufacturer, created);
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

    public Optional<ManufacturerEntity> findManufacturerById(int manufacturer) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_MANUFACTURER_BY_ID_QUERY);
            preparedStatement.setInt(1, manufacturer);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("manufacturer_name");
                ManufacturerEntity manufacturerEntity = new ManufacturerEntity(id, name);
                return Optional.of(manufacturerEntity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Cannot establish connection to database, when searching manufacturer by id", e);
            throw new DBException();
        }
    }

    public Optional<CategoryEntity> findCategoryById(int category) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CATEGORY_BY_ID_QUERY);
            preparedStatement.setInt(1, category);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String categoryName = resultSet.getString("category_name");
                CategoryEntity categoryEntity = new CategoryEntity(id, categoryName);
                return Optional.of(categoryEntity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Cannot establish connection to database, when searching category by id .", e);
            throw new DBException();
        }
    }

    public void createGoods(GoodsEntity goodsEntity) {
        String name = goodsEntity.getName();
        String model = goodsEntity.getModel();
        String price = goodsEntity.getPrice();
        int category = goodsEntity.getCategory().getId();
        String imageName = goodsEntity.getImageName();
        String description = goodsEntity.getDescription();
        int manufacturer = goodsEntity.getManufacturer().getId();
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GOODS_QUERY);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, price);
            preparedStatement.setInt(4, category);
            preparedStatement.setString(5, imageName);
            preparedStatement.setString(6, description);
            preparedStatement.setInt(7, manufacturer);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Cannot establish connection to database when created goods.", e);
            throw new DBException();
        }
    }

    public List<CategoryEntity> findAllCategories() {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CATEGORIES_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<CategoryEntity> categoryEntityList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("category_name");
                categoryEntityList.add(new CategoryEntity(id, name));
            }
            return categoryEntityList;
        } catch (SQLException e) {
            LOGGER.error("Database problems. cannot get categories table", e);
            throw new DBException();
        }
    }

    public List<ManufacturerEntity> findAllManufacturers() {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_MANUFACTURERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ManufacturerEntity> manufacturersEntityList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("manufacturer_name");
                manufacturersEntityList.add(new ManufacturerEntity(id, name));
            }
            return manufacturersEntityList;
        } catch (SQLException e) {
            LOGGER.error("Database problems, cannot get manufacturers table", e);
            throw new DBException();
        }
    }

    public Optional<GoodsEntity> findGoodsById(String goodsId) {

        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_GOODS_BY_ID_QUERY);
            preparedStatement.setString(1, goodsId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                CategoryEntity categoryEntity = extractCategory(resultSet);
                ManufacturerEntity manufacturerEntity = extractManufacturer(resultSet);
                GoodsEntity goodsEntity = createGoodsEntity(resultSet, categoryEntity, manufacturerEntity);
                return Optional.of(goodsEntity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.debug("Problem in goods repository", e);
            throw new DBException();
        }
    }

    public void editGoods(GoodsEntity goodsEntity) {
        int id = goodsEntity.getId();
        String name = goodsEntity.getName();
        String model = goodsEntity.getModel();
        String price = goodsEntity.getPrice();
        int category = goodsEntity.getCategory().getId();
        String imageName = goodsEntity.getImageName();
        String description = goodsEntity.getDescription();
        int manufacturer = goodsEntity.getManufacturer().getId();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement
                            (EDIT_GOODS_QUERY);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, model);
            preparedStatement.setString(3, price);
            preparedStatement.setInt(4, category);
            preparedStatement.setString(5, imageName);
            preparedStatement.setString(6, description);
            preparedStatement.setInt(7, manufacturer);
            preparedStatement.setTimestamp(8, timestamp);
            preparedStatement.setInt(9, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.debug("Problem in goods repository. Cant edit goods with id:{}", id, e);
            throw new DBException();
        }
    }
}
