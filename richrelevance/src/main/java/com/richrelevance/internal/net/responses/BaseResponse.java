package com.richrelevance.internal.net.responses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.text.TextUtils;

import com.richrelevance.internal.RRLog;
import com.richrelevance.internal.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Abstract class which does some of the generic work for a response.
 */
public abstract class BaseResponse implements WebResponse {

	@Override
	public String getContentAsString() {
		InputStream content = null;
		try {
			content = getContentStream();
			if (content != null) {
				return IOUtils.readStream(content);
			}
		} catch (IOException e) {
			RRLog.w(getClass().getName(), "IOException in getContentAsString: " + e.getMessage());
		} finally {
			IOUtils.safeClose(content);
		}
		return null;
	}

	@Override
	public JSONArray getContentAsJSONArray() {
		String content = getContentAsString();
		if (!TextUtils.isEmpty(content)) {
			try {
				return new JSONArray(content);
			} catch (JSONException e) {
				RRLog.w(getClass().getName(), "JSONException in getContentAsJSONArray: " + e.getMessage());
			}
		}
		
		return null;
	}
	
	@Override
	public JSONObject getContentAsJSON() {
		String content = getContentAsString();
		if (!TextUtils.isEmpty(content)) {
			try {
				return new JSONObject(content);
			} catch (JSONException e) {
				RRLog.w(getClass().getName(), "JSONException in getContentAsJSON: " + e.getMessage());
			}
		}
		
		return null;
	}
}