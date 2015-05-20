package com.richrelevance.mocking;

import android.content.Context;

import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebResultInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Class which maintains a set of mocked responses.
 */
public class MockResponseManager {

    private static final MockResponseManager INSTANCE = new MockResponseManager();

    public static MockResponseManager getInstance() {
        return INSTANCE;
    }

    private Context context;
    private Map<WebRequestBuilder, MockWebResponse> responseMap;

    private MockResponseManager() {
        responseMap = new HashMap<>();
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Gets a mocked result which corresponds to the given request or a failure if none is found.
     * @param request The request to get a response for.
     * @param <T> The result type of the request.
     * @return The mocked result of the request.
     */
    public <T> WebResultInfo<T> getResult(WebRequest<T> request) {
        MockWebResponse response = responseMap.get(request.getRequestBuilder());

        if (response != null) {
            return new MockWebResultInfo<>(response.getResponseCode(), request.translate(response));
        }

        return new MockWebResultInfo<>(WebResultInfo.RESPONSE_CODE_FAILED, null);
    }

    /**
     * Stores the given response as the response to the given request. All requests with an equivalent request builder
     * will be responded to using the given response.
     * @param requestBuilder The request builder to test for equality against future requests.
     * @param response The response to return if a matching request is sent.
     */
    public void putResponse(WebRequestBuilder requestBuilder, ResponseBuilder response) {
        responseMap.put(requestBuilder, new MockWebResponse(requestBuilder, response, context));
    }

    private static class MockWebResultInfo<Result> implements WebResultInfo<Result> {

        private int responseCode;
        private Result result;
        private long timestamp;

        public MockWebResultInfo(int responseCode, Result result) {
            this.responseCode = responseCode;
            this.result = result;
            this.timestamp = System.currentTimeMillis();
        }

        @Override
        public long getResponseTimestamp() {
            return timestamp;
        }

        @Override
        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        @Override
        public int getResponseCode() {
            return responseCode;
        }

        @Override
        public String getResponseMessage() {
            return null;
        }
    }
}
