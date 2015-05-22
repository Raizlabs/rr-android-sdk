package com.richrelevance.builders;

import com.richrelevance.Placement;
import com.richrelevance.RequestBuilder;
import com.richrelevance.StrategyType;
import com.richrelevance.StringUtils;
import com.richrelevance.UserAttribute;
import com.richrelevance.internal.net.WebResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlacementsRecommendationsBuilder extends RequestBuilder<List<Object>> {

    private static class Keys {
        private static final String PLACEMENTS = "placements";

        private static final String TIMESTAMP = "ts";

        private static final String BRAND_FILTER = "filbr";
        private static final String BRAND_INCLUDE_FILTERED = "includeBrandFilteredProducts";
        private static final String PAGE_FEATURED_BRAND = "fpb";

        private static final String PRICE_FILTER_MIN = "minPriceFilter";
        private static final String PRICE_FILTER_MAX = "maxPriceFilter";
        private static final String PRICE_INCLUDE_FILTERED = "includePriceFilteredProducts";

        private static final String EXCLUDE_HTML = "excludeHtml";
        private static final String EXCLUDE_ITEM_ATTRIBUTES = "excludeItemAttributes";
        private static final String EXCLUDE_RECOMMENDED_ITEMS = "excludeRecItems";
        private static final String MINIMAL_RECOMMENDED_ITEM_DATA = "returnMinimalRecItemData";
        private static final String INCLUDE_CATEGORY_DATA = "categoryData";
        private static final String EXCLUDE_PRODUCT_IDS = "bi";

        private static final String USER_ATTRIBUTES = "userAttribute";
        private static final String REFERRER = "pref";

        private static final String CLICK_THROUGH_SERVER = "cts";

        private static final String PRODUCT_ID = "productId";
        private static final String CATEGORY_ID = "categoryId";
        private static final String CATEGORY_HINT_IDS = "chi";
        private static final String SEARCH_TERM = "searchTerm";
        private static final String ORDER_ID = "o";
        private static final String REGISTRY_ID = "rg";
        private static final String REGISTRY_TYPE_ID = "rgt";
        private static final String ALREADY_ADDED_REGISTRY_ITEMS = "aari";
        private static final String STRATEGY_SET = "strategySet";
    }

    /**
     * Adds to the list of placements. Each identifier consists of a page type (see valid page types below) and a
     * placement name.
     * <ul>
     * <li>You will get one set of recommendations for each placement.</li>
     * <li>All placements must be for the same page type. </li>
     * <li>The first placement is assumed to be the “best” placement and will receive the best recommendation strategy. </li>
     * <li>When multiple placements are requested in the same call, each will receive a unique strategy and unique products.</li>
     * </ul>
     * @param placements The placements to add.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder addPlacements(Placement... placements) {
        addListParameters(Keys.PLACEMENTS, getPlacementStrings(Arrays.asList(placements)));
        return this;
    }

    /**
     * Sets the list of placements. Each identifier consists of a page type (see valid page types below) and a
     * placement name.
     * <ul>
     * <li>You will get one set of recommendations for each placement.</li>
     * <li>All placements must be for the same page type. </li>
     * <li>The first placement is assumed to be the “best” placement and will receive the best recommendation strategy. </li>
     * <li>When multiple placements are requested in the same call, each will receive a unique strategy and unique products.</li>
     * </ul>
     * @param placements The placements to use.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setPlacements(Placement... placements) {
        setListParameter(Keys.PLACEMENTS, getPlacementStrings(Arrays.asList(placements)));
        return this;
    }

    /**
     * Sets the list of placements. Each identifier consists of a page type (see valid page types below) and a
     * placement name.
     * <ul>
     * <li>You will get one set of recommendations for each placement.</li>
     * <li>All placements must be for the same page type. </li>
     * <li>The first placement is assumed to be the “best” placement and will receive the best recommendation strategy. </li>
     * <li>When multiple placements are requested in the same call, each will receive a unique strategy and unique products.</li>
     * </ul>
     * @param placements The placements to use.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setPlacements(Collection<Placement> placements) {
        setListParameter(Keys.PLACEMENTS, getPlacementStrings(placements));
        return this;
    }

    private Collection<String> getPlacementStrings(Collection<Placement> placements) {
        List<String> stringPlacements = new ArrayList<>(placements.size());

        for (Placement placement : placements) {
            stringPlacements.add(placement.getApiValue());
        }

        return stringPlacements;
    }

    /**
     * For cache busting. Highly recommended. If excluded, you may see cached responses.
     * @param timestamp The timestamp to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setTimestamp(long timestamp) {
        setParameter(Keys.TIMESTAMP, timestamp);
        return this;
    }

    /**
     * Filter by brand name. By default, excludes products of the specified brand from being recommended. See
     * {@link #setIncludeFilteredBrands(boolean)} to change default function.
     * @see #setIncludeFilteredBrands(boolean)
     * @param brands The brands to filter.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setFilterBrandNames(String... brands) {
        setListParameter(Keys.BRAND_FILTER, brands);
        return this;
    }

    /**
     * Filter by brand name. By default, excludes products of the specified brand from being recommended. See
     * {@link #setIncludeFilteredBrands(boolean)} to change default function.
     * @see #setIncludeFilteredBrands(boolean)
     * @param brands The brands to filter.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setFilterBrandNames(Collection<String> brands) {
        setListParameter(Keys.BRAND_FILTER, brands);
        return this;
    }

    /**
     * Changes the function of {@link #setFilterBrandNames(String...)} from exclude to include. If not specified, the
     * default is ‘false’–which will exclude the specified brand. If set to ‘true’, the brand filter will exclude all
     * products except the specified brand.
     * @param include Whether to include the specified brands.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setIncludeFilteredBrands(boolean include) {
        setParameter(Keys.BRAND_INCLUDE_FILTERED, include);
        return this;
    }

    /**
     * The brand featured on the page. Used to set the seed for brand-seeded strategies like Brand Top Sellers.
     * @param brand The brand featured on the page.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setPageFeaturedBrand(String brand) {
        setParameter(Keys.PAGE_FEATURED_BRAND, brand);
        return this;
    }

    /**
     * The minimum price of a price range.  The filter will match the sale price or the list price of a product if no
     * sale price is provided. The price is given in cents meaning if you only want to exclude products that are
     * greater than $5.79, the value provided should be ‘579’.
     * @param minimumPrice The minimum price in cents.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setMinimumPriceFilter(int minimumPrice) {
        setParameter(Keys.PRICE_FILTER_MIN, minimumPrice);
        return this;
    }

    /**
     * The maximum price of a price range. The filter will match the sale price or the list price of a product if no
     * sale price is provided. The price is given in cents, meaning if you only want to exclude products that are less
     * than $100.99, the value provided should be ‘10099’.
     * @param maximumPrice The maximum price in cents.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setMaximumPriceFilter(int maximumPrice) {
        setParameter(Keys.PRICE_FILTER_MAX, maximumPrice);
        return this;
    }

    /**
     * Changes the function of minPriceFilter and maxPriceFilter from exclude to include. If not specified, the default
     * is ‘false’ which will exclude products prices that match the specified range. If set to ‘true’, the filter will
     * exclude all products outside of the range.
     * @param include Whether to treat the min and max price filters as includes.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setIncludeFilteredPrices(boolean include) {
        setParameter(Keys.PRICE_INCLUDE_FILTERED, include);
        return this;
    }

    /**
     * If set to true, omits the HTML returned in the Relevance Cloud server response. If false, the response includes
     * the HTML for the placement, which is set in the layout, in the html field. Default = false.
     * @param exclude True to omit the returned HTML.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder excludeHtml(boolean exclude) {
        setParameter(Keys.EXCLUDE_HTML, exclude);
        return this;
    }

    /**
     * If set to true, removes the item attributes from the recommended products data. Default = false.
     * @param exclude True to remove the item attributes.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder excludeItemAttributes(boolean exclude) {
        setParameter(Keys.EXCLUDE_ITEM_ATTRIBUTES, exclude);
        return this;
    }

    /**
     * If set to true, removes the recommended items structure completely. This is useful when having HTML is enough
     * in the response. Default = false.
     * @param exclude True to remove the recommended items.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder excludeRecommendedItems(boolean exclude) {
        setParameter(Keys.EXCLUDE_RECOMMENDED_ITEMS, exclude);
        return this;
    }

    /**
     * If set to true, reduces the information about the recommended items down to external ID and click URL.
     * Default = false.
     * @param minimal True to reduce the information about the recommended items.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setMinimalItemData(boolean minimal) {
        setParameter(Keys.MINIMAL_RECOMMENDED_ITEM_DATA, minimal);
        return this;
    }

    /**
     * If set to false, omits category data in the response. If true, categoryIds and categories are returned in the
     * response. Default state: true.
     * @param returnCategoryData True to include category data.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setReturnCategoryData(boolean returnCategoryData) {
        setParameter(Keys.INCLUDE_CATEGORY_DATA, returnCategoryData);
        return this;
    }

    /**
     * List of product IDs that should not be recommended in this response. Separate each product with a pipe.
     * @param productIds The product IDs not to recommend.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setExcludedProductIds(String... productIds) {
        setListParameter(Keys.EXCLUDE_PRODUCT_IDS, productIds);
        return this;
    }

    /**
     * List of product IDs that should not be recommended in this response. Separate each product with a pipe.
     * @param productIds The product IDs not to recommend.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setExcludedProductIds(Collection<String> productIds) {
        setListParameter(Keys.EXCLUDE_PRODUCT_IDS, productIds);
        return this;
    }

    /**
     * Adds to the list of {@link UserAttribute}s describing the attribute context of the current of the user.
     * @param attributes The attributes to add.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder addUserAttributes(UserAttribute... attributes) {
        addListParameters(Keys.USER_ATTRIBUTES, getUserAttributeStrings(Arrays.asList(attributes)));
        return this;
    }

    /**
     * Sets the list of {@link UserAttribute}s describing the attribute context of the current of the user.
     * @param attributes The attributes to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setUserAttributes(UserAttribute... attributes) {
        setListParameter(Keys.USER_ATTRIBUTES, getUserAttributeStrings(Arrays.asList(attributes)));
        return this;
    }

    /**
     * Sets the list of {@link UserAttribute}s describing the attribute context of the current of the user.
     * @param attributes The attributes to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setUserAttributes(Collection<UserAttribute> attributes) {
        setListParameter(Keys.USER_ATTRIBUTES, getUserAttributeStrings(attributes));
        return this;
    }

    private Collection<String> getUserAttributeStrings(Collection<UserAttribute> attributes) {
        List<String> stringAttributes = new ArrayList<>(attributes.size());

        for (UserAttribute attribute : attributes) {
            stringAttributes.add(formatUserAttribute(attribute));
        }

        return stringAttributes;
    }

    private String formatUserAttribute(UserAttribute attribute) {
        String values = StringUtils.join(";", attribute.getValues());
        return String.format("%s:%s", attribute.getKey(), values);
    }

    // TODO Refinement

    /**
     * Shopper’s referrer prior to viewing this page. Used for reporting and merchandising. Highly recommended.
     * @param referrer The shopper's referrer prior to viewing this page.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setReferrer(String referrer) {
        setParameter(Keys.REFERRER, referrer);
        return this;
    }

    /**
     * Changes a portion of the click-through URL when clicking on a rec. This feature is typically used during
     * development to keep users within the same environment when clicking a recommendation.
     * @param server The click through server.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setClickThroughServer(String server) {
        setParameter(Keys.CLICK_THROUGH_SERVER, server);
        return this;
    }

    /**
     * Adds a single, or list of, product IDs. Part of an order definition on the purchase complete page.
     * @param ids The product ids to add.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder addProductIds(String... ids) {
        addListParameters(Keys.PRODUCT_ID, ids);
        return this;
    }

    /**
     * Sets a single, or list of, product IDs. Part of an order definition on the purchase complete page.
     * @param ids The product ids to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setProductIds(String... ids) {
        setListParameter(Keys.PRODUCT_ID, ids);
        return this;
    }

    /**
     * Sets a collection of product IDs. Part of an order definition on the purchase complete page.
     * @param ids The product ids to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setProductIds(Collection<String> ids) {
        setListParameter(Keys.PRODUCT_ID, ids);
        return this;
    }

    /**
     * Sets the ID of the category currently being viewed.
     * @param categoryId The ID of the category being viewed.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setCategoryId(String categoryId) {
        setParameter(Keys.CATEGORY_ID, categoryId);
        return this;
    }

    /**
     * Sets category hint IDs. Category hints can be added to any page type. Several category hints can be added on a
     * single page. Each category hint added qualifies the page for merchandising rules that are associated to the
     * category.
     * @param hintIds The category hint IDs to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setCategoryHintIds(String... hintIds) {
        setListParameter(Keys.CATEGORY_HINT_IDS, hintIds);
        return this;
    }

    /**
     * Sets category hint IDs. Category hints can be added to any page type. Several category hints can be added on a
     * single page. Each category hint added qualifies the page for merchandising rules that are associated to the
     * category.
     * @param hintIds The category hint IDs to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setCategoryHintIds(Collection<String> hintIds) {
        setListParameter(Keys.CATEGORY_HINT_IDS, hintIds);
        return this;
    }

    /**
     * Sets the search term the user typed in. You can also use the productId parameter to provide the product IDs of
     * the products in the search results.
     * @param searchTerm The search term the user typed in.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setSearchTerm(String searchTerm) {
        setParameter(Keys.SEARCH_TERM, searchTerm);
        return this;
    }

    /**
     * Sets the Order ID. Part of the order definition.
     * @param orderId The order ID.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setOrderId(String orderId) {
        setParameter(Keys.ORDER_ID, orderId);
        return this;
    }

    // TODO Products

    /**
     * Sets a registry ID string, used to identify a particular registry.
     * @param registryId The registry ID to set.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setRegistryId(String registryId) {
        setParameter(Keys.REGISTRY_ID, registryId);
        return this;
    }

    public PlacementsRecommendationsBuilder setRegistryTypeId(String registryTypeId) {
        setParameter(Keys.REGISTRY_TYPE_ID, registryTypeId);
        return this;
    }

    /**
     * Sets a single, or list of, product IDs.
     * @param productIds IDs of the products.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setAlreadyAddedRegistryItems(String... productIds) {
        setListParameter(Keys.ALREADY_ADDED_REGISTRY_ITEMS, productIds);
        return this;
    }

    /**
     * Sets a single, or list of, product IDs.
     * @param productIds IDs of the products.
     * @return This builder for chaining method calls.
     */
    public PlacementsRecommendationsBuilder setAlreadyAddedRegistryItems(Collection<String> productIds) {
        setListParameter(Keys.ALREADY_ADDED_REGISTRY_ITEMS, productIds);
        return this;
    }

    public PlacementsRecommendationsBuilder setStrategySet(StrategyType... strategies) {
        setStrategySet(Arrays.asList(strategies));
        return this;
    }

    public PlacementsRecommendationsBuilder setStrategySet(Collection<StrategyType> strategies) {
        List<String> stringStrategies = new ArrayList<>(strategies.size());
        for (StrategyType strategy : strategies) {
            stringStrategies.add(strategy.getKey());
        }

        setListParameter(Keys.STRATEGY_SET, stringStrategies);
        return this;
    }

    public PlacementsRecommendationsBuilder setCount(int count) {
        return this;
    }

    public PlacementsRecommendationsBuilder setStartIndex(int start) {
        return this;
    }

    @Override
    protected String getEndpointPath() {
        return "/rrPlatform/recsForPlacements";
    }

    @Override
    protected List<Object> parseResponse(WebResponse response) {
        return null;
    }
}
