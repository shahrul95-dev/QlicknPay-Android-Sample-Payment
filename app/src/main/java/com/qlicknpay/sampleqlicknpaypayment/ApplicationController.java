package com.qlicknpay.sampleqlicknpaypayment;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class ApplicationController {

    private static final String LOG_TAG = "ApplicationController";

    /**
     * startActivity
     */
    public static boolean startActivity(String className, Activity requester) {

        try {
            Class c = Class.forName(className);
            Intent intent = new Intent(requester, c);
            requester.startActivity(intent);
            return true;
        }
        catch (ClassNotFoundException ex) {
            Log.e(LOG_TAG, ex.toString());
        }

        return false;
    }
}
