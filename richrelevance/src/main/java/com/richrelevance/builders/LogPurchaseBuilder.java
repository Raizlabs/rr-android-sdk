package com.richrelevance.builders;

import com.richrelevance.Product;
import com.richrelevance.RequestBuilder;
import com.richrelevance.ResponseInfo;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

import java.util.Collection;

public class LogPurchaseBuilder extends RequestBuilder<ResponseInfo> {

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
    protected String getEndpointPath() {
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
