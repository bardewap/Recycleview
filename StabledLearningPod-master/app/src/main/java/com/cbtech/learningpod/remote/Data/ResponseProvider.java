package com.cbtech.learningpod.remote.Data;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface ResponseProvider {
    void onSuccessResponse(JSONObject response);
    void onErrorResponse(VolleyError error);
}
