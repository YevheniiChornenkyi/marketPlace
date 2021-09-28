package com.epam.yevheniy.chornenky.market.place.repositories.entities;

/**
 * Manufacturer entity. This transfer object characterized by id, name.
 * id must be unique.
 */
public class ManufacturerEntity {
    private final int id;
    private final String name;

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
