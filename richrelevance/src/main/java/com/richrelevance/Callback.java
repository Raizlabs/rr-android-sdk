package com.richrelevance;

public interface Callback<T> {

    public void onResult(T result);
    public void onError(Error error);
}
