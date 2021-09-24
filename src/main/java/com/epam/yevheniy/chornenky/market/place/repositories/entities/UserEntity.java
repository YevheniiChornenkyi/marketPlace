package com.epam.yevheniy.chornenky.market.place.repositories.entities;

public class UserEntity {


    private final String name;
    private final String surName;
    private final String psw;
    private final String email;
    private final Role role;
    private final String id;
    private Boolean isActive = false;

    public UserEntity(String name, String surName, String psw, String email, String id, Role role) {
        this.name = name;
        this.surName = surName;
        this.psw = psw;
        this.email = email;
        this.id = id;
        this.role = role;
    }

    public UserEntity(String name, String surName, String psw, String email, String id, Role role, boolean isActive) {
        this.name = name;
        this.surName = surName;
        this.psw = psw;
        this.email = email;
        this.id = id;
        this.role = role;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getPsw() {
        return psw;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public enum Role {
        ADMIN("1"), CUSTOMER("2"), NOT_ACTIVATE("3");

        private final String id;

        Role(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
