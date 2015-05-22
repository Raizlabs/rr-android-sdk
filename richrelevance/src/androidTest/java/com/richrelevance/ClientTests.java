package com.richrelevance;

import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebRequestManager;
import com.richrelevance.internal.net.WebResponse;
import com.richrelevance.internal.net.WebResultInfo;

import junit.framework.TestCase;

import org.json.JSONObject;

public class ClientTests extends TestCase {

    private static final String VALUE_CONFIG1 = "config1";
    private static final String VALUE_CONFIG2 = "config2";
    
    private ClientConfiguration config1, config2;
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        config1 = new ClientConfiguration(VALUE_CONFIG1, VALUE_CONFIG1);
        config1.setEndpoint(VALUE_CONFIG1, false);
        config1.setSessionId(VALUE_CONFIG1);
        config1.setUserId(VALUE_CONFIG1);

        config2 = new ClientConfiguration(VALUE_CONFIG2, VALUE_CONFIG2);
        config2.setEndpoint(VALUE_CONFIG2, true);
        config2.setSessionId(VALUE_CONFIG2);
        config2.setUserId(VALUE_CONFIG2);
    }

    public void testConfiguration() {
        TestRequestBuilder builder = new TestRequestBuilder();
        RichRelevance.getDefaultClient().setConfiguration(config1);

        WebRequestBuilder webRequestBuilder = builder.build();

        assertTrue(webRequestBuilder.getFullUrl().contains(VALUE_CONFIG1));
        assertTrue(webRequestBuilder.getFullUrl().startsWith("http:"));
        assertConfigurationValues(webRequestBuilder, VALUE_CONFIG1);

        RichRelevance.getDefaultClient().setConfiguration(config2);
        webRequestBuilder = builder.build();

        assertTrue(webRequestBuilder.getFullUrl().contains(VALUE_CONFIG2));
        assertTrue(webRequestBuilder.getFullUrl().startsWith("https:"));
        assertConfigurationValues(webRequestBuilder, VALUE_CONFIG2);
    }

    public void testEndToEnd() {
        String url = "http://recs.richrelevance.com/rrserver/api/rrPlatform/recsUsingStrategy"
                + "?apiKey=" + Constants.TestApiKeys.API_KEY
                + "&apiClientKey=" + Constants.TestApiKeys.API_CLIENT_KEY
                + "&strategyName=SiteWideBestSellers";
        SimpleJsonRequest request = new SimpleJsonRequest(url);

        WebRequestManager webRequestManager = new WebRequestManager();
        WebResultInfo<JSONObject> result = webRequestManager.execute(request);
        assertNotNull(result);
        assertEquals(200, result.getResponseCode());

        JSONObject json = result.getResult();
        assertNotNull(json);
        assertTrue(json.has("recommendedProducts"));

    }

    private void assertConfigurationValues(WebRequestBuilder builder, String value) {
        assertEquals(value, builder.getParam("apiKey"));
        assertEquals(value, builder.getParam("apiClientKey"));
        assertEquals(value, builder.getParam("userId"));
        assertEquals(value, builder.getParam("sessionId"));
    }

    private static class TestRequestBuilder extends RequestBuilder<Void> {

        @Override
        protected String getEndpointPath() {
            return "test";
        }

        @Override
        protected Void parseResponse(WebResponse response) {
            return null;
        }
    }
}
