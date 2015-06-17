package com.richrelevance.userPreference;

import com.richrelevance.internal.json.JSONHelper;

import org.json.JSONObject;

public class UserPreferenceParser {

    static void parseUserPreferenceResponseInfo(JSONObject json, UserPreferenceResponseInfo responseInfo) {
        if (json == null || responseInfo == null) {
            return;
        }

        responseInfo.setUserId(json.optString("userId"));

        responseInfo.setProducts(parsePreference(json, TargetType.PRODUCT));
        responseInfo.setBrands(parsePreference(json, TargetType.BRAND));
        responseInfo.setCategories(parsePreference(json, TargetType.CATEGORY));
        responseInfo.setStores(parsePreference(json, TargetType.STORE));
    }

    private static Preference parsePreference(JSONObject json, TargetType targetType) {
        Preference preference = new Preference(targetType);

        if (json != null) {
            JSONObject preferenceJson = json.optJSONObject(targetType.getResultKey());
            if (preferenceJson != null) {
                preference.setLikes(JSONHelper.parseStrings(preferenceJson, ActionType.LIKE.getKey()));
                preference.setDislikes(JSONHelper.parseStrings(preferenceJson, ActionType.DISLIKE.getKey()));
                preference.setNeutrals(JSONHelper.parseStrings(preferenceJson, ActionType.NEUTRAL.getKey()));
                preference.setNotForRecommendations(JSONHelper.parseStrings(preferenceJson, ActionType.NOT_FOR_RECS.getKey()));
            }
        }

        return preference;
    }
}
