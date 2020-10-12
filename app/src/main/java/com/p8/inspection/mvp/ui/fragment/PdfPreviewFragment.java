package com.p8.inspection.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.blankj.utilcode.util.BarUtils;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;

/**
 * author : WX.Y
 * date : 2020/10/10 14:03
 * description :
 */
public class PdfPreviewFragment extends BaseStatusPagerFragment {

    WebView mWebView;
    private String pdfUrl;
    public static final String PDF_URL = "_PDF_URL";

    public static PdfPreviewFragment getInstance(String pdfUrl) {
        Bundle args = new Bundle();
        args.putString(PDF_URL, pdfUrl);
        PdfPreviewFragment fragment = new PdfPreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView(View view) {
        mWebView = view.findViewById(R.id.wv_pdf);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDisplayZoomControls(true);
        //设置true,才能让WebView支持<meta>标签的viewport属性
        webSettings.setUseWideViewPort(true);
        //设置可以支持缩放
        webSettings.setSupportZoom(true);
        //设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //设定缩放控件隐藏
        webSettings.setDisplayZoomControls(true);
        BarUtils.setStatusBarColor(_mActivity, Color.argb(0, 0, 0, 0));
        mTitleBar.setTitle("pdf预览");
        setStatusBarLightMode(false);
    }

    @Override
    public void initData() {
        Bundle args = getArguments();
        if (args != null) {
            pdfUrl = args.getString(PDF_URL);
            if (!TextUtils.isEmpty(pdfUrl)) {
                mWebView.loadUrl("file:///android_asset/index.html?" + pdfUrl);
            }
        }
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        if (hasTitleBar()) {
            mTitleBar.setLeftDrawable(R.mipmap.nav_button_search_back);
            mTitleBar.setBackgroundColor(mContext.getResources().getColor(R.color.main_default_color));
            mTitleBar.getTitleView().setTextColor(Color.WHITE);
        }
    }

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public void setListener() {

    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_ptf_preview;
    }
}

