package com.qlicknpay.sampleqlicknpaypayment;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class Payment extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // DISPLAY ACTIVITY LAYOUT
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disp_payment);
        Bundle data = getIntent().getExtras();

        if(data != null)
        {

            // RETRIEVED DATA FROM PREVIOUS ACTIVITY
            String url = data.getString("url");

            // RUN THE WEBVIEW BY USING PREVIOUS DATA
            WebView webview = (WebView) findViewById(R.id.WebViewPayment);
            webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

            webview.getSettings().setJavaScriptEnabled(true);

            webview.getSettings().setDomStorageEnabled(true);

            webview.setWebViewClient(new WebViewClient());

            webview.loadUrl(url);

        }

    }
}
