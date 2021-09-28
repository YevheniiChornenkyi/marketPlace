package com.epam.yevheniy.chornenky.market.place.repositories;

import com.epam.yevheniy.chornenky.market.place.db.ConnectionManager;
import com.epam.yevheniy.chornenky.market.place.exceptions.DBException;
import com.epam.yevheniy.chornenky.market.place.models.SiteFilter;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.CategoryEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.GoodsEntity;
import com.epam.yevheniy.chornenky.market.place.repositories.entities.ManufacturerEntity;
import com.epam.yevheniy.chornenky.market.place.servlet.dto.GoodsViewDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class unites the logic of accessing the database related to goods
 * accepts a connectionManager
 */
public class GoodsRepository {
    private static final Logger LOGGER = LogManager.getLogger(GoodsRepository.class);

    private static final String FIND_ALL_QUERY = "SELECT goods.name, goods.id AS goods_id, goods.model, goods.price, categories.category_name, categories.id AS category_id, goods.image_name, goods.description, manufacturers.manufacturer_name, manufacturers.id AS manufacturer_id , goods.created FROM goods LEFT JOIN categories ON goods.category=categories.id LEFT JOIN manufacturers ON goods.manufacturer=manufacturers.id";
    private static final String FIND_GOODS_BY_ID_QUERY = FIND_ALL_QUERY + " WHERE goods.id=?";
    private static final String INSERT_GOODS_QUERY = "INSERT INTO goods (name, model, price, category, image_name, description, manufacturer) VALUE (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_CATEGORY_BY_ID_QUERY = "SELECT categories.category_name, categories.id FROM categories WHERE id=?";
    private static final String FIND_MANUFACTURER_BY_ID_QUERY = "SELECT manufacturers.id, manufacturers.manufacturer_name FROM manufacturers WHERE id=?";
    private static final String FIND_ALL_CATEGORIES_QUERY = "SELECT * FROM categories";
    private static final String FIND_ALL_MANUFACTURERS_QUERY = "SELECT manufacturers.id, manufacturers.manufacturer_name FROM manufacturers";
    private static final String EDIT_GOODS_QUERY = "UPDATE goods SET name=?, model=?, price=?, category=?, image_name=?, description=?, manufacturer=?, created=? WHERE id=?";
    private static final String TEMPLATE_SORTED_GOODS_QUERY = "SELECT goods.id, goods.name, goods.model, goods.price, categories.category_name, goods.image_name, goods.description, manufacturers.manufacturer_name, goods.created FROM goods LEFT JOIN categories ON goods.category=categories.id LEFT JOIN manufacturers ON goods.manufacturer=manufacturers.id ";

    private final ConnectionManager connectionManager;

    public GoodsRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * contacts the database and return all goods from the database in List<GoodsEntity> format
     * @return list of all goods from the database
     */
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

    /**
     * collects goodsEntity from the received parameters
     * @param resultSet extract goodsEntity parameters from this resultSet
     * @param category goods categoryEntity
     * @param manufacturer goods manufacturerEntity
     * @return goodsEntity
     * @throws SQLException Handled above
     */
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

    /**
     * extract category_id from resultSet and return created categoryEntity
     * @param resultSet database resultSet
     * @return CategoryEntity
     * @throws SQLException Handled above
     */
    private CategoryEntity extractCategory(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("category_id");
        String name = resultSet.getString("category_name");
        return new CategoryEntity(id, name);
    }

    /**
     * extract manufacturer_id from resultSet and return created manufacturerEntity
     * @param resultSet database resultSet
     * @return ManufacturerEntity
     * @throws SQLException Handled above
     */
    private ManufacturerEntity extractManufacturer(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("manufacturer_id");
        String name = resultSet.getString("manufacturer_name");
        return new ManufacturerEntity(id, name);
    }

    /**
     * Accesses the database to search for the manufacturer by ID, returns the optional of the found one. empty if found nothing
     * @param manufacturer manufacturer id
     * @return Optional<ManufacturerEntity> empty if not found
     */
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

    /**
     *  Accesses the database to search for the category by ID, returns the optional of the found one. empty if found nothing
     * @param category category id
     * @return Optional<CategoryEntity> empty if not found
     */
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

    /**
     * Accepts GoodsEntity. Takes goods parameter from entity and sent query to database.
     */
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

    /**
     * Send query to database who is select all Categories, save in CategoryEntity format and return List<CategoryEntity>
     * @return List of all CategoryEntity
     */
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

    /**
     * Send query to database who is select all Manufacturers, save in ManufacturerEntity format and return List<ManufacturerEntity>
     * @return List of all ManufacturerEntity
     */
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

    /**
     * Send query to database. Select goods by id. Return founded goods in Optional<GoodsEntity> format. Empty if not found.
     * @param goodsId goods id
     * @return Optional<GoodsId> empty if not found
     */
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

    /**
     * Accept goodsEntity. Extract from entity goods parameter. Sent update query to database. using goodsId to found specific goods.
     * @param goodsEntity goodsEntity with new goods parameter.
     */
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

    /**
     * Accept siteFilter. Select ass goods from database and sorted with specified in siteFilter parameters.
     * @param siteFilter site filter
     * @return sorted goods in List<GoodsViewDTO> format
     */
    public List<GoodsViewDTO> getSortedGoodsViewDtoList(SiteFilter siteFilter) {
        try(Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(querySortedGoodsViewDtoList(siteFilter));
            ResultSet resultSet = preparedStatement.executeQuery();
            List<GoodsViewDTO> goodsViewDTOList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String model = resultSet.getString("model");
                String price = resultSet.getString("price");
                String category = resultSet.getString("category_name");
                String imageName = resultSet.getString("image_name");
                String description = resultSet.getString("description");
                String manufacturer = resultSet.getString("manufacturer_name");
                String created = resultSet.getString("created");
                GoodsViewDTO goodsViewDTO = new GoodsViewDTO(id, model, price, category, imageName, description, manufacturer, name, created);
                goodsViewDTOList.add(goodsViewDTO);
            }
            return goodsViewDTOList;
        } catch (SQLException e) {
            LOGGER.error("Query or Database problem. Cant find goods to print at homePage");
            throw new DBException();
        }
    }

    /**
     * Generates a query depending on the specified in siteFilter parameters.
     * @param siteFilter site filter
     * @return database query
     */
    private String querySortedGoodsViewDtoList(SiteFilter siteFilter) {
        List<Integer> categories = siteFilter.getCategories();
        SiteFilter.SortedType sortedType = siteFilter.getSortedType();
        SiteFilter.Order order = siteFilter.getOrder();
        String maxPrice = siteFilter.getMaxPrice().equals("") ? "(SELECT MAX(goods.price))" : siteFilter.getMaxPrice() + " ";

        StringBuilder query = new StringBuilder(TEMPLATE_SORTED_GOODS_QUERY);
        if (categories.size() != 0) {
            query.append("WHERE (");
            for (Integer category : categories) {
                query.append(" goods.category=")
                        .append(category)
                        .append(" OR");
            }
            query
                    .delete(query.length() - 2, query.length())
                    .append(")");
        }

        if (categories.size() != 0) {
            query
                    .append("AND (upper(price) between ")
                    .append(siteFilter.getMinPrice())
                    .append(" and ")
                    .append(maxPrice)
                    .append(") ");
        }
        query
                .append("ORDER BY ")
                .append(sortedType.getType())
                .append(" ")
                .append(order.getType());
        return query.toString();
    }
}
