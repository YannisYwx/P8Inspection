package com.p8.inspection;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.orhanobut.logger.Logger;
import com.p8.common.base.BaseActivity;
import com.p8.inspection.mvp.ui.EnterActivity;
import com.p8.inspection.utils.LocationManager;
import com.p8.inspection.widget.DialogUtils;

public class MainActivity extends BaseActivity {
    TextView tvInfo;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        tvInfo = findViewById(R.id.tv_info);
        LocationManager.getInstance().initLocation(this);
        LocationManager.getInstance().setLocationListener(new LocationManager.OnLocationListener() {
            @Override
            public void onLocationSuccess(AMapLocation location, String locationInfo) {
                Logger.d(locationInfo);
                tvInfo.setText(locationInfo);
            }

            @Override
            public void onLocationError(int errorCode, String errorInfo) {
                Logger.e(errorInfo);
                tvInfo.setText(errorInfo);
            }
        });
    }

    @Override
    public void setListener() {
        findViewById(R.id.btn_toLogin).setOnClickListener(this);
        findViewById(R.id.btn_startLocation).setOnClickListener(this);
        findViewById(R.id.btn_stopLocation).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_toLogin:
//                DialogUtils.createProgressDialog(this,"登录中");
                startActivity(EnterActivity.class);
//                DialogUtils.createDefaultDialog(this, "分享", "分享此接单码，您和乘客都将获得现金红包！",
//                        "立即分享", new IDialog.OnClickListener() {
//                            @Override
//                            public void onClick(IDialog dialog) {
//                                Toast.makeText(MainActivity.this, "立即分享", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        },
//                        "有钱不要", new IDialog.OnClickListener() {
//                            @Override
//                            public void onClick(IDialog dialog) {
//                                Toast.makeText(MainActivity.this, "有钱不要", Toast.LENGTH_SHORT).show();
//                                dialog.dismiss();
//                            }
//                        });
                break;
            case R.id.btn_stopLocation:
                DialogUtils.closeLoadingDialog(this);
//                LocationManager.getInstance().stopLocation();
                break;
            case R.id.btn_startLocation:
                LocationManager.getInstance().startLocation();
                break;
        }

    }

    @Override
    public void doBusiness(Context mContext) {

    }


    @Override
    public boolean hasTitleBar() {
        return false;
    }

    @Override
    public void initData() {

    }
}