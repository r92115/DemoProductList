package com.app.demo.controller;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static com.app.demo.utilities.Constant.APP_TAG;

/**
 * @author Rajeev on 9/11/16.
 */

public class ApplicationController extends Application {

    // Global request queue for Volley
    private RequestQueue mRequestQueue;

    // A singleton instance of the application class for easy access in other places
    private static ApplicationController sInstance;

    private ConnectivityManager connectivityManager = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        sInstance = this;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req passing request.
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(APP_TAG);

        // set max retries
        int maxRetries = 10;
        int socketTimeout = 100 * 100 * 10;

        req.setRetryPolicy(new DefaultRetryPolicy(socketTimeout, maxRetries,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(req);
    }

    /**
     * Returns connectivity manager instance
     *
     * @return Connectivity manager class instance.
     */
    private ConnectivityManager getConnectivityManager() {

        if (connectivityManager == null) {
            connectivityManager = (ConnectivityManager) getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return connectivityManager;
    }

    /**
     * Checks whether the network is available or not.
     *
     * @return true is network is available otherwise false
     */
    public boolean isNetworkAvailable() {

        NetworkInfo activeNetworkInfo = getConnectivityManager()
                .getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
