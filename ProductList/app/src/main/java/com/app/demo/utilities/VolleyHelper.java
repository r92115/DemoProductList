package com.app.demo.utilities;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.demo.R;
import com.app.demo.controller.ApplicationController;

import org.json.JSONObject;

import static com.app.demo.utilities.Constant.ERROR_TYPE_NETWORK;
import static com.app.demo.utilities.Constant.ERROR_TYPE_NO_NETWORK;
import static com.app.demo.utilities.Constant.ERROR_TYPE_OTHER;

/**
 * @author Rajeev on 9/11/16.
 */

public class VolleyHelper {

    // VolleyResponse call back instance
    private VolleyResponseCallBack mCallBack;

    // Error Dialog call back instance
    private DialogCallBack mDialogCallBack;

    // Application context
    private Context mContext;

    // Determines whether to display the error dialog or not
    private boolean mShowErrorDialog;

    /**
     * Adds the volley request to the Queue.
     *
     * @param req passing request to queue.
     */
    private <T> void addToRequestQueue(Request<T> req) {
        ApplicationController.getInstance().addToRequestQueue(req);
    }

    /**
     * Creates a volley JSONObject request object
     *
     * @param context             calling activity context
     * @param method              the HTTP methods to use
     * @param url                 to fetch the Json data from
     * @param responseCallBack    for volley response and error
     * @param showErrorDialog     determines whether error dialog needs to be shown or not.if
     *                            true will handle the error and shows the generic error
     *                            messages.It can have positive,negative and neutral button
     *                            depending on the error type.
     * @param errorDialogCallback for firing error dialog button events
     */
    public void doJsonObjectRequest(Context context, int method, String url,
                                    VolleyResponseCallBack responseCallBack,
                                    boolean showErrorDialog, DialogCallBack errorDialogCallback) {

        mContext = context;

        mCallBack = responseCallBack;

        mDialogCallBack = errorDialogCallback;

        mShowErrorDialog = showErrorDialog;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method,
                url, onJsonObjectReqResponse(), onVolleyError());

        addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Volley JSONObject request call back.
     *
     * @return json response
     */
    private Response.Listener<JSONObject> onJsonObjectReqResponse() {
        return new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mCallBack.onResponse(response);
            }

        };
    }

    /**
     * Volley error call back.
     *
     * @return json error.
     */
    private Response.ErrorListener onVolleyError() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (mShowErrorDialog) {

                    int positiveBtnTxt = -1;
                    int negativeBtnTxt = -1;
                    int neutralBtnTxt = -1;

                    final int errorType = VolleyErrorHelper.getErrorType(error);

                    switch (errorType) {
                        case ERROR_TYPE_NO_NETWORK:
                            positiveBtnTxt = R.string.txt_hint_retry;
                            negativeBtnTxt = android.R.string.cancel;
                            neutralBtnTxt = R.string.action_settings;
                            break;
                        case ERROR_TYPE_NETWORK:
                            positiveBtnTxt = R.string.txt_hint_retry;
                            negativeBtnTxt = android.R.string.cancel;
                            break;
                        case ERROR_TYPE_OTHER:
                            neutralBtnTxt = android.R.string.ok;
                            break;
                    }

                    DialogHelper volleyErrorDlg = new DialogHelper(mContext,
                            R.string.title_err_attention,
                            VolleyErrorHelper.getMessage(error),
                            positiveBtnTxt,
                            negativeBtnTxt,
                            neutralBtnTxt,
                            new DialogCallBack() {

                                @Override
                                protected void onPositiveBtnClick() {
                                    super.onPositiveBtnClick();

                                    if (mDialogCallBack != null)
                                        mDialogCallBack.onPositiveBtnClick();
                                }

                                @Override
                                protected void onNegativeBtnClick() {
                                    super.onNegativeBtnClick();

                                    if (mDialogCallBack != null)
                                        mDialogCallBack.onNegativeBtnClick();
                                }

                                @Override
                                protected void onNeutralBtnClick() {
                                    super.onNeutralBtnClick();

                                    // open network settings
                                    if (errorType == ERROR_TYPE_NO_NETWORK) {
                                        Intent settingsIntent = new Intent(
                                                Settings.ACTION_SETTINGS);
                                        mContext.startActivity(settingsIntent);
                                    } else {
                                        if (mDialogCallBack != null) {
                                            mDialogCallBack.onNeutralBtnClick();
                                        }
                                    }
                                }

                            });
                    volleyErrorDlg.show();
                }
                mCallBack.onError(error);
            }
        };
    }
}
