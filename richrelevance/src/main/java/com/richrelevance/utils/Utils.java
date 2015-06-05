package com.richrelevance.utils;

import java.util.Collection;

public class Utils {

    public static <T> void addIfNonNull(Collection<T> collection, T item) {
        if (item != null) {
            collection.add(item);
        }
    }
}
