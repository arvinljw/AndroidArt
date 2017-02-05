package net.arvin.afbaselibrary.uis.activities;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;

import net.arvin.afbaselibrary.R;
import net.arvin.afbaselibrary.widgets.WebView4Scroll;

/**
 * created by arvin on 16/11/24 13:52
 * email：1035407623@qq.com
 */
public abstract class BaseHtmlActivity extends BaseRefreshActivity {
    private final String UP_FILE_TAG = "UP_FILE";
    public static final String URL = "url";
    public static final String TITLE = "title";
    protected String url;
    protected String title;
    protected WebView4Scroll mWebView;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mWebView = getView(R.id.pre_web_view);
        mWebView.setSwipeRefreshLayout(mLayoutRefresh);

        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);

        mWebView.setWebViewClient(new AFWebViewClient(getOnLoadUrl()));

        autoRefresh();
    }

    @Override
    public void onRefresh() {
        if (getIntent().getExtras() != null) {
            url = getIntent().getExtras().getString(URL);
            mWebView.loadUrl(url);
        } else {
            mWebView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showToast("未设置网页链接");
                    refreshComplete();
                }
            }, 100);
        }
    }

    public class AFWebViewClient extends WebViewClient {
        private OnLoadUrl loadUrl;

        public AFWebViewClient(OnLoadUrl loadUrl) {
            this.loadUrl = loadUrl;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.d("shouldOverrideUrlLoading URL" + url);
            if (loadUrl == null || !loadUrl.onLoadUrl(url)) {
                mWebView.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (tvTitle != null && TextUtils.isEmpty(title)) {
                title = view.getTitle();
                tvTitle.setText(view.getTitle());
            }
            refreshComplete();
        }

        /**
         * 若访问https的网页需要重写下边两个方法
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * webView上传文件需要重写如下几个方法
     */
    protected class AFWebChromeClient extends WebChromeClient {
        /**
         * For Android 3.0+
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            Log.i(UP_FILE_TAG, "in openFile Uri Callback");
            openFileChoose(uploadMsg, "");
        }

        /**
         * For Android 3.0+
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            Log.i(UP_FILE_TAG, "in openFile Uri Callback has accept Type" + acceptType);
            openFileChoose(uploadMsg, acceptType);
        }

        /**
         * For Android 4.1
         */
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            Log.i(UP_FILE_TAG, "in openFile Uri Callback has accept Type" + acceptType + "has capture" + capture);
            openFileChoose(uploadMsg, acceptType);
        }

        //Android 5.0+
        @Override
        @SuppressLint("NewApi")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return true;
        }
    }

    /**
     * 这个在要用到打开文件时，需子View实现，打开文件选择器，
     */
    protected void openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType) {
    }

    protected abstract OnLoadUrl getOnLoadUrl();

    public interface OnLoadUrl {
        /**
         * @return true 表示已拦截
         */
        boolean onLoadUrl(String url);
    }
}
