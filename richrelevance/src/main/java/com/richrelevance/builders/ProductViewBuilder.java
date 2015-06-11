package com.richrelevance.builders;

import com.richrelevance.ClientConfiguration;
import com.richrelevance.RequestBuilder;
import com.richrelevance.ResponseInfo;
import com.richrelevance.internal.net.WebResponse;
import com.richrelevance.recommendations.Placement;

import org.json.JSONObject;

public class ProductViewBuilder extends RequestBuilder<ResponseInfo> {

    public ProductViewBuilder setPlacement(Placement placement) {
        return this;
    }

    public ProductViewBuilder setProductId(String productId) {
        return this;
    }

    @Override
    protected String getEndpointPath(ClientConfiguration configuration) {
        return "/rrPlatform/recsForPlacements";
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
