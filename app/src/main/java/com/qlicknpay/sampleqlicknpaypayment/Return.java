package com.qlicknpay.sampleqlicknpaypayment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Return extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disp_return);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        Log.w("action: ", action);
        Log.w("data: ", String.valueOf(data));

    }
}
