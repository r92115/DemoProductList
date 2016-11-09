package com.app.demo.utilities;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.app.demo.R;
import com.app.demo.controller.ApplicationController;

import static com.app.demo.utilities.Constant.ERROR_TYPE_NETWORK;
import static com.app.demo.utilities.Constant.ERROR_TYPE_NO_NETWORK;
import static com.app.demo.utilities.Constant.ERROR_TYPE_OTHER;

/**
 * @author Rajeev on 9/11/16.
 */

public class VolleyErrorHelper {

    /**
     * Returns appropriate message which is to be displayed to the user against
     * the specified error object.
     *
     * @param error passing error object
     * @return message id
     */
    public static int getMessage(Object error) {

        if (!ApplicationController.getInstance().isNetworkAvailable()) {
            return R.string.txt_hint_no_internet;
        } else if (error instanceof ServerError) {
            return R.string.txt_hint_server_error;
        } else if (error instanceof AuthFailureError) {
            return R.string.txt_hint_server_error;
        } else if (error instanceof NetworkError) {
            return R.string.txt_hint_server_error;
        } else if (error instanceof TimeoutError) {
            return R.string.txt_hint_server_error;
        }

        return R.string.txt_hint_server_error;
    }

    /**
     * Groups the error and return the type
     *
     * @param error passing error
     * @return error type constants
     */
    public static int getErrorType(Object error) {

        if (!ApplicationController.getInstance().isNetworkAvailable()) {
            return ERROR_TYPE_NO_NETWORK;
        }
        if (error instanceof TimeoutError || error instanceof NetworkError
                || error instanceof ServerError) {
            return ERROR_TYPE_NETWORK;
        }

        return ERROR_TYPE_OTHER;
    }
}
