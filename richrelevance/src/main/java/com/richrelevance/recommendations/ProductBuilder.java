package com.richrelevance.recommendations;

import com.richrelevance.ClientConfiguration;
import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.WebResponse;
import com.richrelevance.utils.Utils;

import org.json.JSONObject;

import java.util.Collection;

public class ProductBuilder extends RequestBuilder<ProductResponseInfo> {

    /**
     * Constructs a builder which retrieves list of products for the given product ids.
     *
     * @param productIds The fields to retrieve.
     */
    public ProductBuilder(String... productIds) {
        setProducts(productIds);
    }

    /**
     * Constructs a builder which retrieves list of products for the given product ids.
     *
     * @param productIds The fields to retrieve.
     */
    public ProductBuilder(Collection<String> productIds) {
        setProducts(productIds);
    }

    /**
     * Which products should be returned?
     *
     * @param productIds The products to return.
     *
     * @return This builder for chaining method calls.
     */
    public ProductBuilder setProducts(String... productIds) {
        setProducts(Utils.safeAsList(productIds));
        return this;
    }

    /**
     * Which products should be returned?
     *
     * @param productIds The products to return.
     *
     * @return This builder for chaining method calls.
     */
    public ProductBuilder setProducts(Collection<String> productIds) {
        if(productIds != null) {
            setListParameter(Keys.PRODUCTID, productIds);
        } else {
            removeParameter(Keys.PRODUCTID);
        }
        return this;
    }

    @Override
    protected ProductResponseInfo createNewResult() {
        return new ProductResponseInfo();
    }

    @Override
    protected String getEndpointPath(ClientConfiguration configuration) {
        return "rrserver/api/rrPlatform/getProducts";
    }

    @Override
    protected void populateResponse(WebResponse response, JSONObject json, ProductResponseInfo responseInfo) {
        ProductsParser.parseProductResponseInfo(json, responseInfo);
    }

    public static class Keys {
        public static final String PRODUCTID = "productId";
    }
}
