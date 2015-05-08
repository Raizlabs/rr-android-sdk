package com.richrelevance.internal.net;

import java.util.Date;

/**
 * {@link WebResultInfo} implementation for a request that totally failed and doesn't
 * have any sort of response. Useful for connection failures, invalid parameters, etc.
 *
 * @param <Result> The type of the result which will be returned
 */
public class FailedResultInfo<Result> implements WebResultInfo<Result>{

	private long timestamp;

	public FailedResultInfo(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public long getResponseTimestamp() {
		return timestamp;
	}

	@Override
	public Result getResult() {
		return null;
	}

	@Override
	public int getResponseCode() {
		return -1;
	}

	@Override
	public String getResponseMessage() {
		return null;
	}
}
