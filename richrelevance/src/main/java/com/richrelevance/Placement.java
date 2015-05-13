package com.richrelevance;

import java.util.Locale;

public class Placement {

    public enum PlacementPageType {

        HOME {
            @Override
            public String getKey() {
                return "home_page";
            }
        },
        ITEM {
            @Override
            public String getKey() {
                return "item_page";
            }
        },
        ADD_TO_CART {
            @Override
            public String getKey() {
                return "add_to_cart_page";
            }
        },
        SEARCH {
            @Override
            public String getKey() {
                return "search_page";
            }
        },
        PURCHASE_COMPLETE {
            @Override
            public String getKey() {
                return "purchase_complete_page";
            }
        },
        CATEGORY {
            @Override
            public String getKey() {
                return "category_page";
            }
        },
        CART {
            @Override
            public String getKey() {
                return "cart_page";
            }
        };


        abstract String getKey();
    }

    private PlacementPageType pageType;
    private String name;

    public Placement(PlacementPageType pageType, String name) {
        this.pageType = pageType;
        this.name = name;
    }

    String getApiValue() {
        return String.format(Locale.US, "%s.%s", pageType.getKey(), name);
    }
}
