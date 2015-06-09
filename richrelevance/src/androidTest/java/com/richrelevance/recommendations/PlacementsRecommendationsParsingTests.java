package com.richrelevance.recommendations;

import com.richrelevance.BaseAndroidTestCase;
import com.richrelevance.Error;
import com.richrelevance.RequestBuilderAccessor;
import com.richrelevance.ResponseInfo;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.mocking.MockWebResponse;
import com.richrelevance.mocking.ResponseBuilder;

public class PlacementsRecommendationsParsingTests extends BaseAndroidTestCase {

    public void testParseRecsForPlacements() {
        PlacementsRecommendationsBuilder builder = new PlacementsRecommendationsBuilder();
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);

        ResponseBuilder responseBuilder = new ResponseBuilder()
                .setResponseCode(200)
                .setContentAssetPath("recsForPlacements.json");
        MockWebResponse response = new MockWebResponse(responseBuilder, getContext());

        TestResultCallback<?> callback = new TestResultCallback<PlacementResponseInfo>() {
            @Override
            protected void testResponse(PlacementResponseInfo response) {
                assertNotNull(response);
                assertEquals("ok", response.getStatus());
                assertEquals("f3a16fcb-1193-460e-4a36-255366b5cf38", response.getViewGuid());
                assertEquals(1, response.getPlacements().size());

                PlacementResponse placement = response.getPlacements().get(0);

                assertEquals("add_to_cart_page_0", placement.getHtmlElementId());
                assertEquals(Placement.PlacementType.ADD_TO_CART, placement.getPlacement().getPageType());
                assertEquals("prod1", placement.getPlacement().getName());
                assertEquals("Best Sellers", placement.getStrategyMessage());
                assertEquals(4, placement.getRecommendedProducts().size());

                ProductRecommendation product = placement.getRecommendedProducts().get(0);
                assertEquals(
                        "http://recs.richrelevance.com/rrserver/apiclick?a=showcaseparent&cak=615389034415e91d&ct=http%3A%2F%2Flabs.richrelevance.com%2Fstorre%2Fcatalog%2Fproduct%2Fview%2Fsku%2F24100292&vg=f3a16fcb-1193-460e-4a36-255366b5cf38&stid=13&pti=13&pa=4892&pos=0&p=24100292&channelId=615389034415e91d&s=13DF9FE0-20D2-4951-AF45-4DDB105E7406&u=RZTestUser",
                        product.getClickUrl());
                assertEmpty(product.getRegionPriceDescription());
                assertEquals(4.238999843597412, product.getRating());
                assertEquals(0, product.getNumReviews());
                assertEquals(2900, product.getPriceRangeCents().getMin());
                assertEquals(2900, product.getPriceRangeCents().getMax());
                assertEquals("Electronics", product.getCategoryIds().get(0));
                assertEquals("24100292", product.getRegionalProductSku());
                assertEquals(
                        "http://labs.richrelevance.com/storre/media/catalog/product/c/a/canon-pixma-mg2220-all-in-one-inkjet-multifunction-printerscannercopier-a3c09644838bab3c901601a1603534b1.jpg",
                        product.getImageUrl());
                assertEquals("Canon PIXMA MG2220 All-in-One Inkjet Multifunction Printer/Scanner/Copier", product.getName());
                assertEquals("Electronics", product.getGenre());
                assertTrue(product.isRecommendable());
                assertEquals(2900, product.getPriceCents());
                assertNotNull(product.getAttributes());
                assertNonEmpty(product.getAttributes().get("MktplcInd").get(0));
                assertEquals("24100292", product.getId());
                assertEquals("Canon", product.getBrand());
                assertEquals(4, product.getCategories().size());

                Category category = product.getCategories().get(0);
                assertEquals("Electronics", category.getName());
                assertEquals("Electronics", category.getId());
                assertFalse(category.hasChildren());
            }
        };

        accessor.parseResponse(response, callback);
        callback.assertSuccess();
    }

    private abstract static class TestResultCallback<T extends ResponseInfo>
            implements WebRequest.ResultCallback<ResponseInfo> {

        private boolean succeeded = false;

        @Override
        public void onSuccess(ResponseInfo result) {
            testResponse((T) result);
            succeeded = true;
        }

        @Override
        public void onError(Error error) {
            succeeded = false;
        }

        public void assertSuccess() {
            assertTrue("Response did not indicate success", succeeded);
        }

        protected abstract void testResponse(T response);
    }
}
