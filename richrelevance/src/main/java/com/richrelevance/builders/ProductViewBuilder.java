package com.richrelevance.builders;

import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.responses.WebResponse;

public class ProductViewBuilder extends RequestBuilder<Void> {

    public ProductViewBuilder setStrategy(String name) {
        return this;
    }

    public ProductViewBuilder setProductId(String productId) {
        return this;
    }

    @Override
    protected Void parseResponse(WebResponse response) {
        return null;
    }
}
