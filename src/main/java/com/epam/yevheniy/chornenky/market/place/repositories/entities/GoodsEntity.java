package com.epam.yevheniy.chornenky.market.place.repositories.entities;

import java.sql.Timestamp;

public class GoodsEntity {
    private String model;
    private int id;
    private String price;
    private CategoryEntity category;
    private String imageId;
    private String description;
    private ManufacturerEntity manufacturer;
    private Timestamp created;

    public GoodsEntity(String model, int id, String price, CategoryEntity category,
                       String imageId, String description, ManufacturerEntity manufacturer, Timestamp created) {

        this.model = model;
        this.id = id;
        this.price = price;
        this.category = category;
        this.imageId = imageId;
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

    public String getImageId() {
        return imageId;
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
}
