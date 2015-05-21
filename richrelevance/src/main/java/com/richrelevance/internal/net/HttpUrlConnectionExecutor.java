package com.richrelevance.internal.net;

import com.richrelevance.RRLog;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Implementation of a {@link WebRequestExecutor} which runs the request over an
 * {@link HttpURLConnection}.
 *
 * @param <Result> The type of the result that will be returned.
 */
class HttpUrlConnectionExecutor<Result> implements WebRequestExecutor<Result> {

    private WebRequest<Result> request;
    private int connectionTimeout;
    private int readTimeout;

    public HttpUrlConnectionExecutor(WebRequest<Result> request, int connectionTimeout, int readTimeout) {
        this.request = request;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    @Override
    public WebResultInfo<Result> execute() {
        WebRequestBuilder builder = request.getRequestBuilder();
        HttpURLConnection connection = getConnection(builder);

        if (connection != null) {
            connection.setConnectTimeout(connectionTimeout);
            connection.setReadTimeout(readTimeout);
            try {
                connection.connect();
                onConnected(builder, connection);

                WebResponse response = new HttpURLConnectionResponse(connection);
                Result result = request.translate(response);
                return new BasicWebResultInfo<>(
                        result,
                        System.currentTimeMillis(),
                        response.getResponseCode(),
                        response.getResponseMessage());
            } catch (IOException e) {
                RRLog.i(getClass().getSimpleName(), "Error opening connection. Connection failed.", e);
            } finally {
                connection.disconnect();
            }
        }

        return new FailedResultInfo<>(System.currentTimeMillis());
    }

    /**
     * Gets a {@link HttpURLConnection} which can be used to execute this request.
     *
     * @return The connection to use to execute the request.
     */
    private HttpURLConnection getConnection(WebRequestBuilder builder) {
        try {
            // Get our current URL
            URL url = new URL(builder.getFullUrl());
            // "Open" the connection, which really just gives us the object, doesn't
            // actually connect
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Set the request method appropriately
            connection.setRequestMethod(builder.getMethod().getMethodName());

            // Add all headers
            for (Map.Entry<String, String> entry : builder.getHeaders().entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            // If we have params and this is a post, we need to do output
            // but they will be written later
            if (builder.getBodyParams().size() > 0) {
                connection.setDoOutput(true);
            }

            return connection;

        } catch (IOException e) {
            RRLog.e(getClass().getName(), e.getMessage(), e);
        }

        return null;
    }


    /**
     * Call after the connection has been established to allow adding the rest of the data.
     *
     * @param connection The opened connection.
     */
    private void onConnected(WebRequestBuilder builder, HttpURLConnection connection) {
        Map<String, String> bodyParams = builder.getBodyParams();
        if ((bodyParams != null) && !bodyParams.isEmpty()) {
            // Convert the params to a query string, and write it to the body.
            String query = HttpUtils.getQueryString(bodyParams);
            try {
                connection.getOutputStream().write(query.getBytes());
            } catch (IOException e) {
                RRLog.e(getClass().getName(), e.getMessage(), e);
            }
        }
    }

    public static final WebRequestExecutorFactory FACTORY = new WebRequestExecutorFactory() {
        @Override
        public <T> WebRequestExecutor<T> create(WebRequest<T> request, int connectTimeout, int readTimeout) {
            return new HttpUrlConnectionExecutor<T>(request, connectTimeout, readTimeout);
        }
    };
}
