package com.richrelevance.builders;

import com.richrelevance.Placement;
import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.responses.WebResponse;

import java.util.Collection;
import java.util.List;

// TODO - This may be able to be merged into {@link PersonalizedRecommendationsBuilder} if the
// response is similar enough
public class PersonalizedRecommendationsBuilder extends RequestBuilder<List<Object>> {

    public PersonalizedRecommendationsBuilder addPlacements(Placement... placements) {
        return this;
    }

    public PersonalizedRecommendationsBuilder addPlacements(Collection<Placement> placements) {
        return this;
    }

    @Override
    protected List<Object> parseResponse(WebResponse response) {
        return null;
    }
}
