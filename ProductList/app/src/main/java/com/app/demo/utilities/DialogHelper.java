package com.app.demo.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author Rajeev on 9/11/16.
 */

public class DialogHelper extends AlertDialog implements DialogInterface.OnClickListener {

    private Context mContext;
    private String mTitle;
    private String mMsg;
    private String mPositiveBtnTxt = "Yes";
    private String mNegativeBtntxt = "No";
    private String mNeutralBtntxt = "Ok";
    private boolean mHasPositiveBtn = false;
    private boolean mHasNegativeBtn = false;
    private boolean mHasNeutralBtn = false;

    /**
     * Creates a new Dialog box with only neutral button.
     *
     * @param context          passing application context.
     * @param resTitle         passing dialog title.
     * @param resMsg           passing dialog message.
     * @param resNeutralBtnTxt passing integer value that which button have to display.
     */
    public DialogHelper(Context context, int resTitle, int resMsg,
                        int resNeutralBtnTxt) {
        super(context);
        mContext = context;
        mTitle = context.getResources().getString(resTitle);
        mMsg = context.getResources().getString(resMsg);
        mHasNeutralBtn = true;
        mNeutralBtntxt = (resNeutralBtnTxt == -1 ? context
                .getString(android.R.string.ok) : context
                .getApplicationContext().getResources()
                .getString(resNeutralBtnTxt));
        init();
    }

    /**
     * Creates a New Dialog box with positive,negative and neutral buttons.
     *
     * @param context
     * 		passing application context.
     * @param resTitle
     * 		passing dialog title.
     * @param resMsg
     * 		passing dialog message.
     * @param resPositiveBtnTxt
     * 		passing positive button.
     * @param resNegativeBtnTxt
     * 		passing negative button.
     * @param resNeutralBtnTxt
     * 		passing neutral button.
     * @param dialogCallBack
     *            DialogCallback for callback or null
     */
    public DialogHelper(Context context, int resTitle, int resMsg,
                        int resPositiveBtnTxt, int resNegativeBtnTxt, int resNeutralBtnTxt,
                        DialogCallBack dialogCallBack) {
        super(context);
        mContext = context;
        mTitle = context.getResources().getString(resTitle);
        mMsg = context.getResources().getString(resMsg);
        mHasPositiveBtn = ((resPositiveBtnTxt != -1));
        mHasNegativeBtn = ((resNegativeBtnTxt != -1));
        mHasNeutralBtn = ((resNeutralBtnTxt != -1));
        mPositiveBtnTxt = mHasPositiveBtn ? context.getResources()
                .getString(resPositiveBtnTxt) : "";
        mNegativeBtntxt = mHasNegativeBtn ? context.getResources()
                .getString(resNegativeBtnTxt) : "";
        mNeutralBtntxt = mHasNeutralBtn ? context.getResources()
                .getString(resNeutralBtnTxt) : "";
        init();
    }

    /**
     * Initialize the alert dialog
     */
    private void init() {
        setTitle(mTitle);
        setIcon(android.R.drawable.ic_dialog_alert);
        setMessage(mMsg);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        if (mHasPositiveBtn) {
            setButton(DialogInterface.BUTTON_POSITIVE, mPositiveBtnTxt, this);
        }

        if (mHasNegativeBtn) {
            setButton(DialogInterface.BUTTON_NEGATIVE, mNegativeBtntxt, this);
        }

        if (mHasNeutralBtn) {
            setButton(DialogInterface.BUTTON_NEUTRAL, mNeutralBtntxt, this);
        }

        if ((!mHasPositiveBtn) && (!mHasNegativeBtn) && (!mHasNeutralBtn)) {
            setButton(DialogInterface.BUTTON_NEUTRAL,
                    mContext.getString(android.R.string.ok), this);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    @Override
    public void show() {
        if (mContext instanceof Activity
                && !((Activity) mContext).isFinishing())
            super.show();
    }
}
