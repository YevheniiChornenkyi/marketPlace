package com.epam.yevheniy.chornenky.market.place.repositories.entities;

import java.sql.Timestamp;

/**
 * Goods entity. This transfer object characterized by name, model, id, price, category, imageName, description, manufacturer, created.
 * id must be unique.
 */
public class GoodsEntity {
    private final String name;
    private final String model;
    private final Integer id;
    private final String price;
    private final CategoryEntity category;
    private final String imageName;
    private final String description;
    private final ManufacturerEntity manufacturer;
    private final Timestamp created;

    public GoodsEntity(String name, String model, Integer id, String price, CategoryEntity category, String imageName, String description, ManufacturerEntity manufacturer, Timestamp created) {
        this.name = name;
        this.model = model;
        this.id = id;
        this.price = price;
        this.category = category;
        this.imageName = imageName;
        this.description = description;
        this.manufacturer = manufacturer;
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public int getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public String getImageName() {
        return imageName;
    }

    public String getDescription() {
        return description;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public Timestamp getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }
}
