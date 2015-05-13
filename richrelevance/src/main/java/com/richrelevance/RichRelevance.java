package com.richrelevance;

import com.richrelevance.builders.GetUserPreferenceBuilder;
import com.richrelevance.builders.LogPurchaseBuilder;
import com.richrelevance.builders.PlacementsBuilder;
import com.richrelevance.builders.ProductViewBuilder;
import com.richrelevance.builders.SetUserPreferenceBuilder;
import com.richrelevance.builders.StrategyBuilder;
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

    public static RequestBuilder<Void> buildProductView(String productId) {
        return new ProductViewBuilder()
                .setProductId(productId);
    }

    public static StrategyBuilder buildStrategyRecommendations(StrategyType strategy) {
        return new StrategyBuilder()
                .setStrategy(strategy);
    }

    public static PlacementsBuilder buildPlacements(String... placements) {
        return new PlacementsBuilder()
                .addPlacements(placements);
    }

    public static PlacementsBuilder buildPlacements(Collection<String> placements) {
        return new PlacementsBuilder()
                .addPlacements(placements);
    }

    public static LogPurchaseBuilder buildLogPurchase(long orderNumber) {
        return new LogPurchaseBuilder()
                .setOrderNumber(orderNumber);
    }

    public static RequestBuilder<Void> buildProductLike(String productId) {
        return new SetUserPreferenceBuilder()
                .setProductId(productId)
                .setActionType(SetUserPreferenceBuilder.ActionType.Like)
                .setTargetType(SetUserPreferenceBuilder.TargetType.Product);
    }

    public static GetUserPreferenceBuilder buildUserPreferences(UserPreference... preferences) {
        return new GetUserPreferenceBuilder()
                .addPreferences(preferences);
    }

    public static GetUserPreferenceBuilder buildUserPreferences(Collection<UserPreference> preferences) {
        return new GetUserPreferenceBuilder()
                .addPreferences(preferences);
    }

    public static UserProfileBuilder buildUserProfile(UserProfileField... fields) {
        return new UserProfileBuilder()
                .addFields(fields);
    }

    public static UserProfileBuilder buildUserProfile(Collection<UserProfileField> fields) {
        return new UserProfileBuilder()
                .addFields(fields);
    }

    public static UserProfileBuilder buildPurchaseHistory() {
        return new UserProfileBuilder()
                .addFields(UserProfileField.ORDERS);
    }


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