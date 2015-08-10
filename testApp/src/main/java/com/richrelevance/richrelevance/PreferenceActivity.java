package com.richrelevance.richrelevance;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.userPreference.ActionType;
import com.richrelevance.userPreference.FieldType;
import com.richrelevance.userPreference.UserPreferenceResponseInfo;

import java.util.ArrayList;
import java.util.List;

public class PreferenceActivity extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener, PreferenceListFragment.LoadingListener {

    List<String> likedProducts, dislikedProducts;

    PreferenceAdapter pagerAdapter;

    ViewPager viewPager;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO);
        //actionBar.setLogo(R.drawable.rr_logo);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        pagerAdapter = new PreferenceAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for(int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(pagerAdapter.getPageTitle(i)).setTabListener(this));
        }

        getPreferences(FieldType.PRODUCT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.action_clear:
                clearProducts();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void startLoading() {
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void stopLoading() {
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void getPreferences(FieldType fieldType) {
        startLoading();
        RichRelevance.buildGetUserPreferences(fieldType).setCallback(new Callback<UserPreferenceResponseInfo>() {
            @Override
            public void onResult(final UserPreferenceResponseInfo result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(PreferenceActivity.this != null && result != null) {
                            PreferenceAdapter pagerAdapter = PreferenceActivity.this.pagerAdapter;
                            likedProducts = result.getProducts().getLikes();
                            dislikedProducts = result.getProducts().getDislikes();
                            ((PreferenceListFragment) pagerAdapter.instantiateItem(viewPager, 0)).loadProducts(likedProducts);
                            ((PreferenceListFragment) pagerAdapter.instantiateItem(viewPager, 1)).loadProducts(dislikedProducts);
                            stopLoading();
                        }
                    }
                });
            }

            @Override
            public void onError(final Error error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(PreferenceActivity.this != null) {
                            stopLoading();
                            Toast.makeText(PreferenceActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).execute();
    }

    private void clearProducts() {
        List<String> allProducts = new ArrayList<>();
        if(likedProducts != null) { allProducts.addAll(likedProducts); }
        if(dislikedProducts != null) { allProducts.addAll(dislikedProducts); }
        RichRelevance.buildTrackUserPreference(FieldType.PRODUCT, ActionType.NEUTRAL, allProducts.toArray(new String[allProducts.size()])).setCallback(new Callback<UserPreferenceResponseInfo>() {
            @Override
            public void onResult(UserPreferenceResponseInfo result) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(PreferenceActivity.this != null) {
                            getPreferences(FieldType.PRODUCT);
                        }
                    }
                });
            }

            @Override
            public void onError(final Error error) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if(PreferenceActivity.this != null) {
                            stopLoading();
                            Toast.makeText(PreferenceActivity.this.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).execute();
    }

    public class PreferenceAdapter extends FragmentStatePagerAdapter {
        private final String LIKE = "Likes";

        private final String DISLIKE = "Dislikes";

        public PreferenceAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new PreferenceListFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                default:
                case 0:
                    return LIKE;
                case 1:
                    return DISLIKE;
            }
        }
    }
}
