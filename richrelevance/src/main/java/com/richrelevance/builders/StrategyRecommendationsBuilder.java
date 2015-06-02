package com.richrelevance.builders;

import android.util.Log;

import com.richrelevance.RequestBuilder;
import com.richrelevance.Strategy;
import com.richrelevance.internal.net.WebResponse;

public class StrategyRecommendationsBuilder extends RequestBuilder<Object> {

    public StrategyRecommendationsBuilder setStrategy(Strategy strategy) {
        setParameter("strategyName", strategy.getKey());
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return "rrPlatform/recsUsingStrategy";
    }

    @Override
    protected Object parseResponse(WebResponse response) {
        Log.i(getClass().getSimpleName(), "Response: " + response.getResponseCode() + " : " + response.getContentAsString());
        return new Object();
    }
}
