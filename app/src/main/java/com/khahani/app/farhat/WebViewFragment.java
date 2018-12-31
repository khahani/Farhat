package com.khahani.app.farhat;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class WebViewFragment extends Fragment {

    public static final String SITE_URL = "https://ndip.ir/asadi"; //asadi

    private static final String ARG_PARAM_NAME = "name";
    private static final String ARG_PARAM_LASTNAME = "lastName";
    private static final String ARG_PARAM_CODE = "code";
    private static final String ARG_PARAM_PHONE = "phone";

    private Person mPerson;

    private OnFragmentInteractionListener mListener;

    private WebView webView;
    private boolean stopReload = false;

    public WebViewFragment() {
    }

    public static WebViewFragment newInstance(Person person) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_NAME, person.getName());
        args.putString(ARG_PARAM_LASTNAME, person.getLastName());
        args.putString(ARG_PARAM_CODE, person.getCode());
        args.putString(ARG_PARAM_PHONE, person.getPhone());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPerson = new Person(
                    getArguments().getString(ARG_PARAM_NAME),
                    getArguments().getString(ARG_PARAM_LASTNAME),
                    getArguments().getString(ARG_PARAM_CODE),
                    getArguments().getString(ARG_PARAM_PHONE)
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        init(view);

        Button stopAndReloadButton = view.findViewById(R.id.buttonReload);
        stopAndReloadButton.setOnClickListener(stopReloadWebView);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void init(View view) {
        webView = view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.requestDisallowInterceptTouchEvent(true);

        webView.addJavascriptInterface(this, "Android");
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(SITE_URL);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

//                String code, name, lastname, phone;
//                code = "1234567890";
//                name = "محمم";
//                lastname = "خووو";
//                phone = "09390000000";

                StringBuilder builder = new StringBuilder();
                builder.append("javascript:");

                if (!webView.getUrl().equals("https://ndip.ir/booking")) {
                    if (!stopReload) {
                        builder.append("var isOk = document.getElementById('nationalCode');" +
                                "if (!isOk){" +
                                "window.Android.log('must reload');" +
                                "window.Android.reloadPage();" +
                                "}");
                    }
                }
                builder.append(
                        "document.getElementById('nationalCode').value = '" + mPerson.getCode() + "';" +
                                "document.getElementById('patientName').value = '" + mPerson.getName() + "';" +
                                "document.getElementById('patientFamily').value = '" + mPerson.getLastName() + "';" +
                                "document.getElementById('cellphone').value = '" + mPerson.getPhone() + "';" +
                                "var sq = document.getElementById('captcha').placeholder;" +
                                "sq = sq.replace(' میشه؟', '');" +
                                "var qArray = sq.split('+');" +
                                "var ans = parseInt(qArray[0]) + parseInt(qArray[1]);" +
                                "document.getElementById('captcha').value = ans;"
                );

                builder.append("document.getElementsByName('bookingConfirm')[0].click();");

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

    private View.OnClickListener stopReloadWebView = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            stopReload = !stopReload;
            Button reload = (Button) view;
            reload.setText(stopReload ? getString(R.string.reload) : getString(R.string.stop));
            reloadPage();
        }
    };

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

}
