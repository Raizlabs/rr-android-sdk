package com.richrelevance.placements;

import com.richrelevance.Range;
import com.richrelevance.utils.ValueMap;

import java.util.List;

public class ProductRecommendation {
    private String id;
    private String name;
    private String genre;
    private double rating;
    private long numReviews;
    private String regionalProductSku;
    private List<String> categoryIds;
    private String imageUrl;
    private boolean isRecommendable;

    private int priceCents;
    private Range priceRangeCents;
    private String regionPriceDescription;

    private String clickUrl;

    private ValueMap<String> attributes;

    private List<Category> categories;

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    void setGenre(String genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    void setRating(double rating) {
        this.rating = rating;
    }

    public long getNumReviews() {
        return numReviews;
    }

    void setNumReviews(long numReviews) {
        this.numReviews = numReviews;
    }

    public String getRegionalProductSku() {
        return regionalProductSku;
    }

    void setRegionalProductSku(String regionalProductSku) {
        this.regionalProductSku = regionalProductSku;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isRecommendable() {
        return isRecommendable;
    }

    void setIsRecommendable(boolean isRecommendable) {
        this.isRecommendable = isRecommendable;
    }

    public int getPriceCents() {
        return priceCents;
    }

    void setPriceCents(int priceCents) {
        this.priceCents = priceCents;
    }

    public Range getPriceRangeCents() {
        return priceRangeCents;
    }

    void setPriceRangeCents(Range priceRangeCents) {
        this.priceRangeCents = priceRangeCents;
    }

    public String getRegionPriceDescription() {
        return regionPriceDescription;
    }

    void setRegionPriceDescription(String regionPriceDescription) {
        this.regionPriceDescription = regionPriceDescription;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }

    public ValueMap<String> getAttributes() {
        return attributes;
    }

    void setAttributes(ValueMap<String> attributes) {
        this.attributes = attributes;
    }

    public List<Category> getCategories() {
        return categories;
    }

    void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
