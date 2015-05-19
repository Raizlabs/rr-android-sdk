package com.richrelevance;

import com.richrelevance.internal.net.HttpMethod;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebResponse;

import java.util.Locale;


public abstract class RequestBuilder<Result> {

    private RichRelevanceClient richRelevanceClient;
    private WebRequestBuilder webRequestBuilder;
    private Callback<Result> resultCallback;

    public RequestBuilder() {
        // TODO
        webRequestBuilder = new WebRequestBuilder(HttpMethod.Get, getFullUrl());
        setClient(RichRelevance.getDefaultClient());
    }

    public void setClient(RichRelevanceClient client) {
        if ((richRelevanceClient == null) || !richRelevanceClient.equals(client)) {
            this.richRelevanceClient = client;
            setApiKey(client.getApiKey());
            setApiClientKey(client.getApiClientKey());
            setUserId(client.getUserId());
            setSessionId(client.getSessionId());
        }
    }

    public void execute() {
        richRelevanceClient.executeRequest(this);
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

    protected String getFullUrl() {
        return getFullUrl(getEndpointPath());
    }

    protected String getFullUrl(String endpointPath) {
        return String.format(Locale.US, "%s/%s", EndpointHelper.getBaseUrl(), endpointPath);
    }

    protected void setUrl(String url) {
        webRequestBuilder.setUrl(url);
    }

    protected abstract String getEndpointPath();

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
