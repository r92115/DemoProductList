package com.app.demo.utilities;

/**
 * @author Rajeev on 9/11/16.
 *         <p>
 *         This class will have all the constant variables.
 */

public class Constant {
    public static final String APP_TAG = "PRODUCT_LIST";

    // error type constants. used to decide number of buttons to be displayed on
    static final int ERROR_TYPE_NETWORK = 0; // Display cancel and retry button
    static final int ERROR_TYPE_NO_NETWORK = ERROR_TYPE_NETWORK + 1; // Display cancel,retry, settings button
    static final int ERROR_TYPE_OTHER = ERROR_TYPE_NO_NETWORK + 1; // Display ok
}
