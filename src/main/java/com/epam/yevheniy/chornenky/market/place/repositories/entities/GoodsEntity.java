package com.epam.yevheniy.chornenky.market.place.repositories.entities;

import java.sql.Timestamp;

public class GoodsEntity {
    private String model;
    private Integer id;
    private String price;
    private CategoryEntity category;
    private String imageName;
    private String description;
    private ManufacturerEntity manufacturer;
    private Timestamp created;

    public GoodsEntity(String model, Integer id, String price, CategoryEntity category,
                       String imageName, String description, ManufacturerEntity manufacturer, Timestamp created) {

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
}
