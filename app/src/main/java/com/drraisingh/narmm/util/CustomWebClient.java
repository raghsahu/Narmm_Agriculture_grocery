package com.drraisingh.narmm.util;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Raghvendra Sahu on 26-Jan-20.
 */
public class CustomWebClient  extends WebViewClient {
    private Context mContext;

    public CustomWebClient(Context context) {
        this.mContext = context;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        final WebView myWebView = (WebView) view;

        String varMySheet = "var mySheet = document.styleSheets[0];";

        String addCSSRule = "function addCSSRule(selector, newRule) {"
                + "ruleIndex = mySheet.cssRules.length;"
                + "mySheet.insertRule(selector + '{' + newRule + ';}', ruleIndex);"

                + "}";

        String insertRule1 = "addCSSRule('html', 'padding: 0px; height: "
                + (myWebView.getMeasuredHeight() /  mContext.getResources().getDisplayMetrics().density)
                + "px; -webkit-column-gap: 0px; -webkit-column-width: "
                + myWebView.getMeasuredWidth() + "px;')";

        myWebView.loadUrl("javascript:" + varMySheet);
        myWebView.loadUrl("javascript:" + addCSSRule);
        myWebView.loadUrl("javascript:" + insertRule1);

    }
}
