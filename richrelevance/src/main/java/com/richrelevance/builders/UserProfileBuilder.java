package com.richrelevance.builders;

import android.net.Uri;

import com.richrelevance.RequestBuilder;
import com.richrelevance.UserProfileField;
import com.richrelevance.internal.net.WebResponse;

import java.util.Collection;

public class UserProfileBuilder extends RequestBuilder<Object> {

    public UserProfileBuilder addFields(UserProfileField... fields) {
        return this;
    }

    public UserProfileBuilder addFields(Collection<UserProfileField> fields) {
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
