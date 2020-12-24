package com.p8.inspection.mvp.ui.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.p8.common.widget.TitleBar;
import com.p8.common.widget.pickerview.builder.OptionsPickerBuilder;
import com.p8.common.widget.pickerview.view.OptionsPickerView;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.DeviceBindingContract;
import com.p8.inspection.mvp.presenter.DeviceBindingPresenter;
import com.p8.inspection.utils.LocationManager;

/**
 * @author : WX.Y
 * date : 2020/10/29 10:24
 * description :
 */
public class DeviceBindingFragment extends DaggerMvpFragment<DeviceBindingPresenter, DeviceBindingContract.View>
        implements DeviceBindingContract.View, LocationManager.OnLocationListener {

    public static final int SCAN_PARKING_NUMBER = 0X123;

    private EditText etNumber, etStreet, etPs, etAffiliation;
    private TextView tvAddress;
    private String currentProvince = "", currentCity = "", currentArea = "";
    private String lng, lat;
    private String deviceAddress, parkingNumber, name;

    public static DeviceBindingFragment newInstance() {
        return new DeviceBindingFragment();
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mTitleBar.setRightText("绑定");
        etNumber = $(R.id.et_parking_number);
        tvAddress = $(R.id.tv_address);
        etStreet = $(R.id.et_street);
        etAffiliation = $(R.id.et_affiliation);
        etPs = $(R.id.et_ps);

    }

    @Override
    public void initData() {
        boolean isLocationGranted = PermissionUtils.isGranted(PermissionConstants.getPermissions(PermissionConstants.LOCATION));
        if (!isLocationGranted) {
            PermissionUtils.permission(PermissionConstants.getPermissions(PermissionConstants.LOCATION)).callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    showMsg("权限通过了");
                    startLocation();
                }

                @Override
                public void onDenied() {
                    showMsg("权限没通过");
                }
            }).request();
        } else {
            startLocation();
        }
    }

    private void startScanQRCode() {
        boolean isCameraGranted = PermissionUtils.isGranted(PermissionConstants.getPermissions(PermissionConstants.CAMERA));
        if (!isCameraGranted) {
            PermissionUtils.permission(PermissionConstants.getPermissions(PermissionConstants.CAMERA)).callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    startForResult(ScanQRCodeFragment.newInstance(), SCAN_PARKING_NUMBER);
                }

                @Override
                public void onDenied() {
                    showMsg("权限没通过");
                }
            }).request();
        } else {
            startForResult(ScanQRCodeFragment.newInstance(), SCAN_PARKING_NUMBER);
        }
    }

    private void startLocation() {
        LocationManager.getInstance().registerLocationListener(this);
        LocationManager.getInstance().startLocation();
    }

    private void showPickerView() {// 弹出选择器
        int[] selectIndex = LocalDataManager.getInstance().getSelectAddressIndex(currentProvince, currentCity, currentArea);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            currentProvince = LocalDataManager.getInstance().getProvinces().size() > 0 ?
                    LocalDataManager.getInstance().getProvinces().get(options1).getPickerViewText() : "";

            currentCity = LocalDataManager.getInstance().getCities().size() > 0
                    && LocalDataManager.getInstance().getCities().get(options1).size() > 0 ?
                    LocalDataManager.getInstance().getCities().get(options1).get(options2) : "";

            currentArea = LocalDataManager.getInstance().getCities().size() > 0
                    && LocalDataManager.getInstance().getAreas().get(options1).size() > 0
                    && LocalDataManager.getInstance().getAreas().get(options1).get(options2).size() > 0 ?
                    LocalDataManager.getInstance().getAreas().get(options1).get(options2).get(options3) : "";

            StringBuilder stringBuilder = new StringBuilder(currentProvince);
            if (!currentProvince.equals(currentCity)) {
                stringBuilder.append(currentCity);
            }

            if (!currentCity.equals(currentArea)) {
                stringBuilder.append(currentArea);
            }

            tvAddress.setText(stringBuilder.toString());
        })
                .setTitleText("城市选择")
                .setTitleColor(getResources().getColor(R.color.text_black))
                .setDividerColor(Color.parseColor("#F4F4F6"))
                .setTextColorCenter(getResources().getColor(R.color.colorPrimary))
                .setSelectTextBackgroundColor(Color.parseColor("#F4F4F6"))
                .setContentTextSize(20)
                .setSelectOptions(selectIndex[0], selectIndex[1], selectIndex[2])
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //三级选择器
        pvOptions.setPicker(LocalDataManager.getInstance().getProvinces(),
                LocalDataManager.getInstance().getCities(),
                LocalDataManager.getInstance().getAreas());
        pvOptions.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocationManager.getInstance().stopLocation();
        LocationManager.getInstance().unregisterLocationListener(this);
    }

    @Override
    public void setListener() {
        bindClickListener(R.id.tv_address, R.id.iv_scan);
    }

    @Override
    public void onClick(int viewId) {
        super.onClick(viewId);
        if (viewId == R.id.tv_address) {
            showPickerView();
        } else if (viewId == R.id.iv_scan) {
            startScanQRCode();
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == SCAN_PARKING_NUMBER && resultCode == RESULT_OK) {
            String parkingNumber = data.getString(Constants.Key.SCAN_QE_CODE_RESULT);
            etNumber.setText(parkingNumber);
        }
    }

    @Override
    public void onTitleBarRightClick() {
        super.onTitleBarRightClick();
        parkingNumber = etNumber.getText().toString();
        deviceAddress = etStreet.getText().toString();
        name = etAffiliation.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort("大主名称不能为空");
        } else if (TextUtils.isEmpty(parkingNumber)) {
            ToastUtils.showShort("车位号不能为空，");
        } else if (RegexUtils.isMatch("[0-9]{10}", parkingNumber)) {
            ToastUtils.showShort("车位号格式不正确（十位有效数字），");
        } else if (TextUtils.isEmpty(deviceAddress)) {
            ToastUtils.showShort("街道地址不能为空");
        } else {
            presenter.bindDevice(deviceAddress, parkingNumber, lat, lng);
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_device_binding;
    }


    @Override
    public int setTitle() {
        return R.string.title_device_binding;
    }

    @Override
    public void onLocationSuccess(AMapLocation location, String locationInfo) {
        String province = location.getProvince();
        String city = location.getCity();
        String district = location.getDistrict();
        String address = province + city + district;
        tvAddress.setText(address);
        etStreet.setText(location.getAddress().replace(address, ""));
        //经度
        double longitude = location.getLongitude();
        //维度
        double latitude = location.getLatitude();
        lat = String.valueOf(latitude);
        lng = String.valueOf(longitude);
        deviceAddress = location.getAddress();
        LocationManager.getInstance().stopLocation();
    }

    @Override
    public void onLocationError(int errorCode, String errorInfo) {

    }
}

