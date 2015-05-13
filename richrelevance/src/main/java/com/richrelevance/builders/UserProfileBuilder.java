package com.richrelevance.builders;

import com.richrelevance.RequestBuilder;
import com.richrelevance.UserProfileField;
import com.richrelevance.internal.net.responses.WebResponse;

import java.util.Collection;

public class UserProfileBuilder extends RequestBuilder<Object> {

    public UserProfileBuilder addFields(UserProfileField... fields) {
        return this;
    }

    public UserProfileBuilder addFields(Collection<UserProfileField> fields) {
        return this;
    }

    @Override
    protected Object parseResponse(WebResponse response) {
        return null;
    }
}
