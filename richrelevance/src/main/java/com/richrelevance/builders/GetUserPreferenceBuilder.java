package com.richrelevance.builders;

import com.richrelevance.RequestBuilder;
import com.richrelevance.UserPreference;
import com.richrelevance.internal.net.responses.WebResponse;

import java.util.Collection;

public class GetUserPreferenceBuilder extends RequestBuilder<Object> {

    public GetUserPreferenceBuilder addPreferences(UserPreference... preferences) {
        return this;
    }

    public GetUserPreferenceBuilder addPreferences(Collection<UserPreference> preferences) {
        return this;
    }

    @Override
    protected Object parseResponse(WebResponse response) {
        return null;
    }
}