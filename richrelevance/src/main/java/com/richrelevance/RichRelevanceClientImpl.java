package com.richrelevance;

import com.richrelevance.internal.net.WebRequestManager;
import com.richrelevance.internal.net.WebResultInfo;

public class RichRelevanceClientImpl implements RichRelevanceClient {

    private String apiKey;
    private String apiClientKey;
    private String userId;
    private String sessionId;

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getApiClientKey() {
        return apiClientKey;
    }

    @Override
    public void setApiClientKey(String apiClientKey) {
        this.apiClientKey = apiClientKey;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public <T> void executeRequest(RequestBuilder<T> builder) {
        builder.setClient(this);

        CallbackWebListener<T> listener = new CallbackWebListener<>(builder.getCallback());

        RichRelevance.getWebRequestManager().executeInBackground(builder.getWebRequest(), listener);
    }

    // region Inner Classes

    private static class CallbackWebListener<T> implements WebRequestManager.WebRequestListener<T> {

        private Callback<T> callback;

        public CallbackWebListener(Callback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onRequestComplete(WebResultInfo<T> resultInfo) {
            // TODO - Error states and status verification
            if (callback != null) {
                callback.onResult(resultInfo.getResult());
            }
        }
    }

    // endregion Inner Classes
}
