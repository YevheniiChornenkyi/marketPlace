package com.epam.yevheniy.chornenky.market.place.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SiteFilter {

    private List<Integer> categories = new ArrayList<>();
    private SortedType sortedType = SortedType.ALPHABETIC;
    private Order order = Order.ASCENDING;

    public List<Integer> getCategories() {
        return categories;
    }

    private void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public SortedType getSortedType() {
        return sortedType;
    }

    private void setSortedType(SortedType sortedType) {
        this.sortedType = sortedType;
    }

    public Order getOrder() {
        return order;
    }

    private void setOrder(Order order) {
        this.order = order;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private final SiteFilter siteFilter = new SiteFilter();

        private Builder() {
        }

        public Builder setSortedType(String sortedType) {
            if (Objects.nonNull(sortedType)) {
                siteFilter.setSortedType(SortedType.valueOf(sortedType));
            }
            return this;
        }

        public Builder setOrder(String order) {
            if (Objects.nonNull(order)) {
                siteFilter.setOrder(Order.valueOf(order));
            }
            return this;
        }

        public Builder addCategory(Integer category) {
            siteFilter.categories.add(category);
            return this;
        }

        public SiteFilter getSiteFilter() {
            return siteFilter;
        }
    }

    public enum Order {
        ASCENDING("ASC"),
        DESCENDING("DESC");

        private final String type;

        Order(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    public enum SortedType {
        ALPHABETIC("name"),
        PRICE("price"),
        DATE("created");

        private final String type;

        SortedType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}
