package com.richrelevance;

import junit.framework.TestCase;

public class LoggingTests extends TestCase {

    public void testLoggingSettings() {
        // Ensure that logging runs only in non-production before we enable it manually
        RichRelevance.setIsProduction(true);
        assertFalse(RRLog.isLoggingEnabled());

        RichRelevance.setIsProduction(false);
        assertTrue(RRLog.isLoggingEnabled());

        RichRelevance.setLoggingEnabled(false);
        assertFalse(RRLog.isLoggingEnabled());

        RichRelevance.setLoggingEnabled(true);
        assertTrue(RRLog.isLoggingEnabled());
    }
}
