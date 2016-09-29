package com.richrelevance.richrelevance.FindDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.richrelevance.richrelevance.ClientConfigurationManager;
import com.richrelevance.richrelevance.R;

public class FindDemoActivity extends Activity {

    private TextView clientKey;

    private TextView clientName;

    public static Intent createFindDemoActivityIntent(Activity activity) {
        Intent intent = new Intent(activity, FindDemoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find);

        clientKey = (TextView) findViewById(R.id.clientKey);
        clientKey.setText(ClientConfigurationManager.getInstance().getClientAPIKey());
        clientName = (TextView) findViewById(R.id.clientName);
        clientName.setText(ClientConfigurationManager.getInstance().getClientName());
    }
}
