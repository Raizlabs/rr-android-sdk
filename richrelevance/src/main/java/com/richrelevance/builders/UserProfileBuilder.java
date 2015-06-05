package com.richrelevance.builders;

import android.net.Uri;

import com.richrelevance.RequestBuilder;
import com.richrelevance.ResponseInfo;
import com.richrelevance.UserProfileField;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

import java.util.Collection;

public class UserProfileBuilder extends RequestBuilder<ResponseInfo> {

    public UserProfileBuilder addFields(UserProfileField... fields) {
        return this;
    }

    public UserProfileBuilder addFields(Collection<UserProfileField> fields) {
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
