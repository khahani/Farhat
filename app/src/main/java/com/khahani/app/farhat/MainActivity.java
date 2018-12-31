package com.khahani.app.farhat;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String SITE_URL = "https://ndip.ir/asadi"; //asadi

    WebView webView;
    private boolean stopReload = false;

    @android.webkit.JavascriptInterface
    public void reloadPage() {
        Log.e("js", "reloadPage: reloading...");
        webView.post(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(SITE_URL);
            }
        });

    }

    @android.webkit.JavascriptInterface
    public void log(String message) {
        Log.e("js", "log: " + message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    public void init() {
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(this, "Android");
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(SITE_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                String code, name, lastname, phone;
                code = "1234567890";
                name = "محمم";
                lastname = "خووو";
                phone = "09390000000";

                StringBuilder builder = new StringBuilder();
                builder.append("javascript:");

                if (!stopReload) {
                    builder.append("var isOk = document.getElementById('nationalCode');" +
                            "if (!isOk){" +
                            "window.Android.log('must reload');" +
                            "window.Android.reloadPage();" +
                            "}");
                }
                builder.append(
                        "document.getElementById('nationalCode').value = '" + code + "';" +
                                "document.getElementById('patientName').value = '" + name + "';" +
                                "document.getElementById('patientFamily').value = '" + lastname + "';" +
                                "document.getElementById('cellphone').value = '" + phone + "';" +
                                "var sq = document.getElementById('captcha').placeholder;" +
                                "sq = sq.replace(' میشه؟', '');" +
                                "var qArray = sq.split('+');" +
                                "var ans = parseInt(qArray[0]) + parseInt(qArray[1]);" +
                                "document.getElementById('captcha').value = ans;"
                );

                //builder.append("document.getElementsByName('bookingConfirm')[0].click()");

                String js = builder.toString();

                if (Build.VERSION.SDK_INT >= 19) {
                    view.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String s) {
                            Log.e("js", "onReceiveValue: " + s);
                        }
                    });
                } else {
                    view.loadUrl(js);
                }
            }
        });
    }


    public void stopReloadWebView(View view) {
        stopReload = !stopReload;
        Button reload = findViewById(R.id.buttonReload);
        reload.setText(stopReload ? getString(R.string.reload) : getString(R.string.stop));
        reloadPage();
    }
}
