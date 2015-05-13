package com.richrelevance.builders;

import com.richrelevance.Product;
import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.responses.WebResponse;

import java.util.Collection;

public class LogPurchaseBuilder extends RequestBuilder<Void> {

    public LogPurchaseBuilder addPlacements(String... names) {
        return this;
    }

    public LogPurchaseBuilder addPlacements(Collection<String> names) {
        return this;
    }

    public LogPurchaseBuilder setOrderNumber(String orderNumber) {
        return this;
    }

    public LogPurchaseBuilder addProducts(Product... products) {
        return this;
    }

    public LogPurchaseBuilder addProducts(Collection<Product> products) {
        return this;
    }

    @Override
    protected Void parseResponse(WebResponse response) {
        return null;
    }
}
