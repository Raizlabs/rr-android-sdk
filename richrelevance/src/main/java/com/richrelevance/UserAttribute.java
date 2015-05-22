package com.richrelevance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UserAttribute {

    private String key;
    private List<String> values;

    public UserAttribute(String key, String... values) {
        this.key = key;
        setValues(values);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getValues() {
        return values;
    }

    public void addValue(String value) {
        values.add(value);
    }

    public void setValues(List<String> values) {
        if (values == null) {
            values = new LinkedList<>();
        }

        this.values = values;
    }

    public void setValues(String...values) {
        setValues(Arrays.asList(values));
    }
}
