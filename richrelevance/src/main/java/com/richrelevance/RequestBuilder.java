package com.richrelevance;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.richrelevance.internal.net.HttpMethod;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebResponse;
import com.richrelevance.utils.ValueMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * Class which assists in the setup and construction of a request, as well as the parsing of the response.
 * @param <Result> The type of the result that this request will return.
 */
public abstract class RequestBuilder<Result> {

    private static final String LIST_DELIMITER = "|";
    private static final String VALUE_MAP_VALUE_DELIMITER = ";";
    private static final String VALUE_MAP_VALUE_ASSIGNMENT = ":";

    private RichRelevanceClient client;
    private WebRequestBuilder webRequestBuilder;
    private Callback<Result> resultCallback;

    public RequestBuilder() {
        webRequestBuilder = new WebRequestBuilder(HttpMethod.Get, (String) null);
        setClient(RichRelevance.getDefaultClient());
    }

    /**
     * Sets the {@link RichRelevanceClient} that this request should be run through if {@link #execute()} is called.
     * @param client The client to run the request through.
     */
    public void setClient(RichRelevanceClient client) {
        this.client = client;
    }

    /**
     * Executes this request using the default client or the one associated with this request via
     * {@link #setClient(RichRelevanceClient)}}.
     */
    public void execute() {
        client.executeRequest(this);
    }

    /**
     * Sets the given arbitrary parameter in this builder.
     * @param key The key of the parameter to set.
     * @param value The value to set the parameter to.
     * @return This builder for chaining method calls.
     */
    public RequestBuilder<Result> setParameter(String key, String value) {
        webRequestBuilder.addParam(key, value);
        return this;
    }

    /**
     * Sets the given arbitrary parameter in this builder.
     * @param key The key of the parameter to set.
     * @param value The value to set the parameter to.
     * @return This builder for chaining method calls.
     */
    public RequestBuilder<Result> setParameter(String key, boolean value) {
        webRequestBuilder.addParam(key, value);
        return this;
    }

    /**
     * Sets the given arbitrary parameter in this builder.
     * @param key The key of the parameter to set.
     * @param value The value to set the parameter to.
     * @return This builder for chaining method calls.
     */
    public RequestBuilder<Result> setParameter(String key, int value) {
        webRequestBuilder.addParam(key, value);
        return this;
    }

    /**
     * Sets the given arbitrary parameter in this builder.
     * @param key The key of the parameter to set.
     * @param value The value to set the parameter to.
     * @return This builder for chaining method calls.
     */
    public RequestBuilder<Result> setParameter(String key, long value) {
        webRequestBuilder.addParam(key, value);
        return this;
    }

    public RequestBuilder<Result> removeParameter(String key) {
        webRequestBuilder.removeParam(key);
        return this;
    }

    public RequestBuilder<Result> addListParameters(String key, Object... values) {
        return addListParameters(key, Arrays.asList(values));
    }

    public RequestBuilder<Result> addListParameters(String key, Collection<?> values) {
        // Short circuit
        if ((values == null) || values.isEmpty()) {
            return this;
        }

        String existingValue = webRequestBuilder.getParam(key);

        boolean hasValue = !TextUtils.isEmpty(existingValue);

        StringBuilder valueBuilder = new StringBuilder(existingValue);
        for (Object value : values) {
            if (value != null) {
                String stringValue = value.toString();
                if (!TextUtils.isEmpty(stringValue)) {
                    if (hasValue) {
                        valueBuilder.append(LIST_DELIMITER);
                    }

                    valueBuilder.append(stringValue);
                    hasValue = true;
                }
            }
        }

        setParameter(key, valueBuilder.toString());

        return this;
    }

    public RequestBuilder<Result> setListParameter(String key, String...values) {
        setParameter(key, StringUtils.join(LIST_DELIMITER, values));
        return this;
    }

    public RequestBuilder<Result> setListParameter(String key, Collection<?> values) {
        setParameter(key, StringUtils.join(LIST_DELIMITER, values));
        return this;
    }

    public RequestBuilder<Result> setValueMapParameter(String key, ValueMap<?> values) {
        String value = StringUtils.join(values, LIST_DELIMITER, VALUE_MAP_VALUE_DELIMITER, VALUE_MAP_VALUE_ASSIGNMENT);
        setParameter(key, value);
        return this;
    }

    public RequestBuilder<Result> setValueMapParameterFlat(String key, ValueMap<?> values) {
        String value = StringUtils.joinFlat(values, LIST_DELIMITER, VALUE_MAP_VALUE_ASSIGNMENT);
        setParameter(key, value);
        return this;
    }

    protected RequestBuilder<Result> setApiKey(String apiKey) {
        setParameter("apiKey", apiKey);
        return this;
    }

    protected RequestBuilder<Result> setApiClientKey(String apiClientKey) {
        setParameter("apiClientKey", apiClientKey);
        return this;
    }

    protected RequestBuilder<Result> setUserId(String userId) {
        setParameter("userId", userId);
        return this;
    }

    protected RequestBuilder<Result> setSessionId(String sessionId) {
        setParameter("sessionId", sessionId);
        return this;
    }

    Callback<Result> getCallback() {
        return resultCallback;
    }

    /**
     * Sets the callback to be called when this request completes.
     * @param callback The callback to call.
     * @return This builder for chaining method calls.
     */
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

    /**
     * Called to obtain the path of the endpoint to hit.
     * @return The path of the endpoint.
     */
    protected abstract String getEndpointPath();

    /**
     * Called to parse the given web response into the appropriate result type.
     * @param response The response to parse.
     * @return The result contained in the response.
     */
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
