package com.richrelevance;

import junit.framework.TestCase;

public class LoggingTests extends TestCase {

    public void testLoggingSettings() {
        RichRelevance.setLoggingEnabled(false);
        assertFalse(RRLog.isLoggingEnabled());

        RichRelevance.setLoggingEnabled(true);
        assertTrue(RRLog.isLoggingEnabled());
    }
}
