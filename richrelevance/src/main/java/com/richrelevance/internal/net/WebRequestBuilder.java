package com.richrelevance.internal.net;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builder class which allows for the construction of a request. This class abstracts the
 * implementation and allows definition of all the properties of HTTP requests.
 */
public class WebRequestBuilder {

    static class ParamLocation {
        private static final int AUTO = 0;
        private static final int URL = 10;
        private static final int BODY = 20;
    }

    private String url;
    private HttpMethod method;
    private LinkedHashMap<String, String> params;
    private LinkedHashMap<String, String> forcedBodyParams;
    private LinkedHashMap<String, String> headers;
    private int paramLocation = ParamLocation.AUTO;

    /**
     * Constructs a {@link WebRequestBuilder} using the given {@link HttpMethod}
     * and pointing to the given url.
     *
     * @param method The {@link HttpMethod} to use for the request.
     * @param url    The url the request targets.
     */
    public WebRequestBuilder(HttpMethod method, String url) {
        setUrl(url);
        this.method = method;
        this.params = new LinkedHashMap<>();
        this.forcedBodyParams = new LinkedHashMap<>();
        this.headers = new LinkedHashMap<>();
    }

    /**
     * Constructs a {@link WebRequestBuilder} using the given {@link HttpMethod}
     * and pointing to the given {@link URI}.
     *
     * @param method The {@link HttpMethod} to use for the request.
     * @param url    The url the request targets.
     */
    public WebRequestBuilder(HttpMethod method, URI url) {
        this(method, url.toString());
    }


    /**
     * Sets the target {@link URI} for this {@link WebRequestBuilder}.
     *
     * @param url the url the request targets.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Adds a parameter to this request.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    /**
     * Adds a parameter to this request.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParam(String key, boolean value) {
        params.put(key, Boolean.toString(value));
        return this;
    }

    /**
     * Adds a parameter to this request.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParam(String key, int value) {
        params.put(key, Integer.toString(value));
        return this;
    }

    /**
     * Adds a parameter to this request.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParam(String key, long value) {
        params.put(key, Long.toString(value));
        return this;
    }

    /**
     * Adds a parameter to the request if the value is not null.  Note that this method
     * will still add an empty string value to the request.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParamIfNotNull(String key, String value) {
        if (value != null) {
            addParam(key, value);
        }
        return this;
    }

    /**
     * Adds a {@link Map} of parameter key value pairs as parameters of this
     * request. Parameters are added in iteration order. Params added through this
     * method will adhere to settings {@link #setSendParamsInBody()} or
     * {@link #setSendParamsInURL()}. If you would like to force params to be
     * sent in the body, use {@link #addParamToBodyForced(String, String)}.
     *
     * @param params The {@link Map} of parameters.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParams(Map<String, String> params) {
        putEntries(params, this.params);
        return this;
    }

    /**
     * Gets the current value for the given key.
     * @param key The key to get the value of.
     * @return The current value of the key.
     */
    public String getParam(String key) {
        return params.get(key);
    }

    /**
     * Adds a parameter to this request that will be sent only as part of
     * the request's body.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParamToBodyForced(String key, String value) {
        forcedBodyParams.put(key, value);
        return this;
    }

    /**
     * Adds a parameter to this request that will be sent only as part of
     * the request's body. This is added only if the value is not null. See also:
     * {@link #addParamToBodyForced(String, String)}.
     *
     * @param key   The parameter key.
     * @param value The parameter value.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addParamToBodyForcedIfNotNull(String key, String value) {
        if (value != null) {
            addParamToBodyForced(key, value);
        }
        return this;
    }

    /**
     * Adds a header to this request with the given name and value.
     *
     * @param name  The name of the header.
     * @param value The value for the header.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * Adds a {@link Map} of key value pairs as headers to this request.
     * Headers are added in iteration order.
     *
     * @param headers The {@link Map} of header key value pairs to add.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder addHeaders(Map<String, String> headers) {
        putEntries(headers, this.headers);
        return this;
    }


    /**
     * Resolves where parameters should be sent and returns the value. This
     * will resolve automatic detection and return the final endpoint instead
     * of {@link ParamLocation#AUTO}
     *
     * @return One of the values defined in {@link ParamLocation} where params
     * should be sent
     */
    protected int getParamLocationResolved() {
        if (paramLocation == ParamLocation.AUTO) {
            if (method == HttpMethod.Post) {
                return ParamLocation.BODY;
            } else {
                return ParamLocation.URL;
            }
        } else {
            return paramLocation;
        }
    }

    /**
     * Causes the params set by addParam calls to be sent in the URL of this
     * request.
     *
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder setSendParamsInURL() {
        paramLocation = ParamLocation.URL;
        return this;
    }

    /**
     * Causes the params set by addParam calls to be sent in the body of this
     * request.
     *
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder setSendParamsInBody() {
        paramLocation = ParamLocation.BODY;
        return this;
    }

    private void putEntries(Map<String, String> entries, Map<String, String> map) {
        for (Entry<String, String> entry : entries.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
    }


    /**
     * Gets the URL that this {@link WebRequestBuilder} points to.
     *
     * @return The URL the {@link WebRequestBuilder} is pointing to.
     */
    public String getFullUrl() {
        String fullUrl = this.url;

        // If we should set params in the url and we have params to set, do so
        if ((getParamLocationResolved() == ParamLocation.URL) && (params.size() > 0)) {
            String queryString = "?" + HttpUtils.getQueryString(params);
            fullUrl = String.format("%s%s", this.url, queryString);
        }

        return fullUrl;
    }

    /**
     * @return The headers to be used on this request.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return The params that are currently set to be put in the body.
     */
    public Map<String, String> getBodyParams() {
        Map<String, String> bodyParms = new LinkedHashMap<>(forcedBodyParams);
        if (getParamLocationResolved() == ParamLocation.BODY) {
            bodyParms.putAll(params);
        }

        return bodyParms;
    }

    /**
     * @return The http method to be used for this request.
     */
    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public int hashCode() {
        int result = 0;

        result = mergeHashes(result, url.hashCode(), method.hashCode());
        result = mergeHashes(result, params.hashCode(), forcedBodyParams.hashCode(), headers.hashCode());
        result = mergeHashes(result, paramLocation);
        return result;
    }

    private int mergeHashes(int current, int... addends) {
        for (int addend : addends) {
            current = 37 * current + addend;
        }

        return current;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof WebRequestBuilder) {
            WebRequestBuilder other = (WebRequestBuilder) o;

            return url.equals(other.url) &&
                    method.equals(other.method) &&
                    paramLocation == other.paramLocation &&
                    params.equals(other.params) &&
                    forcedBodyParams.equals(other.forcedBodyParams) &&
                    headers.equals(other.headers);
        }

        return super.equals(o);
    }
}
