package com.richrelevance;

import android.text.TextUtils;

import com.richrelevance.utils.ParsingUtils;

import org.json.JSONObject;

public abstract class ResponseInfo {
    private String status;
    private String errorMessage;
    private JSONObject rawJson;

    public boolean isStatusOk() {
        return ParsingUtils.isStatusOk(getStatus());
    }

    public String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    public boolean hasErrorMessage() {
        return !TextUtils.isEmpty(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public JSONObject getRawJson() {
        return rawJson;
    }

    public void setRawJson(JSONObject rawJson) {
        this.rawJson = rawJson;
    }
}
