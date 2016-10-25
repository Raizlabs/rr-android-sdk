package com.richrelevance.richrelevance.FindDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.richrelevance.richrelevance.R;

import java.util.ArrayList;
import java.util.List;

public class SearchSortFilterActivity extends AppCompatActivity {

    private static final String KEY_SORTED_BY_LIST = "KEY_SORTED_BY_LIST";
    private static final String KEY_FILTER_BY_LIST = "KEY_FILTER_BY_LIST";

    private ArrayAdapter<String> sortedAdapter;
    private ArrayAdapter<String> filterAdapter;

    public static Intent createSearchSortFilterActivityIntent(Activity activity, ArrayList<String> sortedBys, ArrayList<String> filterBys) {
        Intent intent = new Intent(activity, SearchSortFilterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(KEY_SORTED_BY_LIST, sortedBys);
        bundle.putStringArrayList(KEY_FILTER_BY_LIST, filterBys);
        intent.putExtras(bundle);
        return intent;
    }

    private List<String> getSortedBys() {
        return getIntent().getStringArrayListExtra(KEY_SORTED_BY_LIST);
    }

    private List<String> getFilterBys() {
        return getIntent().getStringArrayListExtra(KEY_FILTER_BY_LIST);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_filter);

        ListView sortListView = (ListView) findViewById(R.id.sortListView);
        ListView filterListView = (ListView) findViewById(R.id.filterListView);
        sortedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getSortedBys());
        filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getFilterBys());
        sortListView.setAdapter(sortedAdapter);
        filterListView.setAdapter(filterAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        menu.findItem(R.id.addUser).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.done:
                sendFilterSortSettingsResult(null, null); //TODO get the selections
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendFilterSortSettingsResult(List<String> selectedSortedBys, List<String> selectedFilterBys) {

    }
}
