package com.richrelevance;

import com.richrelevance.utils.ParsingUtils;

import org.json.JSONObject;

public abstract class ResponseInfo {
    private String status;
    private JSONObject rawJson;

    public boolean isStatusOk() {
        return ParsingUtils.isStatusOk(getStatus());
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONObject getRawJson() {
        return rawJson;
    }

    public void setRawJson(JSONObject rawJson) {
        this.rawJson = rawJson;
    }
}
