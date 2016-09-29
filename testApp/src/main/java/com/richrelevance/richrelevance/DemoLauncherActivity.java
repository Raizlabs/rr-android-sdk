package com.richrelevance.richrelevance;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.richrelevance.richrelevance.FindDemo.FindDemoActivity.createFindDemoActivityIntent;
import static com.richrelevance.richrelevance.PreferencesDemo.PreferencesDemoActivity.createPreferencesDemoActivityIntent;

public class DemoLauncherActivity extends Activity {

    private final String STATE_CLIENT_API_KEY = "STATE_CLIENT_API_KEY";

    private final String STATE_CLIENT_NAME = "STATE_CLIENT_NAME";

    private EditText clientAPIKeyEditText;

    private EditText clientNameEditText;

    private Button preferencesDemoButton;

    private Button findDemoButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_launcher);

        clientAPIKeyEditText = (EditText) findViewById(R.id.clientKeyEditText);
        clientNameEditText = (EditText) findViewById(R.id.clientNameEditText);
        preferencesDemoButton = (Button) findViewById(R.id.preferencesDemoButton);
        findDemoButton = (Button) findViewById(R.id.findDemoButton);


        TextWatcher clientConfigurationComplete = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                preferencesDemoButton.setVisibility(View.VISIBLE);
                findDemoButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        clientAPIKeyEditText.addTextChangedListener(clientConfigurationComplete);
        clientNameEditText.addTextChangedListener(clientConfigurationComplete);

        if (savedInstanceState != null) {
            String clientAPIKey = savedInstanceState.getString(STATE_CLIENT_API_KEY);
            String clientName = savedInstanceState.getString(STATE_CLIENT_NAME);

            if (clientAPIKey != null) {
                clientAPIKeyEditText.setText(clientAPIKey);
            }

            if (clientName != null) {
                clientNameEditText.setText(clientName);
            }
        }

        preferencesDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClientConfiguration();
                startActivity(createPreferencesDemoActivityIntent(DemoLauncherActivity.this));
            }
        });

        findDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClientConfiguration();
                startActivity(createFindDemoActivityIntent(DemoLauncherActivity.this));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putString(STATE_CLIENT_API_KEY, clientAPIKeyEditText.getText().toString());
        savedInstanceState.putString(STATE_CLIENT_NAME, clientNameEditText.getText().toString());
    }

    private void setClientConfiguration() {
        ClientConfigurationManager.getInstance().setClientAPIKey(clientAPIKeyEditText.getText().toString());
        ClientConfigurationManager.getInstance().setClientName(clientNameEditText.getText().toString());
    }
}
