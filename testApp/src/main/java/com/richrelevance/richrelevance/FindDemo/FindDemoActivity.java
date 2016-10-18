package com.richrelevance.richrelevance.FindDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.richrelevance.richrelevance.ClientConfigurationManager;
import com.richrelevance.richrelevance.R;

import static com.richrelevance.richrelevance.FindDemo.CatalogProductDetailActivity.createCatalogProductDetailActivityIntent;
import static com.richrelevance.richrelevance.FindDemo.SearchActivity.createSearchActivityIntent;

public class FindDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static Intent createFindDemoActivityIntent(Activity activity) {
        Intent intent = new Intent(activity, FindDemoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find);

        final CatalogProductsAdapter adapter = new CatalogProductsAdapter() {
            @Override
            public void onProductClicked(SearchResultProduct product) {
                startActivity(createCatalogProductDetailActivityIntent(FindDemoActivity.this, product));
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(ClientConfigurationManager.getInstance().getClientName());
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
                startActivity(createSearchActivityIntent(FindDemoActivity.this));
            }
        });

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
                    public void onError(Error error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).execute();
    }
}
