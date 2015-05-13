package com.richrelevance.builders;

import com.richrelevance.RequestBuilder;
import com.richrelevance.StrategyType;
import com.richrelevance.internal.net.responses.WebResponse;

public class StrategyBuilder extends RequestBuilder<Object> {

    public StrategyBuilder setStrategy(StrategyType strategy) {
        addParameter("strategyName", strategy.getKey());
        return this;
    }

    @Override
    protected Object parseResponse(WebResponse response) {
        return new Object();
    }
}
