package com.richrelevance.richrelevance.FindDemo;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.find.search.SearchResponseInfo;
import com.richrelevance.find.search.SearchResultProduct;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.richrelevance.R;

import static com.richrelevance.richrelevance.FindDemo.CatalogProductDetailActivity.createCatalogProductDetailActivityIntent;
import static com.richrelevance.richrelevance.FindDemo.SearchActivity.createSearchActivityIntent;

public class FindMainActivity extends FindBaseActivity {

    private static final String KEY_CLIENT_NAME = "KEY_CLIENT_NAME";

    private RecyclerView recyclerView;

    public static Intent createFindDemoActivityIntent(Activity activity, String clientName) {
        Intent intent = new Intent(activity, FindMainActivity.class);
        intent.putExtra(KEY_CLIENT_NAME, clientName);
        return intent;
    }

    public String getClientName() {
        return getIntent().getStringExtra(KEY_CLIENT_NAME);
    }


    @Override
    protected void loadActivity() {
        setContentView(R.layout.activity_find);

        final CatalogProductsAdapter adapter = new CatalogProductsAdapter() {
            @Override
            public void onProductClicked(SearchResultProduct product) {
                startActivity(createCatalogProductDetailActivityIntent(FindMainActivity.this, product));
            }

            @Override
            protected void onNotifiedDataSetChanged(boolean hasProducts) {
                // Do nothing
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getClientName());
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(createSearchActivityIntent(FindMainActivity.this));
            }
        });

        //This is an arbitrary search result to put items on the landing screen of the find demo
        RichRelevance.buildSearchRequest("sh", new Placement(Placement.PlacementType.SEARCH, "find"))
                .setCallback(new Callback<SearchResponseInfo>() {
                    @Override
                    public void onResult(final SearchResponseInfo result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setProducts(result.getProducts());
                            }
                        });
                    }

                    @Override
                    public void onError(final Error error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });                    }
                }).execute();
    }
}
