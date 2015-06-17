package com.richrelevance.userPreferences;

import com.richrelevance.BaseTestCase;
import com.richrelevance.ClientConfiguration;
import com.richrelevance.RequestBuilder;
import com.richrelevance.RequestBuilderAccessor;
import com.richrelevance.RichRelevanceClient;
import com.richrelevance.TestClient;
import com.richrelevance.userPreference.ActionType;
import com.richrelevance.userPreference.TargetType;
import com.richrelevance.userPreference.UserPreferenceBuilder;

public class UserPreferencesBuilderTests extends BaseTestCase {

    private RichRelevanceClient client;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ClientConfiguration config = new ClientConfiguration("apiKey", "apiClientKey");
        config.setUserId("RZTestUser");
        config.setSessionId("session");

        client = new TestClient(config);
    }

    public void testActionTypes() {
        assertEquals("dislike", ActionType.DISLIKE.getKey());
        assertEquals("like", ActionType.LIKE.getKey());
        assertEquals("neutral", ActionType.NEUTRAL.getKey());
        assertEquals("notForRecs", ActionType.NOT_FOR_RECS.getKey());
    }

    public void testTargetTypes() {
        assertEquals("brand", TargetType.BRAND.getRequestKey());
        assertEquals("pref_brand", TargetType.BRAND.getResultKey());
        assertEquals("category", TargetType.CATEGORY.getRequestKey());
        assertEquals("pref_category", TargetType.CATEGORY.getResultKey());
        assertEquals("product", TargetType.PRODUCT.getRequestKey());
        assertEquals("pref_product", TargetType.PRODUCT.getResultKey());
        assertEquals("store", TargetType.STORE.getRequestKey());
        assertEquals("pref_store", TargetType.STORE.getResultKey());
    }

    public void testPath() {
        RequestBuilder<?> getBuilder = new UserPreferenceBuilder(TargetType.BRAND);
        getBuilder.setClient(client);
        RequestBuilderAccessor getAccessor = new RequestBuilderAccessor(getBuilder);
        assertTrue(getAccessor.getUrl().startsWith("https://recs.richrelevance.com/rrserver/api/user/preference/RZTestUser"));

        RequestBuilder<?> setBuilder = new UserPreferenceBuilder(TargetType.BRAND, ActionType.DISLIKE, "item");
        setBuilder.setClient(client);
        RequestBuilderAccessor setAccessor = new RequestBuilderAccessor(setBuilder);
        assertTrue(setAccessor.getUrl().startsWith("https://recs.richrelevance.com/rrserver/api/user/preference"));
    }

    public void testConstruction() {
        RequestBuilder<?> builder = new UserPreferenceBuilder(TargetType.BRAND, ActionType.LIKE, "");
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);
        assertEquals("brand", accessor.getParamValue(UserPreferenceBuilder.Keys.TARGET_TYPE));
        assertEquals("like", accessor.getParamValue(UserPreferenceBuilder.Keys.ACTION_TYPE));
    }

    public void testSetViewGuid() {
        RequestBuilder<?> builder = new UserPreferenceBuilder(TargetType.BRAND)
                .setViewGuid("ab");
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);

        assertEquals("ab", accessor.getParamValue(UserPreferenceBuilder.Keys.VIEW_GUID));
    }

    public void testSetPreferences() {
        RequestBuilder<?> builder = new UserPreferenceBuilder(TargetType.BRAND)
                .setPreferences("a", "b");
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);
        assertEquals("a|b", accessor.getParamValue(UserPreferenceBuilder.Keys.PREFERENCES));
    }
}