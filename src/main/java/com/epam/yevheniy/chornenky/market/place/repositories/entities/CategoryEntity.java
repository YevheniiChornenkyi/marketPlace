package com.epam.yevheniy.chornenky.market.place.repositories.entities;

public class CategoryEntity {
    private int id;
    private String name;

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
