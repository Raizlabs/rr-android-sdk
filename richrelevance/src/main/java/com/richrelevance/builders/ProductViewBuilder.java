package com.richrelevance.builders;

import com.richrelevance.Placement;
import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.WebResponse;

public class ProductViewBuilder extends RequestBuilder<Void> {

    public ProductViewBuilder setPlacement(Placement placement) {
        return this;
    }

    public ProductViewBuilder setProductId(String productId) {
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return "/rrPlatform/recsForPlacements";
    }

    @Override
    protected Void parseResponse(WebResponse response) {
        return null;
    }
}
