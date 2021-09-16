package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class EditGoodsDTO {
    private final String oldImageName;
    private final int id;
    private final String name;
    private final String model;
    private final String price;
    private final int category;
    private final String description;
    private final int manufacturer;
    private final byte[] image;

    public EditGoodsDTO(int id, String name, String model, String price, int category,
                        String description, int manufacturer, byte[] image, String oldImageName) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
        this.category = category;
        this.description = description;
        this.manufacturer = manufacturer;
        this.image = image;
        this.oldImageName = oldImageName;
    }

    public int getId() {
        return id;
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

    public String getOldImageName() {
        return oldImageName;
    }
}
