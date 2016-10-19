package com.richrelevance.richrelevance.FindDemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.richrelevance.find.search.SearchResultProduct;
import com.richrelevance.richrelevance.R;
import com.squareup.picasso.Picasso;

public class CatalogProductDetailActivity extends AppCompatActivity {

    private static final String KEY_PRODUCT = "KEY_PRODUCT";

    public static Intent createCatalogProductDetailActivityIntent(Activity activity, SearchResultProduct product) {
        Intent intent = new Intent(activity, CatalogProductDetailActivity.class);
        intent.putExtra(KEY_PRODUCT, product);
        return intent;
    }

    private SearchResultProduct product;

    private ImageView image;
    private TextView name;
    private TextView brand;
    private TextView price;

    private SearchResultProduct getProduct() {
        if(product == null) {
            product = getIntent().getParcelableExtra(KEY_PRODUCT);
        }
        return product;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_catalog_product);

        image = (ImageView) findViewById(R.id.product_image);
        name = (TextView) findViewById(R.id.product_name);
        brand = (TextView) findViewById(R.id.product_brand);
        price = (TextView) findViewById(R.id.product_price);

        loadProduct(getProduct());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    private void loadProduct(SearchResultProduct product) {
        Picasso.with(image.getContext()).load(product.getImageId()).fit().centerCrop().into(image);
        name.setText(product.getName());
        brand.setText(product.getBrand());
        price.setText(convertCents(product.getSalesPriceCents()));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(product.getName());
        }
    }

    public String convertCents(int cents) {
        return String.format(getResources().getString(R.string.format), Integer.toString(cents / 100), Integer.toString(cents % 100)).replace(" ", "0");
    }
}
