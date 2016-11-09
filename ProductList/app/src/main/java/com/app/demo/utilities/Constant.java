package com.app.demo.utilities;

/**
 * @author Rajeev on 9/11/16.
 *         <p>
 *         This class will have all the constant variables.
 */

public class Constant {
    public static final String APP_TAG = "PRODUCT_LIST";

    // server url
    private static final String BASE_URL = "https://sky-firebase.firebaseapp.com/mobmerry/";

    public static final String CLOTH_URL = BASE_URL + "cloths.json";
    public static final String FOOD_URL = BASE_URL + "food.json";

    // error type constants. used to decide number of buttons to be displayed on
    static final int ERROR_TYPE_NETWORK = 0; // Display cancel and retry button
    static final int ERROR_TYPE_NO_NETWORK = ERROR_TYPE_NETWORK + 1; // Display cancel,retry, settings button
    static final int ERROR_TYPE_OTHER = ERROR_TYPE_NO_NETWORK + 1; // Display ok
}
