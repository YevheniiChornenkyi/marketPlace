package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class UserViewDto {
    private final String id;
    private final String name;
    private final String surName;
    private final String role;
    private final String email;
    private final boolean isActive;

    public UserViewDto(String id, String name, String surName, String role, String email, boolean isActive) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.role = role;
        this.email = email;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
