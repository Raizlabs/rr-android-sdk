package com.richrelevance;

public enum UserPreference {

    BRAND {
        @Override
        public String getKey() {
            return "pref_brand";
        }
    };

    public abstract String getKey();
}
