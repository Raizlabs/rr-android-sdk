package com.richrelevance.find.search;


import java.util.List;
import java.util.Locale;

public class Facet {

    public static class Keys {
        public static final String TYPE = "facet";
        public static final String FITERS = "values";
    }

    private String type;
    private List<Filter> filters;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public class Filter {

        public class Keys {
            public static final String VALUE = "value";
            public static final String COUNT = "count";
            public static final String FILTER = "filter";
        }

        private String filter;
        private int count;
        private String value;

        public String getFilter() {
            return filter;
        }

        public void setFilter(String filter) {
            this.filter = filter;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getApiValue() {
            return String.format(Locale.US, "%s.%s", getType(), getValue());
        }
    }
}
