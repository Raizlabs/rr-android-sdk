package com.richrelevance.richrelevance;

import android.app.Application;

import com.richrelevance.ClientConfiguration;
import com.richrelevance.RichRelevance;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ClientConfiguration configuration = new ClientConfiguration("showcaseparent", "615389034415e91d");
        configuration.setUserId("androidTest");
        configuration.setSessionId("093820948123");

        RichRelevance.getDefaultClient().setConfiguration(configuration);
    }
}
