package com.richrelevance;

import com.richrelevance.internal.net.HttpMethod;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.responses.WebResponse;

public abstract class RequestBuilder<Result> {

    private WebRequestBuilder webRequestBuilder;
    private Callback<Result> resultCallback;

    public RequestBuilder() {
        // TODO
        webRequestBuilder = new WebRequestBuilder(HttpMethod.Get, "");
        setApiKey(RichRelevance.apiKey);
        setApiClientKey(RichRelevance.apiClientKey);
        setUserId(RichRelevance.userId);
        setSessionId(RichRelevance.sessionId);
    }

    public void execute() {
        RichRelevance.executeRequest(this);
    }

    public RequestBuilder<Result> addParameter(String key, String value) {
        webRequestBuilder.addParam(key, value);
        return this;
    }

    public RequestBuilder<Result> addParameter(String key, long value) {
        webRequestBuilder.addParam(key, value);
        return this;
    }

    protected RequestBuilder<Result> setApiKey(String apiKey) {
        return this;
    }

    protected RequestBuilder<Result> setApiClientKey(String apiKey) {
        return this;
    }

    protected RequestBuilder<Result> setUserId(String userId) {
        return this;
    }

    protected RequestBuilder<Result> setSessionId(String sessionId) {
        return this;
    }

    Callback<Result> getCallback() {
        return resultCallback;
    }

    public RequestBuilder<Result> setCallback(Callback<Result> callback) {
        this.resultCallback = callback;
        return this;
    }

    WebRequestBuilder getWebRequestBuilder() {
        return webRequestBuilder;
    }

    protected abstract Result parseResponse(WebResponse response);

    WebRequest<Result> getWebRequest() {
        return new WebRequest<Result>() {
            @Override
            public WebRequestBuilder getRequestBuilder() {
                return getWebRequestBuilder();
            }

            @Override
            public Result translate(WebResponse response) {
                return parseResponse(response);
            }
        };
    }
}
