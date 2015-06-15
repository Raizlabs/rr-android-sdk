package com.richrelevance.userProfile;

import com.richrelevance.BaseTestCase;
import com.richrelevance.RequestBuilderAccessor;

public class UserProfileBuilderTests extends BaseTestCase {

    public void testFieldConstants() {
        assertEquals(UserProfileField.VIEWED_ITEMS.getKey(), "viewedItems");
        assertEquals(UserProfileField.CLICKED_ITEMS.getKey(), "clickedItems");
        assertEquals(UserProfileField.REFERRER_URLS.getKey(), "referrerUrls");
        assertEquals(UserProfileField.ORDERS.getKey(), "orders");
        assertEquals(UserProfileField.VIEWED_CATEGORIES.getKey(), "viewedCategories");
        assertEquals(UserProfileField.VIEWED_BRANDS.getKey(), "viewedBrands");
        assertEquals(UserProfileField.ADDED_TO_CART_ITEMS.getKey(), "addedToCartItems");
        assertEquals(UserProfileField.SEARCHED_TERMS.getKey(), "searchedTerms");
        assertEquals(UserProfileField.USER_ATTRIBUTES.getKey(), "userAttributes");
        assertEquals(UserProfileField.USER_SEGMENTS.getKey(), "userSegments");
        assertEquals(UserProfileField.VERB_NOUNS.getKey(), "verbNouns");
        assertEquals(UserProfileField.COUNTED_EVENTS.getKey(), "countedEvents");
    }

    public void testAddField() {
        UserProfileBuilder builder = new UserProfileBuilder();
        RequestBuilderAccessor accessor = new RequestBuilderAccessor(builder);

        builder.addFields(UserProfileField.ORDERS, UserProfileField.VERB_NOUNS, UserProfileField.CLICKED_ITEMS);
        assertEquals(accessor.getParamValue(UserProfileBuilder.Keys.FIELDS), "orders,verbNouns,clickedItems");
    }
}
