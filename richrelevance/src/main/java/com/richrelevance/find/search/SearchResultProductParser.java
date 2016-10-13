package com.richrelevance.find.search;

import android.util.Log;

import com.richrelevance.internal.json.JSONArrayParserDelegate;
import com.richrelevance.internal.json.JSONHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultProductParser {

    static void parseSearchResponseInfo(JSONObject json, SearchResponseInfo responseInfo) {
        if (json == null || responseInfo == null) {
            return;
        }

        try {
            JSONArray resultPlacements = json.getJSONArray("placements");
            if (resultPlacements != null) {

                // The SDK only currently supports a single placement specification for Search request and single placement handling from the response.
                JSONObject supportedPlacement = resultPlacements.getJSONObject(0);
                if (supportedPlacement != null) {
                    responseInfo.setFacets(JSONHelper.parseJSONArray(supportedPlacement.getJSONArray("facets"), facetResponseParserDelegate));
                    responseInfo.setProducts(JSONHelper.parseJSONArray(supportedPlacement.getJSONArray("docs"), searchResultProductResponseParserDelegate));
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    static Facet parseFacet(JSONObject json) {
        if (json == null) {
            return null;
        }

        final Facet facet = new Facet();
        facet.setType(json.optString(Facet.Keys.TYPE));
        facet.setFilters(JSONHelper.parseJSONArray(json, Facet.Keys.FITERS, new JSONArrayParserDelegate<Facet.Filter>() {
                    @Override
                    public Facet.Filter parseObject(JSONObject json) {
                        return parseFilter(facet, json);
                    }
                })
        );

        return facet;
    }

    static Facet.Filter parseFilter(Facet facet, JSONObject json) {
        Facet.Filter filter = null;
        if (json != null) {
            try {
                filter = facet.new Filter();
                filter.setCount(json.getInt(Facet.Filter.Keys.COUNT));
                filter.setFilter(json.getString(Facet.Filter.Keys.FILTER));
                filter.setValue(json.getString(Facet.Filter.Keys.VALUE));

            } catch (JSONException e) {
                Log.e(SearchResultProductParser.class.getSimpleName(), "Unable to parse Filter object due to missing expected json response fields");
            }
        }
        return filter;
    }

    static SearchResultProduct parseSearchResultProduct(JSONObject json) {
        SearchResultProduct product = null;
        if (json != null) {

            try {
                product = new SearchResultProduct();
                product.setId(json.getString(SearchResultProduct.Keys.ID));
                product.setName(json.getString(SearchResultProduct.Keys.NAME));
                product.setClickUrl(json.optString(SearchResultProduct.Keys.CLICK_URL));
                product.setImageId(json.optString(SearchResultProduct.Keys.IMAGE_ID));
                product.setLinkId(json.optString(SearchResultProduct.Keys.LINK_ID));
                product.setNumReviews(json.optInt(SearchResultProduct.Keys.NUM_REVIEWS));
                product.setDescription(json.optString(SearchResultProduct.Keys.DESCRIPTION));
                product.setScore(json.optDouble(SearchResultProduct.Keys.SCORE));
                product.setPriceCents(json.optInt(SearchResultProduct.Keys.PRICE_CENTS));
                product.setSalesPriceCents(json.optInt(SearchResultProduct.Keys.SALES_PRICE_CENTS));
                product.setBrand(json.optString(SearchResultProduct.Keys.BRAND));

                product.setCategoryNames(listify(json.optJSONArray(SearchResultProduct.Keys.CATEGORY_NAMES)));
                product.setCategoryIds(listify(json.optJSONArray(SearchResultProduct.Keys.CATEGORY_IDS)));

            } catch (JSONException e) {
                Log.e(SearchResultProductParser.class.getSimpleName(), "Unable to parse SearchResultProduct object due to missing expected json response fields");
            }
        }

        return product;
    }

    private static final JSONArrayParserDelegate<Facet> facetResponseParserDelegate =
            new JSONArrayParserDelegate<Facet>() {
                @Override
                public Facet parseObject(JSONObject json) {
                    return parseFacet(json);
                }
            };


    private static final JSONArrayParserDelegate<SearchResultProduct> searchResultProductResponseParserDelegate =
            new JSONArrayParserDelegate<SearchResultProduct>() {
                @Override
                public SearchResultProduct parseObject(JSONObject json) {
                    return parseSearchResultProduct(json);
                }
            };

    private static List<String> listify(JSONArray jsonArray) throws JSONException {
        if(jsonArray == null) {
            return null;
        }

        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }

        return list;
    }
}
