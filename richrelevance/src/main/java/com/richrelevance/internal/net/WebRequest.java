package com.richrelevance.internal.net;

import com.richrelevance.internal.net.responses.WebResponse;

/**
 * Class which contains the logic of how to make a web request and interpret the response.
 *
 * @param <T> The type of data that will be returned as the response to the request.
 */
public interface WebRequest<T> {

    /**
     * Called to obtain a {@link RequestBuilder} that contains all the parameters and info needed to
     * perform the request.
     * @return A request builder representing the contents of the request.
     */
    public RequestBuilder getRequestBuilder();

    /**
     * Interprets the given response and returns a result.
     * @param response The response to interpret.
     * @return The result that was contained in the response.
     */
    public T translate(WebResponse response);
}
