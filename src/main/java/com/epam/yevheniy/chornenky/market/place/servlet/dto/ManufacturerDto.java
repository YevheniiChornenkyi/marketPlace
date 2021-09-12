package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class ManufacturerDto {
    private final int manufacturerId;
    private final String manufacturerName;

    public ManufacturerDto(int manufacturerId, String manufacturerName) {
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }
}
