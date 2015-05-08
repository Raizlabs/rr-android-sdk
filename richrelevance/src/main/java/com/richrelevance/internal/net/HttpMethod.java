package com.richrelevance.internal.net;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Enum which represents each of the Http Methods.
 * Contains some helpers for getting the method name and vice versa.
 */
public enum HttpMethod {
    Get {
        @Override
        public String getMethodName() {
            return "GET";
        }
    },
    Post {
        @Override
        public String getMethodName() {
            return "POST";
        }
    },
    Put {
        @Override
        public String getMethodName() {
            return "PUT";
        }
    },
    Delete {
        @Override
        public String getMethodName() {
            return "DELETE";
        }
    },
    Head {
        @Override
        public String getMethodName() {
            return "HEAD";
        }
    };

    public abstract String getMethodName();

    public static HttpMethod fromName(String name) {
        for (HttpMethod method : values()) {
            if (method.getMethodName().equalsIgnoreCase(name)) {
                return method;
            }
        }

        return null;
    }
}
