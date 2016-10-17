package com.richrelevance.richrelevance.FindDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.find.search.SearchResponseInfo;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.richrelevance.ClientConfigurationManager;
import com.richrelevance.richrelevance.R;

public class FindDemoActivity extends Activity {

    private TextView clientKey;

    private TextView clientName;

    private TextView searchResults;

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

        searchResults = (TextView) findViewById(R.id.search_results);
        RichRelevance.buildSearchRequest("sh", new Placement(Placement.PlacementType.SEARCH, "find"))
                .setCallback(new Callback<SearchResponseInfo>() {
                    @Override
                    public void onResult(SearchResponseInfo result) {
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).execute();
    }
}
