package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class CategoryDto {
    private final int CategoryId;
    private final String CategoryName;

    public CategoryDto(int categoryId, String categoryName) {
        CategoryId = categoryId;
        CategoryName = categoryName;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }
}
