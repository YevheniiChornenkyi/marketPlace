package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class CreateGoodsDTO {
    private final String name;
    private final String model;
    private final String price;
    private final int category;
    private final String description;
    private final int manufacturer;
    private byte[] image;

    public CreateGoodsDTO(String name, String model, String price, int category, String description, int manufacturer) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.category = category;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public CreateGoodsDTO(String name, String model, String price, int category, String description, int manufacturer, byte[] image) {
        this.name = name;
        this.model = model;
        this.price = price;
        this.category = category;
        this.description = description;
        this.manufacturer = manufacturer;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public int getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public byte[] getImage() {
        return image;
    }
}
