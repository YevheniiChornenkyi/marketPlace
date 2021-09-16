package com.epam.yevheniy.chornenky.market.place.models;

import java.util.ArrayList;
import java.util.List;

public class SiteFilter {
    private List<Integer> categories = new ArrayList<>();
    private SortedBy sortedBy = SortedBy.ALPHABETIC;
    private SortedType sortedType = SortedType.ASCENDING;

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public SortedBy getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(SortedBy sortedBy) {
        this.sortedBy = sortedBy;
    }

    public SortedType getSortedType() {
        return sortedType;
    }

    public void setSortedType(SortedType sortedType) {
        this.sortedType = sortedType;
    }

    public Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
        }

        public SiteFilter setSortedBy(SiteFilter siteFilter, SortedBy sortedBy) {
            siteFilter.sortedBy = sortedBy;
            return siteFilter;
        }

        public SiteFilter setSortedType(SiteFilter siteFilter, SortedType sortedType) {
            siteFilter.sortedType = sortedType;
            return siteFilter;
        }

        public SiteFilter addCategory(SiteFilter siteFilter, Integer category) {
            siteFilter.getCategories().add(category);
            return siteFilter;
        }
    }


    public enum SortedType {
        ASCENDING,
        DESCENDING
    }

    public enum SortedBy {
        ALPHABETIC,
        PRICE,
        DATE
    }
}
