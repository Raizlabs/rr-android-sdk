package com.richrelevance.find.search;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Facet implements Parcelable {

    public static class Keys {
        public static final String TYPE = "facet";
        public static final String FITERS = "values";
    }

    private String type;
    private List<Filter> filters;

    public Facet() {}

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

    protected Facet(Parcel in) {
        type = in.readString();
        if (in.readByte() == 0x01) {
            filters = new ArrayList<>();
            in.readList(filters, Filter.class.getClassLoader());
        } else {
            filters = null;
        }
    }

    // Inner classes

    public class Filter {

        // TODO make Filter Parcelable

        public class Keys {
            public static final String VALUE = "value";
            public static final String COUNT = "count";
            public static final String FILTER = "filter";
        }

        private String filter;
        private int count;
        private String value;

        public Filter() {}

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

        protected Filter(Parcel in) {
            filter = in.readString();
            count = in.readInt();
            value = in.readString();
        }
    }

    // End Inner classes

    // Parcelable methods and classes

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        if (filters == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(filters);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Facet> CREATOR = new Parcelable.Creator<Facet>() {
        @Override
        public Facet createFromParcel(Parcel in) {
            return new Facet(in);
        }

        @Override
        public Facet[] newArray(int size) {
            return new Facet[size];
        }
    };

    // End Parcelable methods and classes
}
