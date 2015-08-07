package com.richrelevance.richrelevance;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.Range;
import com.richrelevance.RichRelevance;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.recommendations.PlacementResponse;
import com.richrelevance.recommendations.PlacementResponseInfo;
import com.richrelevance.recommendations.Product;
import com.richrelevance.recommendations.RecommendedProduct;
import com.richrelevance.recommendations.StrategyType;
import com.richrelevance.utils.ValueMap;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RichRelevance.buildRecommendationsForPlacements(
                new Placement(Placement.PlacementType.ADD_TO_CART, "thing"),
                new Placement(Placement.PlacementType.CATEGORY, "other thing"))
                .addPlacements(new Placement(Placement.PlacementType.ITEM, "another one"))
                .setCount(50)
                .addPurchasedProducts(
                        new Product("product1", 2093, 902),
                        new Product("product2", 3920, 298)
                )
                .setUserAttributes(
                        new ValueMap<String>()
                                .add("attr", "val", "val2")
                                .add("otherAtt", "val", "val2")
                                .add("otherAttr", "val", "val2")
                )
                .setPriceRanges(
                        new Range(100, 10000),
                        new Range(Range.NONE, 200000)
                )
                .execute();

        RichRelevance.buildRecommendationsUsingStrategy(StrategyType.SITE_WIDE_BEST_SELLERS)
                .execute();


        // Create a "RecommendationsForPlacements" builder for the "add to cart" placement type.
        Placement placement = new Placement(Placement.PlacementType.ADD_TO_CART, "prod1");
        RichRelevance.buildRecommendationsForPlacements(placement)
                // Attach a callback
                .setCallback(new Callback<PlacementResponseInfo>() {
                    @Override
                    public void onResult(PlacementResponseInfo result) {
                        PlacementResponse placement = result.getPlacements().get(0);
                        RecommendedProduct product = placement.getRecommendedProducts().get(0);

                        product.trackClick();
                    }

                    @Override
                    public void onError(Error error) {
                        Log.e(getClass().getSimpleName(), "Error: " + error.getMessage());
                    }
                })
                // Execute the request
                .execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
