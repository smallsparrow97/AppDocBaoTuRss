package com.gameloft.pc.appdocbaoturss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Main2Activity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        webView = (WebView) findViewById(R.id.webView);
        Intent intent = getIntent();
        String duongLink = intent.getStringExtra("link");
        webView.loadUrl(duongLink);
        webView.setWebViewClient(new WebViewClient());
    }
}
