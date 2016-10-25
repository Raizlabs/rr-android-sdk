package com.richrelevance.richrelevance.FindDemo;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.find.search.SearchResponseInfo;
import com.richrelevance.find.search.SearchResultProduct;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.richrelevance.R;

import static com.richrelevance.richrelevance.FindDemo.CatalogProductDetailActivity.createCatalogProductDetailActivityIntent;

public class SearchActivity extends FindBaseActivity {

    private FloatingSearchView searchView;

    private CatalogProductsAdapter adapter;

    private ViewFlipper viewFlipper;

    private FloatingActionButton fabSortFilter;

    public static Intent createSearchActivityIntent(Activity activity) {
        return new Intent(activity, SearchActivity.class);
    }

    @Override
    protected void loadActivity() {
        setContentView(R.layout.activity_search);

        if(getSupportActionBar() != null) {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        }

        searchView = (FloatingSearchView) findViewById(R.id.searchView);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final View emptyState = findViewById(R.id.emptyState);

        fabSortFilter = (FloatingActionButton) findViewById(R.id.fabSortFilter);

        setUpSearchView();

        adapter = new CatalogProductsAdapter() {
            private boolean hasProducts = false;

            @Override
            public void onProductClicked(SearchResultProduct product) {
                startActivity(createCatalogProductDetailActivityIntent(SearchActivity.this, product));
            }

            @Override
            protected void onNotifiedDataSetChanged(boolean hasProducts) {
                if(this.hasProducts != hasProducts) {
                    viewFlipper.setDisplayedChild(viewFlipper.indexOfChild(hasProducts ? recyclerView : emptyState));
                    this.hasProducts = hasProducts;
                }
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private void setUpSearchView() {
        searchView.setCloseSearchOnKeyboardDismiss(true);

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                executeSearch(newQuery);
                //get suggestions based on newQuery

                //pass them on to the search view
                //searchView.swapSuggestions(newSuggestions);
            }
        });

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

            }
        });

        searchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {
                onBackPressed();
            }
        });
    }

    private void executeSearch(String query) {
        RichRelevance.buildSearchRequest(query, new Placement(Placement.PlacementType.SEARCH, "find"))
                .setCallback(new Callback<SearchResponseInfo>() {
                    @Override
                    public void onResult(final SearchResponseInfo result) {
                        if(result != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.setProducts(result.getProducts());
                                    // show fab only if results are present
                                    if (!result.getProducts().isEmpty()) {
                                        fabSortFilter.setVisibility(View.VISIBLE);
                                    } else fabSortFilter.setVisibility(View.GONE);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }).execute();
    }

    public void sortFilter(View view) {

        // Todo: launch the sort/filter activities
    }
}
