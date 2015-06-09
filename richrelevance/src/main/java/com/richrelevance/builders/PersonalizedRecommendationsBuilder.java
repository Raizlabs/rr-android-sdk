package com.richrelevance.builders;

import com.richrelevance.ResponseInfo;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

import java.util.Collection;

// TODO - This may be able to be merged into {@link PersonalizedRecommendationsBuilder} if the
// response is similar enough
public class PersonalizedRecommendationsBuilder extends RequestBuilder<ResponseInfo> {

    public PersonalizedRecommendationsBuilder addPlacements(Placement... placements) {
        return this;
    }

    public PersonalizedRecommendationsBuilder addPlacements(Collection<Placement> placements) {
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return "/rrPlatform/personalize";
    }

    @Override
    protected ResponseInfo createNewResult() {
        // TODO
        return null;
    }

    @Override
    protected void populateResponse(WebResponse response, JSONObject json, ResponseInfo responseInfo) {
        // TODO
    }
}
