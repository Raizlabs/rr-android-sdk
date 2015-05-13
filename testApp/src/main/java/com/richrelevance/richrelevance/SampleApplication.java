package com.richrelevance.richrelevance;

import android.app.Application;

import com.richrelevance.RichRelevance;

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RichRelevance.setApiKey("myApiKey");
        RichRelevance.setApiClientKey("myApiClientKey");
        RichRelevance.setUserId("myUserId");
        RichRelevance.setSessionId("mySessionId");
    }
}
