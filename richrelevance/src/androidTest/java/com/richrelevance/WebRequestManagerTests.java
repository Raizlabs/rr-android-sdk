package com.richrelevance;

import android.util.Log;

import com.richrelevance.internal.Wrapper;
import com.richrelevance.internal.net.HttpMethod;
import com.richrelevance.internal.net.WebRequest;
import com.richrelevance.internal.net.WebRequestBuilder;
import com.richrelevance.internal.net.WebRequestManager;
import com.richrelevance.internal.net.WebResultInfo;
import com.richrelevance.internal.net.responses.WebResponse;

import junit.framework.TestCase;

import org.json.JSONObject;

public class WebRequestManagerTests extends TestCase {
    private WebRequestManager manager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        manager = new WebRequestManager();
    }

    public void testSynchronous() {
        WebResultInfo<JSONObject> resultInfo = manager.execute(new SimpleTestRequest());
        assertNotNull(resultInfo);
        assertNotNull(resultInfo.getResult());
        Log.i(getClass().getSimpleName(), resultInfo.getResult().toString());
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public void testAsynchronous() {
        final Wrapper<Boolean> completed = new Wrapper<>(false);
        final Wrapper<WebResultInfo<JSONObject>> payload = new Wrapper<>();
        manager.executeInBackground(new SimpleTestRequest(), new WebRequestManager.WebRequestListener<JSONObject>() {
            @Override
            public void onRequestComplete(WebResultInfo<JSONObject> resultInfo) {
                synchronized (completed) {
                    payload.set(resultInfo);
                    completed.set(true);
                    completed.notifyAll();
                }
            }
        });

        synchronized (completed) {
            while (!completed.get()) {
                try {
                    completed.wait();
                } catch (InterruptedException e) {
                    // Don't care
                }
            }
        }

        WebResultInfo<JSONObject> resultInfo = payload.get();
        assertNotNull(resultInfo);
        assertNotNull(resultInfo.getResult());
        Log.i(getClass().getSimpleName(), resultInfo.getResult().toString());
    }

    private class SimpleTestRequest implements WebRequest<JSONObject> {

        @Override
        public WebRequestBuilder getRequestBuilder() {
            return new WebRequestBuilder(HttpMethod.Get, "http://jsonplaceholder.typicode.com/posts/1");
        }

        @Override
        public JSONObject translate(WebResponse response) {
            return response.getContentAsJSON();
        }
    }

    ;
}
