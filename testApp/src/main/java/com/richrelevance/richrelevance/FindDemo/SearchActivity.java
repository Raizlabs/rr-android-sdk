package com.richrelevance.richrelevance.FindDemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.find.search.SearchResponseInfo;
import com.richrelevance.find.search.SearchResultProduct;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.richrelevance.R;

import static com.richrelevance.richrelevance.FindDemo.CatalogProductDetailActivity.createCatalogProductDetailActivityIntent;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private CatalogProductsAdapter adapter;

    private RecyclerView recyclerView;

    public static Intent createSearchActivityIntent(Activity activity) {
        return new Intent(activity, SearchActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adapter = new CatalogProductsAdapter() {
            @Override
            public void onProductClicked(SearchResultProduct product) {
                startActivity(createCatalogProductDetailActivityIntent(SearchActivity.this, product));
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.expandActionView(searchItem);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        executeSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //TODO implement 3 second wait and auto-query
        //TODO implement autocomplete suggestions

        return false;
    }

    private void executeSearch(String query) {
        RichRelevance.buildSearchRequest(query, new Placement(Placement.PlacementType.SEARCH, "find"))
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
