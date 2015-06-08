package com.richrelevance.placements;

import com.richrelevance.Range;
import com.richrelevance.internal.json.JSONArrayParserDelegate;
import com.richrelevance.internal.json.JSONHelper;
import com.richrelevance.utils.ParsingUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlacementsParser {

    static void parsePlacementResponseInfo(JSONObject json, PlacementResponseInfo responseInfo) {
        if (json == null || responseInfo == null) {
            return;
        }

        responseInfo.setViewGuid(json.optString("viewGuid"));

        responseInfo.setPlacements(JSONHelper.parseJSONArray(json, "placements", placementResponseParserDelegate));
    }

    static PlacementResponse parsePlacementResponse(JSONObject json) {
        if (json == null) {
            return null;
        }

        PlacementResponse response = new PlacementResponse();
        response.setPlacement(new Placement(json.optString("placement")));
        response.setStrategyMessage(json.optString("strategyMessage"));

        response.setHtmlElementId(json.optString("htmlElementId"));
        response.setHtml(json.optString("html"));

        response.setRecommendedProducts(
                JSONHelper.parseJSONArray(json, "recommendedProducts", productRecommendationParserDelegate));

        return response;
    }

    static ProductRecommendation parseProductRecommendation(JSONObject json) {
        if (json == null) {
            return null;
        }

        ProductRecommendation product = new ProductRecommendation();
        product.setId(json.optString("id"));
        product.setName(json.optString("name"));
        product.setGenre(json.optString("genre"));
        product.setRating(json.optDouble("rating"));
        product.setNumReviews(json.optLong("numReviews"));
        product.setRegionalProductSku(json.optString("regionalProductSku"));
        product.setCategoryIds(JSONHelper.parseStrings(json, "categoryIds"));
        product.setImageUrl(json.optString("imageURL"));
        product.setIsRecommendable(json.optBoolean("isRecommendable"));

        product.setPriceCents(json.optInt("priceCents"));
        product.setRegionPriceDescription(json.optString("regionPriceDescription"));
        JSONArray priceRangeCentsJson = json.optJSONArray("priceRangeCents");
        if (priceRangeCentsJson != null && priceRangeCentsJson.length() == 2) {
            try {
                int min = priceRangeCentsJson.getInt(0);
                int max = priceRangeCentsJson.getInt(1);
                product.setPriceRangeCents(new Range(min, max));
            } catch (JSONException e) {
                // Don't care, just drop it
            }
        }

        product.setClickUrl(json.optString("clickURL"));

        product.setAttributes(ParsingUtils.optValueMap(json, "attributes"));
        product.setCategories(JSONHelper.parseJSONArray(json, "categories", categoryParserDelegate));

        return product;
    }

    static Category parseCategory(JSONObject json) {
        if (json == null) {
            return null;
        }

        Category category = new Category();
        category.setId(json.optString("id"));
        category.setName(json.optString("name"));
        category.setHasChildren(json.optBoolean("hasChildren"));

        return category;
    }

    private static final JSONArrayParserDelegate<PlacementResponse> placementResponseParserDelegate =
            new JSONArrayParserDelegate<PlacementResponse>() {
                @Override
                public PlacementResponse parseObject(JSONObject json) {
                    return parsePlacementResponse(json);
                }
            };

    private static final JSONArrayParserDelegate<ProductRecommendation> productRecommendationParserDelegate =
            new JSONArrayParserDelegate<ProductRecommendation>() {
                @Override
                public ProductRecommendation parseObject(JSONObject json) {
                    return parseProductRecommendation(json);
                }
            };

    private static final JSONArrayParserDelegate<Category> categoryParserDelegate =
            new JSONArrayParserDelegate<Category>() {
                @Override
                public Category parseObject(JSONObject json) {
                    return parseCategory(json);
                }
            };
}
