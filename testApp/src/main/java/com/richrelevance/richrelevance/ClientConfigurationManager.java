package com.richrelevance.richrelevance;


import android.content.Context;

import com.richrelevance.ClientConfiguration;
import com.richrelevance.RRLog;
import com.richrelevance.RichRelevance;

import java.util.UUID;

public class ClientConfigurationManager {

    public static ClientConfigurationManager INSTANCE = new ClientConfigurationManager();

    public static ClientConfigurationManager getInstance() {
        return INSTANCE;
    }

    public static final String API_KEY = "showcaseparent";

    public static final String DEFAULT_CLIENT_API_KEY = "bccfa17d092268c0";

    public static final String API_CLIENT_SECRET = "r5j50mlag06593401nd4kt734i";

    public static final String DEFAULT_CLIENT_NAME = "Rich Relevance";

    public static final String DEFAULT_USER_ID = "RZTestUserTest";

    private String clientApiKey;

    private String clientName = DEFAULT_CLIENT_NAME;

    private User user;

    public void setConfig(Context context, String clientApiKey, String clientName, User user) {
        this.clientApiKey = clientApiKey;
        setClientName(clientName);
        this.user = user;
        createConfiguration(context);
    }

    public void setClientApiKey(Context context, String clientApiKey) {
        this.clientApiKey = clientApiKey;
        createConfiguration(context);
    }

    public void setClientName(String clientName) {
        this.clientName = (clientName == null || clientName.isEmpty()) ? DEFAULT_CLIENT_NAME : clientName;
    }

    public void setUser(Context context, User user) {
        this.user = user;
        createConfiguration(context);
    }

    public String getClientName() {
        return clientName;
    }

    public User getSelectedUser() {
        return user;
    }

    private void createConfiguration(Context context) {
        ClientConfiguration config = new ClientConfiguration(ClientConfigurationManager.API_KEY,
                ((clientApiKey == null || clientApiKey.isEmpty()) ? DEFAULT_CLIENT_API_KEY : clientApiKey));
        config.setApiClientSecret(ClientConfigurationManager.API_CLIENT_SECRET);
        config.setUserId((user == null) ? ClientConfigurationManager.DEFAULT_USER_ID : user.getUserID());
        config.setSessionId(UUID.randomUUID().toString());

        RichRelevance.init(context.getApplicationContext(), config);

        // Enable all logging
        RichRelevance.setLoggingLevel(RRLog.VERBOSE);
    }
}
