package com.epam.yevheniy.chornenky.market.place.repositories.entities;

/**
 * Category entity. This transfer object characterized by id, name.
 * id must be unique.
 */
public class CategoryEntity {
    private final int id;
    private final String name;

    public CategoryEntity(int id, String name) {
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
