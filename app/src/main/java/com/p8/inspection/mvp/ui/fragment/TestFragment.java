package com.p8.inspection.mvp.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.net.download.DownloadInfo;
import com.p8.inspection.data.net.download.DownloadListener;
import com.p8.inspection.data.net.download.DownloadManager;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.LoginContract;
import com.p8.inspection.mvp.presenter.LoginPresenter;
import com.p8.inspection.mvp.ui.entry.fragment.EntryPagerFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.ParkingMonitorFragment;

/**
 * @author : WX.Y
 * date : 2020/9/16 17:06
 * description :
 */
public class TestFragment extends DaggerMvpFragment<LoginPresenter, LoginContract.View> implements LoginContract.View {

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
        mTitleBar.setTitle("测试");
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
//                showMsg("下载进度 = " + progress);
            }

            @Override
            public void onFinishDownload(String tag, DownloadInfo downloadInfo) {
                showMsg("下载完成 = " + downloadInfo.getDefaultLocalPath());
            }

            @Override
            public void onFail(String tag, String msg) {
                showMsg(tag + " = 下载失败 = " + msg);
            }
        });
    }

    @Override
    public void setListener() {
        $(R.id.btn_login).setOnClickListener(this);
        $(R.id.btn_province).setOnClickListener(this);
        $(R.id.btn_download).setOnClickListener(this);
        $(R.id.btn_pdf).setOnClickListener(this);
        $(R.id.btn_me).setOnClickListener(this);
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
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.btn_login) {
            presenter.doLogin("wzh", "123456");
        }

        if (v.getId() == R.id.btn_province) {
            start(ParkingMonitorFragment.newInstance());
        }

        if (v.getId() == R.id.btn_download) {
            boolean isStorageGranted = PermissionUtils.isGranted(PermissionConstants.getPermissions(PermissionConstants.STORAGE));
            if(!isStorageGranted) {
                PermissionUtils.permission(PermissionConstants.getPermissions(PermissionConstants.STORAGE)).callback(new PermissionUtils.SimpleCallback() {
                    @Override
                    public void onGranted() {
                        showMsg("权限通过了");
                        DownloadManager.getInstance().start("https://p8bucket.oss-cn-shenzhen.aliyuncs.com/J1-0.pdf");
                    }

                    @Override
                    public void onDenied() {
                        showMsg("权限没通过");
                    }
                }).request();
            } else {
                DownloadManager.getInstance().start("https://p8bucket.oss-cn-shenzhen.aliyuncs.com/J1-0.pdf");
            }

        }

        if (v.getId() == R.id.btn_pdf) {
            start(PdfPreviewFragment.getInstance("/storage/emulated/0/Android/data/com.p8.inspection/files/p8_inspection/download/pdf/J1-0.pdf"));
//            showHideFragment(this, PdfPreviewFragment.getInstance("J1-2.pdf"));
        }

        if (v.getId() == R.id.btn_me) {
//            start(MeRVFragment.newInstance());
            start(EntryPagerFragment.newInstance());
        }

//        PermissionUtils.permission().request();
    }

}

