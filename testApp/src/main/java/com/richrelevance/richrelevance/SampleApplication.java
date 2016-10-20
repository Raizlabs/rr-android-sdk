package com.richrelevance.richrelevance;

import android.app.Application;

import com.richrelevance.ClientConfiguration;
import com.richrelevance.RRLog;
import com.richrelevance.RichRelevance;

import java.util.UUID;

public class SampleApplication extends Application {

    public static final String API_KEY = "showcaseparent";

    public static final String DEFAULT_CLIENT_API_KEY = "bccfa17d092268c0";

    public static final String API_CLIENT_SECRET = "r5j50mlag06593401nd4kt734i";

    public static final String DEFAULT_USER_ID = "RZTestUserTest";

    @Override
    public void onCreate() {
        super.onCreate();

        // First create a configuration and use it to configure the default client.
        ClientConfiguration config = new ClientConfiguration(API_KEY, DEFAULT_CLIENT_API_KEY);
        config.setApiClientSecret(API_CLIENT_SECRET);
        config.setUserId(DEFAULT_USER_ID);
        config.setSessionId(UUID.randomUUID().toString());

        RichRelevance.init(this, config);

        // Enable all logging
        RichRelevance.setLoggingLevel(RRLog.VERBOSE);
    }
}
