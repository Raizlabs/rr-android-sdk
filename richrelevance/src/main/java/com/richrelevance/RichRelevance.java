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
import com.richrelevance.internal.net.WebResultInfo;

import java.util.Collection;

public class RichRelevance {

    static String apiKey;
    static String apiClientKey;
    static String userId;
    static String sessionId;

    public static void setApiKey(String apiKey) {
        RichRelevance.apiKey = apiKey;
    }

    public static void setApiClientKey(String apiClientKey) {
        RichRelevance.apiClientKey = apiClientKey;
    }

    public static void setUserId(String userId) {
        RichRelevance.userId = userId;
    }

    public static void setSessionId(String sessionId) {
        RichRelevance.sessionId = sessionId;
    }

    public static <T> void executeRequest(final RequestBuilder<T> builder) {
        CallbackWebListener<T> listener = new CallbackWebListener<>(builder.getCallback());

        new WebRequestManager().executeInBackground(builder.getWebRequest(), listener);
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

    private static class CallbackWebListener<T> implements WebRequestManager.WebRequestListener<T> {

        private Callback<T> callback;

        public CallbackWebListener(Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onRequestComplete(WebResultInfo<T> resultInfo) {
            // TODO - Error states and status verification
            if (callback != null) {
                callback.onResult(resultInfo.getResult());
            }
        }
    }
}