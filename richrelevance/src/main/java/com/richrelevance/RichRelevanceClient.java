package com.richrelevance;

public interface RichRelevanceClient {
    public String getApiKey();

    public void setApiKey(String apiKey);

    public String getApiClientKey();

    public void setApiClientKey(String apiClientKey);

    public String getUserId();

    public void setUserId(String userId);

    public String getSessionId();

    public void setSessionId(String sessionId);

    public <T> void executeRequest(RequestBuilder<T> builder);
}
