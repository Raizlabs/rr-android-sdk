package com.richrelevance;

import android.support.annotation.Nullable;

import com.richrelevance.internal.net.HttpMethod;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebResponse;

import java.util.Locale;


public abstract class RequestBuilder<Result> {

    private RichRelevanceClient client;
    private WebRequestBuilder webRequestBuilder;
    private Callback<Result> resultCallback;

    public RequestBuilder() {
        // TODO
        webRequestBuilder = new WebRequestBuilder(HttpMethod.Get, (String) null);
        setClient(RichRelevance.getDefaultClient());
    }

    public void setClient(RichRelevanceClient client) {
        this.client = client;
    }

    public void execute() {
        client.executeRequest(this);
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
        addParameter("apiKey", apiKey);
        return this;
    }

    protected RequestBuilder<Result> setApiClientKey(String apiClientKey) {
        addParameter("apiClientKey", apiClientKey);
        return this;
    }

    protected RequestBuilder<Result> setUserId(String userId) {
        addParameter("userId", userId);
        return this;
    }

    protected RequestBuilder<Result> setSessionId(String sessionId) {
        addParameter("sessionId", sessionId);
        return this;
    }

    Callback<Result> getCallback() {
        return resultCallback;
    }

    public RequestBuilder<Result> setCallback(Callback<Result> callback) {
        this.resultCallback = callback;
        return this;
    }

    /**
     * Populates and returns a {@link WebRequestBuilder} to execute the request.
     *
     * @return The builder to use to execute the request or null if something went wrong.
     */
    @Nullable
    WebRequestBuilder build() {
        if (assertConfiguration()) {
            applyConfiguration(getConfiguration());
            return webRequestBuilder;
        }

        return null;
    }

    protected void applyConfiguration(ClientConfiguration configuration) {
        setUrl(getFullUrl());
        setApiKey(configuration.getApiKey());
        setApiClientKey(configuration.getApiClientKey());
        setUserId(configuration.getUserId());
        setSessionId(configuration.getSessionId());
    }

    protected String getFullUrl() {
        return getFullUrl(getEndpointPath());
    }

    protected String getFullUrl(String path) {
        if (!assertConfiguration()) {
            return null;
        }

        String scheme = getConfiguration().useHttps() ? "https" : "http";
        String endpoint = getConfiguration().getEndpoint();
        while (endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        return String.format(Locale.US, "%s://%s/%s", scheme, endpoint, path);
    }

    protected void setUrl(String url) {
        webRequestBuilder.setUrl(url);
    }

    private ClientConfiguration getConfiguration() {
        return client.getConfiguration();
    }

    private boolean assertConfiguration() {
        if (!Assertions.assertNotNull("No " + RichRelevanceClient.class.getSimpleName() + " was specified.", client)) {
            return false;
        }

        ClientConfiguration configuration = client.getConfiguration();
        if (!Assertions.assertNotNull("No " + ClientConfiguration.class.getSimpleName() + " was specified.", configuration)) {
            return false;
        }

        return true;
    }

    protected abstract String getEndpointPath();

    protected abstract Result parseResponse(WebResponse response);

    WebRequest<Result> getWebRequest() {
        return new WebRequest<Result>() {
            @Override
            public WebRequestBuilder getRequestBuilder() {
                return build();
            }

            @Override
            public Result translate(WebResponse response) {
                return parseResponse(response);
            }
        };
    }
}
