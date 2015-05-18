package com.richrelevance;

public enum UserProfileField {

    ORDERS {
        @Override
        public String getKey() {
            return "orders";
        }
    };

    public abstract String getKey();
}
