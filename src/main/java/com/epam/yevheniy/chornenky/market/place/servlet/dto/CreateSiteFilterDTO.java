package com.epam.yevheniy.chornenky.market.place.servlet.dto;

public class CreateSiteFilterDTO {
    private final String sort;
    private final String order;
    private final String minPrice;
    private final String maxPrice;
    private final String[] categories;

    public CreateSiteFilterDTO(String sort, String order, String minPrice, String maxPrice, String[] categories) {
        this.sort = sort;
        this.order = order;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.categories = categories;
    }

    public String getSort() {
        return sort;
    }

    public String getOrder() {
        return order;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public String[] getCategories() {
        return categories;
    }
}
