package com.js.jainsamaj.listeners;

/**
 * Created by arbaz on 6/2/17.
 */

public interface OnApiCallListener {

    void onSuccess(int responseCode, String responseString, String requestType);

    void onFailure(String errorMessage);
}
