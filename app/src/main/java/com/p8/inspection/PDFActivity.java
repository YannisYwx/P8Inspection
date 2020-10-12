package com.p8.inspection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.WebView;

//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.PDFView;
import com.p8.common.base.BaseActivity;

import java.io.File;

/**
 * author : WX.Y
 * date : 2020/9/15 15:32
 * description :
 */
public class PDFActivity extends BaseActivity {

    WebView mWebView;
//    PDFView pdfView;

    @Override
    public void initData() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pdf;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView(View view) {
//        mWebView = $(R.id.wv_pdf);
////        val webSettings = mBinding.webView.settings
////        webSettings.javaScriptEnabled = true
////        webSettings.allowFileAccess = true
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////            webSettings.allowFileAccessFromFileURLs = true
////            webSettings.allowUniversalAccessFromFileURLs = true
////        }
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setAllowFileAccess(true);
//        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
//        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
//        String path = "file:///android_asset/pdf_p.pdf";
//        mWebView.loadUrl("file:///android_asset/show_pdf.html?file=" + new File(path).toURI().toString());

//        pdfView = (PDFView) findViewById(R.id.wv_pdf);
//        pdfView.fromAsset("pdf_p.pdf").defaultPage(0)
//                .enableAnnotationRendering(true)
//                .swipeHorizontal(false)
//                .spacing(10)
//                .load();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }
}

