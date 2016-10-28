package com.richrelevance.richrelevance.FindDemo;


import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.find.autocomplete.AutoCompleteBuilder;
import com.richrelevance.find.autocomplete.AutoCompleteResponseInfo;
import com.richrelevance.find.autocomplete.AutoCompleteSuggestion;
import com.richrelevance.find.search.Facet;
import com.richrelevance.find.search.Filter;
import com.richrelevance.find.search.SearchRequestBuilder;
import com.richrelevance.find.search.SearchResponseInfo;
import com.richrelevance.find.search.SearchResultProduct;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.richrelevance.R;

import java.util.ArrayList;
import java.util.List;

import static com.richrelevance.richrelevance.FindDemo.CatalogProductDetailActivity.createCatalogProductDetailActivityIntent;
import static com.richrelevance.richrelevance.FindDemo.SearchSortFilterActivity.KEY_SELECTED_FILTER_BY;
import static com.richrelevance.richrelevance.FindDemo.SearchSortFilterActivity.KEY_SELECTED_SORTED_BY;
import static com.richrelevance.richrelevance.FindDemo.SearchSortFilterActivity.createSearchSortFilterActivityIntent;

public class SearchActivity extends FindBaseActivity {

    private static final int SELECT_SORT_FILTER_RESULT = 199;

    private FloatingSearchView searchView;

    private CatalogProductsAdapter adapter;

    private ViewFlipper viewFlipper;

    private FloatingActionButton fabSortFilter;

    private ArrayList<Facet> facets = new ArrayList<>();

    public static Intent createSearchActivityIntent(Activity activity) {
        return new Intent(activity, SearchActivity.class);
    }

    @Override
    protected void loadActivity() {
        setContentView(R.layout.activity_search);

        if (getSupportActionBar() != null) {
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        }

        searchView = (FloatingSearchView) findViewById(R.id.searchView);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final View emptyState = findViewById(R.id.emptyState);

        fabSortFilter = (FloatingActionButton) findViewById(R.id.fabSortFilter);
        fabSortFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(createSearchSortFilterActivityIntent(SearchActivity.this, facets), SELECT_SORT_FILTER_RESULT);
            }
        });

        setUpSearchView();

        adapter = new CatalogProductsAdapter() {
            private boolean hasProducts = false;

            @Override
            public void onProductClicked(SearchResultProduct product) {
                startActivity(createCatalogProductDetailActivityIntent(SearchActivity.this, product));
            }

            @Override
            protected void onNotifiedDataSetChanged(boolean hasProducts) {
                if (this.hasProducts != hasProducts) {
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
                executeAutoComplete(newQuery);
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

    private void executeAutoComplete(String query) {
        AutoCompleteBuilder builder = RichRelevance.buildAutoCompleteRequest(query, 20);
        builder.setCallback(new Callback<AutoCompleteResponseInfo>() {
            @Override
            public void onResult(final AutoCompleteResponseInfo result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(result != null) {
                            List<AutoCompleteSearchSuggestion> searchSuggestions = new ArrayList<>();
                            for (AutoCompleteSuggestion suggestion : result.getSuggestions()) {
                                searchSuggestions.add(new AutoCompleteSearchSuggestion(suggestion));
                            }
                            searchView.swapSuggestions(searchSuggestions);
                        } else {
                            searchView.clearSuggestions();
                        }
                    }
                });
            }

            @Override
            public void onError(Error error) {
                Log.e(SearchActivity.class.getSimpleName(), "Error response from autocomplete request.");
            }
        });
        builder.execute();
    }

    private void executeSearch(String query) {
        executeSearch(query, null, null, null);
    }

    private void executeSearch(String query, SearchResultProduct.Field sortBy, SearchRequestBuilder.SortOrder sortOrder, final Filter filter) {
        SearchRequestBuilder builder = RichRelevance.buildSearchRequest(query, new Placement(Placement.PlacementType.SEARCH, "find"));
        if (sortBy != null && sortOrder != null) {
            builder.setSort(sortBy, sortOrder);
        }
        if (filter != null) {
            builder.setFilters(filter);
        }
        builder.setCallback(new Callback<SearchResponseInfo>() {
                                @Override
                                public void onResult(final SearchResponseInfo result) {

                                    runOnUiThread(new Runnable() {
                                                      @Override
                                                      public void run() {
                                                          if (result == null) {
                                                              adapter.setProducts(new ArrayList<SearchResultProduct>());
                                                              facets = new ArrayList<>();
                                                              fabSortFilter.setVisibility(View.GONE);
                                                          } else {

                                                              adapter.setProducts(result.getProducts());
                                                              facets = new ArrayList<>(result.getFacets());

                                                              // show fab only if results are present
                                                              if (!result.getProducts().isEmpty()) {
                                                                  fabSortFilter.setVisibility(View.VISIBLE);
                                                              } else {
                                                                  fabSortFilter.setVisibility(View.GONE);
                                                              }
                                                          }
                                                      }
                                                  }

                                    );

                                }

                                @Override
                                public void onError(Error error) {
                                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }

        );
        builder.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_SORT_FILTER_RESULT) {

            if (resultCode == Activity.RESULT_OK) {
                SearchResultProduct.Field sort = (SearchResultProduct.Field) data.getSerializableExtra(KEY_SELECTED_SORTED_BY);
                Filter filter = data.getParcelableExtra(KEY_SELECTED_FILTER_BY);
                if (searchView.getQuery() != null && !searchView.getQuery().isEmpty()) {
                    executeSearch(searchView.getQuery(), sort, SearchRequestBuilder.SortOrder.ASCENDING, filter);
                }
            }
        }
    }
}
