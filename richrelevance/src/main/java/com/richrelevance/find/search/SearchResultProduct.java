package com.richrelevance.find.search;

import java.util.List;
import java.util.Map;

public class SearchResultProduct {
    
    public static class Keys {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CLICK_URL = "clickUrl";
        public static final String IMAGE_ID = "imageId";
        public static final String LINK_ID = "linkId";
        public static final String NUM_REVIEWS = "numReviews";
        public static final String DESCRIPTION = "description";
        public static final String CATEGORY_NAMES = "categoryName";
        public static final String SCORE = "score";
        public static final String PRICE_CENTS = "priceCents";
        public static final String SALES_PRICE_CENTS = "salePriceCents";
        public static final String BRAND = "brand";
        public static final String CATEGORY_IDS = "categoryId";
    }

    private String id;
    private String name;
    private String clickUrl;
    private String imageId;
    private String linkId;
    private int numReviews;
    private String description;
    private List<String> categoryNames;
    private List<String> categoryIds;
    private double score;
    private int priceCents;
    private int salesPriceCents;
    private String brand;
    private Map<Facet, String> filters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(List<String> categoryNames) {
        this.categoryNames = categoryNames;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }

    public int getSalesPriceCents() {
        return salesPriceCents;
    }

    public void setSalesPriceCents(int salesPriceCents) {
        this.salesPriceCents = salesPriceCents;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Map<Facet, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<Facet, String> filters) {
        this.filters = filters;
    }
}
