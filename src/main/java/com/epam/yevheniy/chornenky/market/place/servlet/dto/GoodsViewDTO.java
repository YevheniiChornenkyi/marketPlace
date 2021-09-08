package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class GoodsViewDTO {
    private int id;
    private String model;
    private String price;
    private String categoryName;
    private String imageId;
    private String description;
    private String manufacturerName;

    public GoodsViewDTO(int id, String model, String price, String categoryName,
                        String imageId, String description, String manufacturerName) {

        this.id = id;
        this.model = model;
        this.price = price;
        this.categoryName = categoryName;
        this.imageId = imageId;
        this.description = description;
        this.manufacturerName = manufacturerName;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageName() {
        return imageId;
    }

    public String getDescription() {
        return description;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public int getId() {
        return id;
    }
}
