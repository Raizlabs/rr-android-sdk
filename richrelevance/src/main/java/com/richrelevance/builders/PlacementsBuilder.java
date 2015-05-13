package com.richrelevance.builders;

import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.responses.WebResponse;

import java.util.Collection;
import java.util.List;

public class PlacementsBuilder extends RequestBuilder<List<Object>> {

    public PlacementsBuilder addPlacements(String... names) {
        return this;
    }

    public PlacementsBuilder addPlacements(Collection<String> names) {
        return this;
    }

    public PlacementsBuilder setCategoryId(long categoryId) {
        return this;
    }

    public PlacementsBuilder setCount(int count) {
        return this;
    }

    public PlacementsBuilder setStartIndex(int start) {
        return this;
    }

    public PlacementsBuilder setSearchTerm(String searchTerm) {
        return this;
    }

    @Override
    protected List<Object> parseResponse(WebResponse response) {
        return null;
    }
}
