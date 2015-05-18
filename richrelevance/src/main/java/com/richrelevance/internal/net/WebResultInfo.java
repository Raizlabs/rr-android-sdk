package com.richrelevance.internal.net;

/**
 * Interface which provides information about the result of a web request.
 *
 * @param <Result> The type of the result which will be returned.
 */
public interface WebResultInfo<Result> {

    /**
     * @return The time that the response was received in milliseconds.
     */
    public long getResponseTimestamp();

    /**
     * @return The result of the request, or null if it failed.
     */
    public Result getResult();

    /**
     * @return The response code from the request, or -1 if it failed.
     */
    public int getResponseCode();

    /**
     * @return The response message from the request, or null if it failed.
     */
    public String getResponseMessage();
}
