package com.richrelevance.builders;

import com.richrelevance.Placement;
import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.responses.WebResponse;

import java.util.Collection;
import java.util.List;

public class PlacementsRecommendationsBuilder extends RequestBuilder<List<Object>> {

    public PlacementsRecommendationsBuilder addPlacements(Placement... placements) {
        return this;
    }

    public PlacementsRecommendationsBuilder addPlacements(Collection<Placement> placements) {
        return this;
    }

    public PlacementsRecommendationsBuilder setCategoryId(long categoryId) {
        return this;
    }

    public PlacementsRecommendationsBuilder setCount(int count) {
        return this;
    }

    public PlacementsRecommendationsBuilder setStartIndex(int start) {
        return this;
    }

    public PlacementsRecommendationsBuilder setSearchTerm(String searchTerm) {
        return this;
    }

    @Override
    protected List<Object> parseResponse(WebResponse response) {
        return null;
    }
}
