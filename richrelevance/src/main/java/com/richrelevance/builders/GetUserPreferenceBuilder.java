package com.richrelevance.builders;

import android.net.Uri;

import com.richrelevance.RequestBuilder;
import com.richrelevance.ResponseInfo;
import com.richrelevance.UserPreference;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

import java.util.Collection;

public class GetUserPreferenceBuilder extends RequestBuilder<ResponseInfo> {

    public GetUserPreferenceBuilder addPreferences(UserPreference... preferences) {
        return this;
    }

    public GetUserPreferenceBuilder addPreferences(Collection<UserPreference> preferences) {
        return this;
    }

    @Override
    protected RequestBuilder<ResponseInfo> setUserId(String userId) {
        String endpointPath = "/user/preference/" + Uri.encode(userId);
        setUrl(getFullUrl(endpointPath));
        return this;
    }

    @Override
    protected ResponseInfo createNewResult() {
        return null;
    }

    @Override
    protected String getEndpointPath() {
        return null;
    }

    @Override
    protected void populateResponse(WebResponse response, JSONObject json, ResponseInfo responseInfo) {

    }
}