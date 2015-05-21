package com.richrelevance;

import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebResponse;

import junit.framework.TestCase;

public class RequestBuilderTests extends TestCase {

    private static final String TEST_KEY = "TestKey";
    private static final String TEST_VALUE = "TestValue";
    private static final String TEST_ENDPOINT_PATH = "some/url/path";

    public void testRequestBuilder() {

        TestBuilder builder = new TestBuilder();
        builder.addParameter(TEST_KEY, TEST_VALUE);

        WebRequestBuilder webRequest = builder.build();
        webRequest.setSendParamsInBody();
        assertEquals(TEST_VALUE, webRequest.getBodyParams().get(TEST_KEY));
        assertTrue(webRequest.getFullUrl().contains(TEST_ENDPOINT_PATH));
    }

    private static class TestBuilder extends RequestBuilder<Void> {

        @Override
        protected String getEndpointPath() {
            return TEST_ENDPOINT_PATH;
        }

        @Override
        protected Void parseResponse(WebResponse response) {
            return null;
        }
    }
}
