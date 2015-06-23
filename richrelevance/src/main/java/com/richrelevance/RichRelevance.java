package com.richrelevance;

import android.content.Context;

import com.richrelevance.internal.net.WebRequestManager;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.recommendations.PlacementsRecommendationsBuilder;
import com.richrelevance.recommendations.Product;
import com.richrelevance.recommendations.RecommendedProduct;
import com.richrelevance.recommendations.StrategyRecommendationsBuilder;
import com.richrelevance.recommendations.StrategyType;
import com.richrelevance.userPreference.ActionType;
import com.richrelevance.userPreference.TargetType;
import com.richrelevance.userPreference.UserPreferenceBuilder;
import com.richrelevance.userProfile.UserProfileBuilder;
import com.richrelevance.userProfile.UserProfileField;

import java.util.Collection;

public class RichRelevance {

    private static WebRequestManager webRequestManager = new WebRequestManager();

    private static RichRelevanceClient defaultClient = newClient();

    static WebRequestManager getWebRequestManager() {
        return webRequestManager;
    }

    /**
     * @return The default client used for requests.
     */
    public static RichRelevanceClient getDefaultClient() {
        return RichRelevance.defaultClient;
    }

    /**
     * Sets the default {@link RichRelevanceClient} to use for requests.
     *
     * @param client The default client to use for requests.
     */
    public static void setDefaultClient(RichRelevanceClient client) {
        RichRelevance.defaultClient = client;
    }

    /**
     * Creates a new {@link RichRelevanceClient}.
     *
     * @return A new client.
     */
    public static RichRelevanceClient newClient() {
        return new RichRelevanceClientImpl();
    }

    /**
     * Manually enables or disables all logging in the SDK.
     *
     * @param enabled True to enable logging, false to disable it.
     */
    public static void setLoggingEnabled(boolean enabled) {
        RRLog.setLoggingEnabled(enabled);
    }

    public static void init(Context context) {
        ClickTrackingManager.getInstance().init(context);
    }

    // region Fetching

    public static StrategyRecommendationsBuilder buildRecommendationsUsingStrategy(StrategyType strategy) {
        return new StrategyRecommendationsBuilder()
                .setStrategy(strategy);
    }

    public static PlacementsRecommendationsBuilder buildRecommendationsForPlacements(Placement... placements) {
        return new PlacementsRecommendationsBuilder()
                .addPlacements(placements);
    }

    public static PlacementsRecommendationsBuilder buildRecommendationsForPlacements(Collection<Placement> placements) {
        return new PlacementsRecommendationsBuilder()
                .setPlacements(placements);
    }

    public static UserPreferenceBuilder buildGetUserPreferences(TargetType... fields) {
        return new UserPreferenceBuilder(fields);
    }

    public static UserPreferenceBuilder buildGetUserPreferences(Collection<TargetType> fields) {
        return new UserPreferenceBuilder(fields);
    }

    public static UserProfileBuilder buildUserProfile(UserProfileField... fields) {
        return new UserProfileBuilder()
                .setFields(fields);
    }

    public static UserProfileBuilder buildUserProfile(Collection<UserProfileField> fields) {
        return new UserProfileBuilder()
                .setFields(fields);
    }

    // endregion Fetching

    // region Tracking

    public static RequestBuilder<?> buildProductView(Placement placement, String productId) {
        return new PlacementsRecommendationsBuilder()
                .addPlacements(placement)
                .setProductIds(productId);
    }

    public static RequestBuilder<?> buildLogPurchase(Placement placement, String orderId, Product... products) {
        return new PlacementsRecommendationsBuilder()
                .addPlacements(placement)
                .setOrderId(orderId)
                .addPurchasedProducts(products);
    }

    public static RequestBuilder<?> buildLogPurchase(Placement placement, String orderId, Collection<Product> products) {
        return new PlacementsRecommendationsBuilder()
                .addPlacements(placement)
                .setOrderId(orderId)
                .addPurchasedProducts(products);
    }

    public static UserPreferenceBuilder buildSetUserPreference(TargetType target, ActionType action, String... ids) {
        return new UserPreferenceBuilder(target, action, ids);
    }

    public static RequestBuilder<?> buildProductLike(String... productIds) {
        return buildSetUserPreference(TargetType.PRODUCT, ActionType.LIKE, productIds);
    }

    public static void trackClick(RecommendedProduct product) {
        ClickTrackingManager.getInstance().trackClick(product.getClickUrl());
    }

    public static void flushClickTracking() {
        ClickTrackingManager.getInstance().flush();
    }

    // endregion Tracking


}