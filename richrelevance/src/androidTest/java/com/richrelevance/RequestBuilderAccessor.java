package com.richrelevance;

import java.util.List;

public class RequestBuilderAccessor {
    private RequestBuilder<?> builder;

    public RequestBuilderAccessor(RequestBuilder<?> builder) {
        this.builder = builder;
    }

    public String getParamValue(String key) {
        return builder.getWebRequest().getRequestBuilder().getParam(key);
    }

    public List<String> getAllParamValues(String key) {
        return builder.getWebRequest().getRequestBuilder().getAllParamValues(key);
    }
}