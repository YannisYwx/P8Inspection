package com.p8.inspection.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.PermissionUtils;
import com.p8.common.widget.MultiFunEditText;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMVPFragment;
import com.p8.inspection.data.net.download.DownloadInfo;
import com.p8.inspection.data.net.download.DownloadListener;
import com.p8.inspection.data.net.download.DownloadManager;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.LoginContract;
import com.p8.inspection.mvp.presenter.LoginPresenter;

/**
 * author : WX.Y
 * date : 2020/9/16 17:06
 * description :
 */
public class TestFragment extends DaggerMVPFragment<LoginPresenter, LoginContract.View> implements LoginContract.View {

    public static TestFragment newInstance() {
        Bundle args = new Bundle();
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void initData() {
        DownloadManager.getInstance().addDownloadListener(new DownloadListener() {
            @Override
            public void onStartDownload(String tag) {
                showMsg("开始下载" + tag);
            }

            @Override
            public void onProgress(String tag, int progress) {
                showMsg("下载进度 = " + progress);
            }

            @Override
            public void onFinishDownload(String tag, DownloadInfo downloadInfo) {
                showMsg("下载完成 = " + downloadInfo.getDefaultLocalPath());
            }

            @Override
            public void onFail(String tag, String msg) {

            }
        });
    }

    @Override
    public void setListener() {
        $(R.id.btn_login).setOnClickListener(this);
        $(R.id.btn_province).setOnClickListener(this);
        $(R.id.btn_download).setOnClickListener(this);
        $(R.id.btn_pdf).setOnClickListener(this);
    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    public void onLoginSuccess() {
        showMsg("登录成功");
    }

    @Override
    public void onLoginError(String errorMsg) {
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_login) {
            presenter.doLogin("wzh", "123456");
        }

        if (v.getId() == R.id.btn_province) {
            start(MonitorFragment.getInstance());
        }

        if (v.getId() == R.id.btn_download) {
//            start(ResetPwdFragment.newInstance());
//            presenter.getProvince();
            DownloadManager.getInstance().start("https://p8bucket.oss-cn-shenzhen.aliyuncs.com/J1-0.pdf");
        }

        if (v.getId() == R.id.btn_pdf) {
            start(PdfPreviewFragment.getInstance("J1-2.pdf"));
        }

        PermissionUtils.permission().request();
    }

}

