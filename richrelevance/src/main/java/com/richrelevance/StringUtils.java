package com.richrelevance;

import java.util.Collection;

public class StringUtils {

    public static String join(String delimiter, String... strings) {
        if (strings == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i > 0) {
                builder.append(delimiter);
            }

            builder.append(strings[i]);
        }

        return builder.toString();
    }

    public static String join(String delimiter, Collection<String> strings) {
        if (strings == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        boolean isFirst = true;
        for (String string : strings) {
            if (!isFirst) {
                builder.append(delimiter);
            }

            builder.append(string);
            isFirst = false;
        }

        return builder.toString();
    }
}
