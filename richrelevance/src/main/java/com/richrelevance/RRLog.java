package com.richrelevance;

/**
 * Class which mirrors the default Android {@link android.util.Log}, but allows enabling and disabling. All Rich
 * Relevance logging will go through here such that logging may be globally disabled in the SDK.
 */
public class RRLog {

    private static Boolean manualLoggingEnabled = null;

    static boolean isLoggingEnabled() {
        // If it has been set manually, use it
        if (manualLoggingEnabled != null) {
            return manualLoggingEnabled;
        } else {
            // Otherwise default to production
            return !RichRelevance.isProduction();
        }
    }

    /**
     * Sets whether logging is enabled.
     * @param enabled True to enable logging, false to disable it.
     */
    static void setLoggingEnabled(boolean enabled) {
        manualLoggingEnabled = enabled;
    }

    public static void v(String tag, String message) {
        if (isLoggable(tag, android.util.Log.VERBOSE)) {
            android.util.Log.v(tag, message);
        }
    }

    public static void v(String tag, String message, Throwable throwable) {
        if (isLoggable(tag, android.util.Log.VERBOSE)) {
            android.util.Log.v(tag, message, throwable);
        }
    }

    public static void d(String tag, String message) {
        if (isLoggable(tag, android.util.Log.DEBUG)) {
            android.util.Log.d(tag, message);
        }
    }

    public static void d(String tag, String message, Throwable throwable) {
        if (isLoggable(tag, android.util.Log.DEBUG)) {
            android.util.Log.d(tag, message, throwable);
        }
    }

    public static void i(String tag, String message) {
        if (isLoggable(tag, android.util.Log.INFO)) {
            android.util.Log.i(tag, message);
        }
    }

    public static void i(String tag, String message, Throwable throwable) {
        if (isLoggable(tag, android.util.Log.INFO)) {
            android.util.Log.i(tag, message, throwable);
        }
    }

    public static void w(String tag, String message) {
        if (isLoggable(tag, android.util.Log.WARN)) {
            android.util.Log.w(tag, message);
        }
    }

    public static void w(String tag, String message, Throwable throwable) {
        if (isLoggable(tag, android.util.Log.WARN)) {
            android.util.Log.w(tag, message, throwable);
        }
    }

    public static void e(String tag, String message) {
        if (isLoggable(tag, android.util.Log.ERROR)) {
            android.util.Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable throwable) {
        if (isLoggable(tag, android.util.Log.ERROR)) {
            android.util.Log.e(tag, message, throwable);
        }
    }

    private static boolean isLoggable(String tag, int level) {
        return isLoggingEnabled();
    }
}
