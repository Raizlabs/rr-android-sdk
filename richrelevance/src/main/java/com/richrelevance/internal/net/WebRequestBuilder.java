package com.richrelevance.internal.net;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Builder class which allows for the construction of a request. This class
 * abstracts the implementation.
 */
public class WebRequestBuilder {

    protected static class ParamLocation {
        private static final int AUTO = 0;
        private static final int URL = 10;
        private static final int BODY = 20;
    }

    private URI uri;
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
        this(method, URI.create(url));
    }

    /**
     * Constructs a {@link WebRequestBuilder} using the given {@link HttpMethod}
     * and pointing to the given {@link URI}.
     *
     * @param method The {@link HttpMethod} to use for the request.
     * @param uri    The {@link URI} the request targets.
     */
    public WebRequestBuilder(HttpMethod method, URI uri) {
        this.method = method;
        this.uri = uri;
        this.params = new LinkedHashMap<>();
        this.forcedBodyParams = new LinkedHashMap<>();
        this.headers = new LinkedHashMap<>();
    }


    /**
     * Sets the target URL for this {@link WebRequestBuilder}.
     *
     * @param url The url the request should target.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder setURL(String url) {
        return setURI(URI.create(url));
    }

    /**
     * Sets the target {@link URI} for this {@link WebRequestBuilder}.
     *
     * @param uri the {@link URI} the request targets.
     * @return This {@link WebRequestBuilder} object to allow for chaining of calls.
     */
    public WebRequestBuilder setURI(URI uri) {
        this.uri = uri;
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
        String url = uri.toString();

        // If we should set params in the url and we have params to set, do so
        if ((getParamLocationResolved() == ParamLocation.URL) && (params.size() > 0)) {
            String queryString = "?" + HttpUtils.getQueryString(params);
            url = String.format("%s%s", uri, queryString);
        }

        return url;
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

//    /**
//     * Gets a {@link HttpURLConnection} which can be used to execute this request.
//     *
//     * @return The connection to use to execute the request.
//     */
//    public HttpURLConnection getConnection() {
//        try {
//            // Get our current URL
//            URL url = new URL(getUrl());
//            // "Open" the connection, which really just gives us the object, doesn't
//            // actually connect
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            // Set the request method appropriately
//            connection.setRequestMethod(method.getMethodName());
//
//            // Add all headers
//            for (Entry<String, String> entry : headers.entrySet()) {
//                connection.setRequestProperty(entry.getKey(), entry.getValue());
//            }
//
//            // If we have params and this is a post, we need to do output
//            // but they will be written later
//            if ((params.size() > 0 && (getParamLocationResolved() == ParamLocation.BODY)) || (forcedBodyParams.size() > 0)) {
//                connection.setDoOutput(true);
//            }
//
//            return connection;
//
//        } catch (IOException e) {
//            RRLog.e(getClass().getName(), e.getMessage(), e);
//        }
//
//        return null;
//    }
//
//    /**
//     * Called once the connection has been established. Should be called after
//     * {@link #getConnection()} to allow adding the rest of the data.
//     *
//     * @param connection The opened connection
//     */
//    public void onConnected(HttpURLConnection connection) {
//        // If we have params and this is a put, we need to write them here
//        boolean shouldAddNormalParams = (params.size() > 0 && (getParamLocationResolved() == ParamLocation.BODY));
//        boolean shouldAddForcedBodyParams = (forcedBodyParams.size() > 0);
//        LinkedHashMap<String, String> bodyParams = null;
//
//        if (shouldAddNormalParams && shouldAddForcedBodyParams) {
//            bodyParams = new LinkedHashMap<>();
//            bodyParams.putAll(params);
//            bodyParams.putAll(forcedBodyParams);
//        } else if (shouldAddNormalParams) {
//            bodyParams = params;
//        } else if (shouldAddForcedBodyParams) {
//            bodyParams = forcedBodyParams;
//        }
//
//        if (bodyParams != null) {
//            // Convert the params to a query string, and write it to the body.
//            String query = getQueryString(bodyParams);
//            try {
//                connection.getOutputStream().write(query.getBytes());
//            } catch (IOException e) {
//                RRLog.e(getClass().getName(), e.getMessage(), e);
//            }
//        }
//    }
}
