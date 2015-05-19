package com.richrelevance.builders;

import com.richrelevance.RequestBuilder;
import com.richrelevance.internal.net.WebResponse;

public class SetUserPreferenceBuilder extends RequestBuilder<Void> {

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
        addParameter("targetType", target.getKey());
        return this;
    }

    public SetUserPreferenceBuilder setActionType(ActionType action) {
        addParameter("actionType", action.getKey());
        return this;
    }

    public SetUserPreferenceBuilder setIds(String... ids) {
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return "/user/preference";
    }

    @Override
    protected Void parseResponse(WebResponse response) {
        return null;
    }
}
