package com.richrelevance.richrelevance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.richrelevance.richrelevance.model.CardModel;
import com.richrelevance.richrelevance.model.Orientations;
import com.richrelevance.richrelevance.view.CardContainer;
import com.richrelevance.richrelevance.view.SimpleCardStackAdapter;
import com.richrelevance.Callback;
import com.richrelevance.Error;
import com.richrelevance.RichRelevance;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.recommendations.PlacementResponse;
import com.richrelevance.recommendations.PlacementResponseInfo;
import com.richrelevance.recommendations.RecommendedProduct;
import com.richrelevance.userPreference.ActionType;
import com.richrelevance.userPreference.FieldType;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    CardContainer cardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetStack(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){

            case R.id.action_refresh:
                return true;
            case R.id.action_preference:
                Intent intent = new Intent(this, PreferenceActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public Context getContext() {
        return (Context)this;
    }

    public void resetStack(View view) {
        // Create a "RecommendationsForPlacements" builder for the "add to cart" placement type.
       // Placement placement = new Placement(Placement.PlacementType.ADD_TO_CART, "prod1");
        RichRelevance.buildRecommendationsForPlacements(
                new Placement(Placement.PlacementType.ADD_TO_CART, "prod1"),
                new Placement(Placement.PlacementType.HOME, "prod2"))
                // Attach a callback
                .setCallback(new Callback<PlacementResponseInfo>() {
                    @Override
                    public void onResult(PlacementResponseInfo result) {
                        List<CardModel> cards = new ArrayList<>();

                        for (PlacementResponse placement : result.getPlacements()) {
                            for (final RecommendedProduct recommendedProduct : placement.getRecommendedProducts()) {

                                CardModel card = new CardModel(
                                        recommendedProduct.getName(),
                                        recommendedProduct.getBrand(),
                                        recommendedProduct.getImageUrl(),
                                        recommendedProduct.getPriceCents()
                                );

                                card.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
                                    @Override
                                    public void onLike() {
                                        RichRelevance.buildTrackUserPreference(
                                                FieldType.PRODUCT,
                                                ActionType.LIKE,
                                                recommendedProduct.getId()
                                        ).execute();
                                    }

                                    @Override
                                    public void onDislike() {
                                        RichRelevance.buildTrackUserPreference(
                                                FieldType.PRODUCT,
                                                ActionType.DISLIKE,
                                                recommendedProduct.getId()
                                        ).execute();
                                    }
                                });
                                cards.add(card);
                            }
                        }

                        final SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getContext(), cards);

                        cardContainer = (CardContainer) findViewById(R.id.cardLayoutView);
                        cardContainer.setOrientation(Orientations.Orientation.Disordered);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cardContainer.setAdapter(adapter);
                            }
                        });
                    }

                    @Override
                    public void onError(Error error) {
                        Log.e(getClass().getSimpleName(), "Error: " + error.getMessage());
                    }
                })
                .execute();
    }
}
