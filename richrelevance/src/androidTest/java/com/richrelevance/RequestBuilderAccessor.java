package com.richrelevance;

public class RequestBuilderAccessor {
    private RequestBuilder<?> builder;

    public RequestBuilderAccessor(RequestBuilder<?> builder) {
        this.builder = builder;
    }

    public String getParamValue(String key) {
        return builder.getWebRequest().getRequestBuilder().getParam(key);
    }
}