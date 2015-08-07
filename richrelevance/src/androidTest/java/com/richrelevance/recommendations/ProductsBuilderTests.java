package com.richrelevance.recommendations;

import com.richrelevance.BaseTestCase;
import com.richrelevance.ClientConfiguration;
import com.richrelevance.RequestBuilder;
import com.richrelevance.RequestBuilderAccessor;
import com.richrelevance.RichRelevanceClient;
import com.richrelevance.TestClient;
import com.richrelevance.userPreference.ActionType;
import com.richrelevance.userPreference.FieldType;

public class ProductsBuilderTests extends BaseTestCase{
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
        assertEquals("brand", FieldType.BRAND.getRequestKey());
        assertEquals("pref_brand", FieldType.BRAND.getResultKey());
        assertEquals("category", FieldType.CATEGORY.getRequestKey());
        assertEquals("pref_category", FieldType.CATEGORY.getResultKey());
        assertEquals("product", FieldType.PRODUCT.getRequestKey());
        assertEquals("pref_product", FieldType.PRODUCT.getResultKey());
        assertEquals("store", FieldType.STORE.getRequestKey());
        assertEquals("pref_store", FieldType.STORE.getResultKey());
    }

    public void testPath() {
        RequestBuilder<?> getBuilder = new ProductBuilder("11111", "22222");
        getBuilder.setClient(client);
        RequestBuilderAccessor getAccessor = new RequestBuilderAccessor(getBuilder);
        assertTrue(getAccessor.getUrl().startsWith("https://recs.richrelevance.com/rrserver/api/rrPlatform/getProducts"));
    }

    public void testConstruction() {
        RequestBuilder<?> builder = new ProductBuilder("11111");
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);
        assertEquals("11111", accessor.getParamValue(ProductBuilder.Keys.PRODUCTID));
    }

    public void testSetProducts() {
        RequestBuilder<?> builder = new ProductBuilder().setProducts("11111");
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);
        assertEquals("11111", accessor.getParamValue(ProductBuilder.Keys.PRODUCTID));
    }
}
