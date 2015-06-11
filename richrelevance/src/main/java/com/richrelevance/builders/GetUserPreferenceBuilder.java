package com.richrelevance.builders;

import com.richrelevance.ClientConfiguration;
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
    protected ResponseInfo createNewResult() {
        return null;
    }

    @Override
    protected String getEndpointPath(ClientConfiguration configuration) {
        return null;
    }

    @Override
    protected void populateResponse(WebResponse response, JSONObject json, ResponseInfo responseInfo) {

    }
}