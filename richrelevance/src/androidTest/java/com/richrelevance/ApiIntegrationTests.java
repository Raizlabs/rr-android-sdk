package com.richrelevance;

import android.util.Log;

import com.richrelevance.internal.net.WebResponse;
import com.richrelevance.recommendations.Placement;
import com.richrelevance.recommendations.PlacementResponse;
import com.richrelevance.recommendations.PlacementResponseInfo;
import com.richrelevance.recommendations.PlacementsRecommendationsBuilder;
import com.richrelevance.recommendations.ProductRecommendation;
import com.richrelevance.recommendations.StrategyRecommendationsBuilder;
import com.richrelevance.recommendations.StrategyResponseInfo;
import com.richrelevance.utils.ParsingUtils;

import org.json.JSONObject;

import java.util.UUID;

public class ApiIntegrationTests extends BaseTestCase {

    private RichRelevanceClient client;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        ClientConfiguration config = new ClientConfiguration(Constants.TestApiKeys.API_KEY, Constants.TestApiKeys.API_CLIENT_KEY);
        config.setEndpoint(Endpoints.PRODUDCTION, false);
        config.setUserId("AndroidTestUser");
        config.setSessionId(UUID.randomUUID().toString());

        client = new RichRelevanceClientImpl();
        client.setConfiguration(config);
    }

    public void testBasic() {
        RequestBuilder<ResponseInfo> builder = new RequestBuilder<ResponseInfo>() {
            @Override
            protected ResponseInfo createNewResult() {
                return new ResponseInfo() {
                };
            }

            @Override
            protected String getEndpointPath() {
                return "rrPlatform/recsForPlacements";
            }

            @Override
            protected void populateResponse(WebResponse response, JSONObject json, ResponseInfo responseInfo) {
            }
        };

        BuilderExecutorHelper<ResponseInfo> helper = new BuilderExecutorHelper<>(client, builder);
        helper.execute();
        helper.waitUntilCompleted();
        assertNotNull(helper.getResult());
    }

    public void testRecommendationsForStrategy() {
        StrategyRecommendationsBuilder builder = RichRelevance.buildRecommendationsUsingStrategy(StrategyType.SITE_WIDE_BEST_SELLERS);
        BuilderExecutorHelper<StrategyResponseInfo> helper = new BuilderExecutorHelper<>(client, builder);
        helper.execute();
        helper.waitUntilCompleted();
        assertNotNull(helper.getResult());
    }

    public void testPersonalizedRecommendationsForPlacements() {
        Placement placement = new Placement(Placement.PlacementType.ADD_TO_CART, "prod1");
        PlacementsRecommendationsBuilder builder = RichRelevance.buildRecommendationsForPlacements(placement);
        BuilderExecutorHelper<PlacementResponseInfo> helper = new BuilderExecutorHelper<>(client, builder);
        helper.execute();
        helper.waitUntilCompleted();
        validateRecommendationsForPlacementsResponse(helper.getResult());
    }

    public void testRecommendationsForPlacementsWithSearchTerm() {
        Placement placement = new Placement(Placement.PlacementType.ADD_TO_CART, "prod1");
        PlacementsRecommendationsBuilder builder = RichRelevance.buildRecommendationsForPlacements(placement)
                .setSearchTerm("SearchTerm");
        BuilderExecutorHelper<PlacementResponseInfo> helper = new BuilderExecutorHelper<>(client, builder);
        helper.execute();
        helper.waitUntilCompleted();
        validateRecommendationsForPlacementsResponse(helper.getResult());
    }

    public void validateRecommendationsForPlacementsResponse(PlacementResponseInfo responseInfo) {
        assertNotNull(responseInfo);

        assertTrue(ParsingUtils.isStatusOk(responseInfo.getStatus()));
        assertNonEmpty(responseInfo.getViewGuid());
        assertTrue(responseInfo.getPlacements().size() > 0);

        PlacementResponse placement = responseInfo.getPlacements().get(0);
        assertTrue(placement.getRecommendedProducts().size() > 0);

        ProductRecommendation product = placement.getRecommendedProducts().get(0);
        assertTrue(product.getCategories().size() > 0);
        assertNonEmpty(product.getName());
        assertNonEmpty(product.getGenre());
        assertNonEmpty(product.getId());
        assertNonEmpty(product.getClickUrl());
        assertNonEmpty(product.getImageUrl());
    }

    private static class BuilderExecutorHelper<T extends ResponseInfo> {

        private RichRelevanceClient client;
        private RequestBuilder<T> builder;

        private OneShotLock completionLock;
        private Wrapper<T> resultWrapper;

        public BuilderExecutorHelper(RichRelevanceClient client, RequestBuilder<T> builder) {
            this.client = client;
            this.builder = builder;

            this.completionLock = new OneShotLock();
            this.resultWrapper = new Wrapper<>();
        }

        public void execute() {
            builder.setCallback(new Callback<T>() {
                @Override
                public void onResult(T result) {
                    resultWrapper.set(result);
                    completionLock.unlock();
                }

                @Override
                public void onError(Error error) {
                    Log.w(getClass().getSimpleName(), "Builder web request responded with an error");
                    resultWrapper.set(null);
                    completionLock.unlock();
                }
            });

            client.executeRequest(builder);
        }

        public void waitUntilCompleted() {
            completionLock.waitUntilUnlocked();
        }

        public T getResult() {
            return resultWrapper.get();
        }
    }
}
