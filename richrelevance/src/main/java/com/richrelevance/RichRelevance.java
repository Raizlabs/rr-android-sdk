package com.richrelevance;

import com.richrelevance.builders.GetUserPreferenceBuilder;
import com.richrelevance.builders.LogPurchaseBuilder;
import com.richrelevance.builders.PersonalizedRecommendationsBuilder;
import com.richrelevance.builders.PlacementsRecommendationsBuilder;
import com.richrelevance.builders.ProductViewBuilder;
import com.richrelevance.builders.SetUserPreferenceBuilder;
import com.richrelevance.builders.StrategyRecommendationsBuilder;
import com.richrelevance.builders.UserProfileBuilder;
import com.richrelevance.internal.net.WebRequestManager;

import java.util.Collection;

public class RichRelevance {

    private static WebRequestManager webRequestManager = new WebRequestManager();

    private static RichRelevanceClient defaultClient = new RichRelevanceClientImpl();

    static WebRequestManager getWebRequestManager() {
        return webRequestManager;
    }

    public static RichRelevanceClient getDefaultClient() {
        return RichRelevance.defaultClient;
    }

    public static void setDefaultClient(RichRelevanceClient client) {
        RichRelevance.defaultClient = client;
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
                .addPlacements(placements);
    }

    public static PersonalizedRecommendationsBuilder buildPersonalizedRecommendations(Placement... placements) {
        return new PersonalizedRecommendationsBuilder()
                .addPlacements(placements);
    }

    public static PersonalizedRecommendationsBuilder buildPersonalizedRecommendations(Collection<Placement> placements) {
        return new PersonalizedRecommendationsBuilder()
                .addPlacements(placements);
    }

    public static GetUserPreferenceBuilder buildUserPreferences(UserPreference... preferences) {
        return new GetUserPreferenceBuilder()
                .addPreferences(preferences);
    }

    public static GetUserPreferenceBuilder buildUserPreferences(Collection<UserPreference> preferences) {
        return new GetUserPreferenceBuilder()
                .addPreferences(preferences);
    }

    public static UserProfileBuilder buildUserProfileField(UserProfileField... fields) {
        return new UserProfileBuilder()
                .addFields(fields);
    }

    public static UserProfileBuilder buildUserProfileField(Collection<UserProfileField> fields) {
        return new UserProfileBuilder()
                .addFields(fields);
    }

    // endregion Fetching

    // region Tracking

    public static RequestBuilder<Void> buildProductView(Placement placement, String productId) {
        return new ProductViewBuilder()
                .setProductId(productId);
    }

    public static LogPurchaseBuilder buildLogPurchase(String orderNumber) {
        return new LogPurchaseBuilder()
                .setOrderNumber(orderNumber);
    }

    public static SetUserPreferenceBuilder buildTrackUserPreference(
            SetUserPreferenceBuilder.ActionType action,
            SetUserPreferenceBuilder.TargetType target,
            String... ids) {

        return new SetUserPreferenceBuilder()
                .setIds(ids)
                .setActionType(action)
                .setTargetType(target);
    }

    public static RequestBuilder<Void> buildProductLike(String... productIds) {
        return buildTrackUserPreference(
                SetUserPreferenceBuilder.ActionType.Like,
                SetUserPreferenceBuilder.TargetType.Product,
                productIds);
    }

    // endregion Tracking


}