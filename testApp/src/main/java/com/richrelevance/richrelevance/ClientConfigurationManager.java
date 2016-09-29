package com.richrelevance.richrelevance;

public class ClientConfigurationManager {

    private static final ClientConfigurationManager INSTANCE = new ClientConfigurationManager();

    private String clientAPIKey;

    private String clientName;

    public static ClientConfigurationManager getInstance() {
        return INSTANCE;
    }

    public String getClientAPIKey() {
        return clientAPIKey;
    }

    public void setClientAPIKey(String clientAPIKey) {
        this.clientAPIKey = clientAPIKey;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
