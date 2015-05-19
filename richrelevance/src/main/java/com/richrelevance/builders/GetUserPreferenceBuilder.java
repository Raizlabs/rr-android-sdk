package com.richrelevance.builders;

import android.net.Uri;

import com.richrelevance.RequestBuilder;
import com.richrelevance.UserPreference;
import com.richrelevance.internal.net.WebResponse;

import java.util.Collection;

public class GetUserPreferenceBuilder extends RequestBuilder<Object> {

    public GetUserPreferenceBuilder addPreferences(UserPreference... preferences) {
        return this;
    }

    public GetUserPreferenceBuilder addPreferences(Collection<UserPreference> preferences) {
        return this;
    }

    @Override
    protected RequestBuilder<Object> setUserId(String userId) {
        String endpointPath = "/user/preference/" + Uri.encode(userId);
        setUrl(getFullUrl(endpointPath));
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return null;
    }

    @Override
    protected Object parseResponse(WebResponse response) {
        return null;
    }
}