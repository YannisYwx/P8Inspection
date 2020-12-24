package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ReflectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.king.zxing.CaptureHelper;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;
import com.king.zxing.camera.FrontLightMode;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;
import com.p8.inspection.data.Constants;

/**
 * @author : WX.Y
 * date : 2020/11/16 16:37
 * description : 扫码二维码界面
 */
public class ScanQRCodeFragment extends BaseStatusPagerFragment implements OnCaptureCallback {

    public static ScanQRCodeFragment newInstance() {
        return new ScanQRCodeFragment();
    }

    private CaptureHelper mCaptureHelper;

    private SurfaceView surfaceView;

    private ViewfinderView viewfinderView;

    private View ivTorch;

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

        surfaceView = $(R.id.surfaceView);
        viewfinderView = $(R.id.viewfinderView);
        ivTorch = $(R.id.ivFlash);
        ivTorch.setVisibility(View.INVISIBLE);

        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, ivTorch);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
        mCaptureHelper.vibrate(true)
                //全屏扫码
                .fullScreenScan(true)
                //支持扫垂直条码，建议有此需求时才使用。
                .supportVerticalCode(true)
                //是否支持识别反色码（黑白反色的码），增加识别率
                .supportLuminanceInvert(true)
                .continuousScan(true)
                //震动
                .vibrate(true)
                //设置闪光灯模式
                .frontLightMode(FrontLightMode.AUTO)
                //设置光线太暗时，自动触发开启闪光灯的照度值
                .tooDarkLux(45f)
                //设置光线足够明亮时，自动触发关闭闪光灯的照度值
                .brightEnoughLux(100f)
                .continuousScan(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCaptureHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
    }

    /**
     * 扫码结果回调
     *
     * @param result 扫码结果
     * @return
     */
    @Override
    public boolean onResultCallback(String result) {
        if (RegexUtils.isMatch("[0-9]{10}", result)) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.Key.SCAN_QE_CODE_RESULT, result);
            setFragmentResult(RESULT_OK, bundle);
            pop();
        } else {
            ToastUtils.showShort("该二维码不符合车位号规范,请重新扫描正确的二维码");
            mCaptureHelper.restartPreviewAndDecode();
        }

        return true;
    }

    @Override
    public void initData() {

        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, ivTorch);
        mCaptureHelper.setOnCaptureCallback(this);
        mCaptureHelper.onCreate();
        mCaptureHelper.vibrate(true)
                //全屏扫码
                .fullScreenScan(true)
                //支持扫垂直条码，建议有此需求时才使用。
                .supportVerticalCode(true)
                //是否支持识别反色码（黑白反色的码），增加识别率
                .supportLuminanceInvert(true)
                .continuousScan(false);
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
        return R.layout.fragment_scan_qr_code;
    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }

    @Override
    public int setTitle() {
        return R.string.title_scan_qr_code;
    }
}

