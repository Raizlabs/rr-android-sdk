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

    private static RichRelevanceClient defaultClient = newClient();

    // Currently doesn't work in libraries built with Gradle
    // Leaving for forwards compatibility if this ever gets fixed
    // This defaults to false, so this is "safe" since we will always assume the worst
    private static boolean isProduction = !BuildConfig.DEBUG;

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
     * @param client The default client to use for requests.
     */
    public static void setDefaultClient(RichRelevanceClient client) {
        RichRelevance.defaultClient = client;
    }

    /**
     * Creates a new {@link RichRelevanceClient}.
     * @return A new client.
     */
    public static RichRelevanceClient newClient() {
        return new RichRelevanceClientImpl();
    }

    // TODO - If isProduction() is just for logging, we might as well remove it since logging can already be configured manually.
    /**
     * @return False if the app is known to be built configured for debug, true if it is unknown or built for
     * production.
     */
    static boolean isProduction() {
        return isProduction;
    }

    /**
     * Manually sets the configuration to act like this is a production build.
     * @param isProduction True to act like a production build, false not to.
     */
    public static void setIsProduction(boolean isProduction) {
        RichRelevance.isProduction = isProduction;
    }

    /**
     * Manually enables or disables all logging in the SDK.
     * @param enabled True to enable logging, false to disable it.
     */
    public static void setLoggingEnabled(boolean enabled) {
        RRLog.setLoggingEnabled(enabled);
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