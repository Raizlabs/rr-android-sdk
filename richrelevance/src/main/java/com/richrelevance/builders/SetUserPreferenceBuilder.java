package com.richrelevance.builders;

import com.richrelevance.ClientConfiguration;
import com.richrelevance.RequestBuilder;
import com.richrelevance.ResponseInfo;
import com.richrelevance.internal.net.WebResponse;

import org.json.JSONObject;

public class SetUserPreferenceBuilder extends RequestBuilder<ResponseInfo> {

    public enum TargetType {
        Product {
            @Override
            String getKey() {
                return "product";
            }
        };


        abstract String getKey();
    }

    public enum ActionType {
        Like {
            @Override
            String getKey() {
                return "like";
            }
        };

        abstract String getKey();
    }

    public SetUserPreferenceBuilder setTargetType(TargetType target) {
        setParameter("targetType", target.getKey());
        return this;
    }

    public SetUserPreferenceBuilder setActionType(ActionType action) {
        setParameter("actionType", action.getKey());
        return this;
    }

    public SetUserPreferenceBuilder setIds(String... ids) {
        return this;
    }

    @Override
    protected String getEndpointPath(ClientConfiguration configuration) {
        return "/user/preference";
    }

    @Override
    protected ResponseInfo createNewResult() {
        return null;
    }

    @Override
    protected void populateResponse(WebResponse response, JSONObject json, ResponseInfo responseInfo) {

    }
}
