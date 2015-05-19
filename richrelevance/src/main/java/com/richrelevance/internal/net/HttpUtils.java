package com.richrelevance.internal.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Class of helper utilities for HTTP.
 */
class HttpUtils {

    static String getQueryString(Map<String, String> map) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // This will throw a NullPointerException if you call URLEncoder.encode(null).
            // Instead caught & thrown with description above.
            String value = entry.getValue();
            if (value == null) {
                // Can't be more specific without jeopardizing security.
                throw new NullPointerException("Malformed Request. Entry has null value for key: "
                        + entry.getKey());
            }

            if (!first) {
                queryBuilder.append("&");
            }
            queryBuilder.append(entry.getKey());
            queryBuilder.append("=");
            try {
                queryBuilder.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                // Fallback
                //noinspection deprecation
                queryBuilder.append(URLEncoder.encode(value));
            }

            first = false;

        }

        return queryBuilder.toString();
    }
}
