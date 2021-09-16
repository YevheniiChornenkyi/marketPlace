package com.epam.yevheniy.chornenky.market.place.servlet.dto;

import java.util.Objects;

public class GoodsViewDTO {
    private final int id;
    private final String name;
    private final String model;
    private final String price;
    private final String categoryName;
    private final String imageId;
    private final String description;
    private final String manufacturerName;

    public GoodsViewDTO(int id, String model, String price, String categoryName,
                        String imageId, String description, String manufacturerName, String name) {

        this.id = id;
        this.model = model;
        this.price = price;
        this.categoryName = categoryName;
        this.imageId = imageId;
        this.description = description;
        this.manufacturerName = manufacturerName;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsViewDTO that = (GoodsViewDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "GoodsViewDTO{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", price='" + price + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", imageId='" + imageId + '\'' +
                ", description='" + description + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                '}';
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
