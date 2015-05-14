package com.richrelevance.richrelevance;

import android.app.Application;

import com.richrelevance.RichRelevance;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RichRelevance.setApiKey("showcaseparent");
        RichRelevance.setApiClientKey("615389034415e91d");
        RichRelevance.setUserId("androidTest");
        RichRelevance.setSessionId("093820948123");
    }
}
