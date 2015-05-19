package com.richrelevance.internal.net;

/**
 * Class which provides {@link WebRequestExecutor}s.
 */
public class WebRequestExecutorFactory {

    private static final WebRequestExecutorFactory INSTANCE = new WebRequestExecutorFactory();

    public static WebRequestExecutorFactory getInstance() {
        return INSTANCE;
    }

    /**
     * Gets a {@link WebRequestExecutor} which executes the given request.
     *
     * @param request        The request to execute.
     * @param connectTimeout The connect timeout to set on the request.
     * @param readTimeout    The read timeout to set on the request.
     * @return An executor to execute the given request.
     */
    public <Result> WebRequestExecutor<Result> create(WebRequest<Result> request, int connectTimeout, int readTimeout) {
        return new HttpUrlConnectionExecutor<>(request, connectTimeout, readTimeout);
    }
}
