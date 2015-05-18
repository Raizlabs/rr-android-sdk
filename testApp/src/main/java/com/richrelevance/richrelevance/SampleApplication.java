package com.richrelevance.richrelevance;

import android.app.Application;

import com.richrelevance.RichRelevance;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RichRelevance.getDefaultClient().setApiKey("showcaseparent");
        RichRelevance.getDefaultClient().setApiClientKey("615389034415e91d");
        RichRelevance.getDefaultClient().setUserId("androidTest");
        RichRelevance.getDefaultClient().setSessionId("093820948123");
    }
}
