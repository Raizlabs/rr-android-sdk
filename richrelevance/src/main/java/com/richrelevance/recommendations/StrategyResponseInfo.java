package com.richrelevance.recommendations;

import com.richrelevance.ResponseInfo;
import com.richrelevance.StrategyType;

import java.util.List;

public class StrategyResponseInfo extends ResponseInfo {
    private List<ProductRecommendation> products;
    private StrategyType strategyType;
    private String requestId;
    private String message;

    public List<ProductRecommendation> getProducts() {
        return products;
    }

    void setProducts(List<ProductRecommendation> products) {
        this.products = products;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    void setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public String getRequestId() {
        return requestId;
    }

    void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}
