package com.drraisingh.narmm.Fragment.UseFragmnet;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.drraisingh.narmm.Adapter.Card_NewAdapter;
import com.drraisingh.narmm.Model.UseableModel.Cartlist_model;
import com.drraisingh.narmm.R;
import com.drraisingh.narmm.activity.MainActivity;
import com.drraisingh.narmm.util.AppController;
import com.drraisingh.narmm.util.CustomVolleyJsonRequest;
import com.drraisingh.narmm.util.CustomWebClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drraisingh.narmm.Config.BaseURL.GET_get_cart;
import static com.drraisingh.narmm.Config.BaseURL.GET_order_bill;

/**
 * Created by Raghvendra Sahu on 11-Jan-20.
 */
public class ViewPdfFragment extends AppCompatActivity {


    String Order_id;
    private WebView web_pdf;

    public ViewPdfFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pdf);
   // }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_view_pdf, container, false);
//        ((MainActivity)  getActivity()).setTitle("Bill");

       // web_pdf=findViewById(R.id.web_pdf);
        WebView webView = (WebView) findViewById(R.id.web_pdf);
        WebSettings webSettings = webView.getSettings();
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        try {
          // Viewpdf = getArguments().getString("Viewpdf");
            Order_id = getIntent().getStringExtra("Order_id");

        }catch (Exception e){

        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);


        webView.loadUrl(GET_order_bill+Order_id);
        Log.e("WEB VIEW ","==== "+GET_order_bill+Order_id);

        /*WebSettings webSettings = web_pdf.getSettings();
        web_pdf.getSettings().setJavaScriptEnabled(true);
        web_pdf.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_pdf.getSettings().setPluginState(WebSettings.PluginState.ON_DEMAND);
        web_pdf.getSettings().setMediaPlaybackRequiresUserGesture(false);
        web_pdf.getSettings().setSupportZoom(true);
        web_pdf.getSettings().setBuiltInZoomControls(true);


        if (Build.VERSION.SDK_INT >= 21) {
            web_pdf.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(web_pdf, true);
        }

        if (android.os.Build.VERSION.SDK_INT < 16) {
            web_pdf.setBackgroundColor(0x00000000);
        } else {
            web_pdf.setBackgroundColor(Color.argb(1, 0, 0, 0));
        }

        //**************webcrome client****************************
        web_pdf.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //progressBar.setProgress(progress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                return super.onJsBeforeUnload(view, url, message, result);
            }

        });

        //webview client
        web_pdf.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView webview, String url, Bitmap favicon) {
                super.onPageStarted(webview, url, favicon);
                webview.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageFinished(WebView webview, String url) {

                webview.setVisibility(View.VISIBLE);
                super.onPageFinished(webview, url);

            }
        });
        web_pdf.setWebChromeClient(new WebChromeClient());
        web_pdf.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");

        web_pdf.getSettings().setLoadWithOverviewMode(true);
        web_pdf.getSettings().setUseWideViewPort(true);

        web_pdf.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);

        webSettings.setDefaultTextEncodingName("utf-8");
        web_pdf.setVerticalScrollBarEnabled(true);
        web_pdf.setHorizontalScrollBarEnabled(true);
        web_pdf.requestFocus();


        web_pdf.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        web_pdf.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        }); */

       // return  view;
    }

//    private void GetBill() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),R.style.MyGravity);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        progressDialog.show();
//
//        // Tag used to cancel the request
//        String tag_json_obj = "json_get_address_req";
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("order_id",Order_id );
//        params.put("Link BILL ",GET_order_bill+Order_id );
//
//        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
//                GET_order_bill+Order_id, params, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.e("cart_bill", response.toString());
//                progressDialog.dismiss();
//
//                if (response!=null){
//
//                   //web_pdf.loadUrl(response.toString());
//                    web_pdf.loadData(response.toString(), "text/html; charset=utf-8", null);
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                VolleyLog.d("Error_bill: " + error.getMessage());
//                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    if (getActivity() != null) {
//                        Toast.makeText(getActivity(), getResources().getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
//
//    }


}
