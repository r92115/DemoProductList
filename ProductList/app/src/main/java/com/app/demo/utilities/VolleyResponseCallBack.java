package com.app.demo.utilities;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * @author Rajeev on 9/11/16.
 *         <p>
 *         Call back for handling volley response and error.
 */

public abstract class VolleyResponseCallBack {

    /**
     * Call back method for Volley JSONObject request.Override this method for
     * parsing JSONObject response
     *
     * @param response passing json response
     */
    public void onResponse(JSONObject response) {
    }

    /**
     * Call back method for error in Volley request. Implement this if you wants
     * to handle the error.
     *
     * @param volleyError passing volley error.
     */
    public abstract void onError(VolleyError volleyError);
}
