package com.epam.yevheniy.chornenky.market.place.repositories.entities;

public class ManufacturerEntity {
    private int id;
    private String name;

    public ManufacturerEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
