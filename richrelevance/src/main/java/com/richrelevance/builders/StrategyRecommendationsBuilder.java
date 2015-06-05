package com.richrelevance.builders;

import android.util.Log;

import com.richrelevance.RequestBuilder;
import com.richrelevance.ResponseInfo;
import com.richrelevance.StrategyType;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

public class StrategyRecommendationsBuilder extends RequestBuilder<ResponseInfo> {

    public StrategyRecommendationsBuilder setStrategy(StrategyType strategy) {
        setParameter("strategyName", strategy.getKey());
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return "rrPlatform/recsUsingStrategy";
    }

    @Override
    protected ResponseInfo createNewResult() {
        // TODO
        return null;
    }

    @Override
    protected void populateResponse(WebResponse response, JSONObject json, ResponseInfo responseInfo) {
        // TODO
        Log.i(getClass().getSimpleName(), "Response: " + response.getResponseCode() + " : " + response.getContentAsString());
    }
}
