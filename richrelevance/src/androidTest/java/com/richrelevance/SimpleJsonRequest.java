package com.richrelevance;

import android.support.annotation.NonNull;

import com.richrelevance.internal.net.HttpMethod;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

public class SimpleJsonRequest implements WebRequest<JSONObject> {

    private String url;

    public SimpleJsonRequest(String url) {
        this.url = url;
    }

    @Override
    public WebRequestBuilder getRequestBuilder() {
        return new WebRequestBuilder(HttpMethod.Get, url);
    }

    @Override
    public void translate(WebResponse response, @NonNull ResultCallback<JSONObject> resultCallback) {
        resultCallback.onSuccess(response.getContentAsJSON());
    }
}
