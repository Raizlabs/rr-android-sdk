package com.richrelevance;

import com.richrelevance.builders.PersonalizedRecommendationsBuilder;
import com.richrelevance.internal.net.WebRequestManager;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.recommendations.PlacementsRecommendationsBuilder;
import com.richrelevance.recommendations.StrategyRecommendationsBuilder;
import com.richrelevance.recommendations.StrategyType;
import com.richrelevance.userPreference.ActionType;
import com.richrelevance.userPreference.TargetType;
import com.richrelevance.userPreference.UserPreferenceBuilder;
import com.richrelevance.userProfile.UserProfileBuilder;
import com.richrelevance.userProfile.UserProfileField;

import java.util.Arrays;
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
     *
     * @param isProduction True to act like a production build, false not to.
     */
    public static void setIsProduction(boolean isProduction) {
        RichRelevance.isProduction = isProduction;
    }

    /**
     * Manually enables or disables all logging in the SDK.
     *
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
        // TODO
        return null;
    }

    public static RequestBuilder<?> buildLogPurchase(String orderNumber) {
        // TODO
        return null;
    }

    public static UserPreferenceBuilder buildSetUserPreference(TargetType target, ActionType action, String... ids) {
        return new UserPreferenceBuilder(target, action, ids);
    }

    public static RequestBuilder<?> buildProductLike(String... productIds) {
        return buildSetUserPreference(TargetType.PRODUCT, ActionType.LIKE, productIds);
    }

    // endregion Tracking


}