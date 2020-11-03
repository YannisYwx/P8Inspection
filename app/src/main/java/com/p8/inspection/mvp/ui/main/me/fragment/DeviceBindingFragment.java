package com.p8.inspection.mvp.ui.main.me.fragment;

import android.graphics.Color;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.p8.common.widget.TitleBar;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.data.bean.ProvinceBean;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.DeviceBindingContract;
import com.p8.inspection.mvp.presenter.DeviceBindingPresenter;
import com.p8.inspection.utils.LocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/29 10:24
 * description :
 */
public class DeviceBindingFragment extends DaggerMvpFragment<DeviceBindingPresenter, DeviceBindingContract.View>
        implements DeviceBindingContract.View, LocationManager.OnLocationListener {

    private EditText etNumber, etStreet, etPs, etAffiliation;
    private TextView tvAddress;

    public static DeviceBindingFragment newInstance() {
        return new DeviceBindingFragment();
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
        mTitleBar.setRightText("保存");
        etNumber = $(R.id.et_device_number);
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

    private void startLocation() {
        LocationManager.getInstance().setLocationListener(this);
        LocationManager.getInstance().startLocation();
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            String opt1tx = LocalDataManager.getInstance().getProvinces().size() > 0 ?
                    LocalDataManager.getInstance().getProvinces().get(options1).getPickerViewText() : "";

            String opt2tx = LocalDataManager.getInstance().getCities().size() > 0
                    && LocalDataManager.getInstance().getCities().get(options1).size() > 0 ?
                    LocalDataManager.getInstance().getCities().get(options1).get(options2) : "";

            String opt3tx = LocalDataManager.getInstance().getCities().size() > 0
                    && LocalDataManager.getInstance().getAreas().get(options1).size() > 0
                    && LocalDataManager.getInstance().getAreas().get(options1).get(options2).size() > 0 ?
                    LocalDataManager.getInstance().getAreas().get(options1).get(options2).get(options3) : "";

            String tx = opt1tx + opt2tx + opt3tx;
            tvAddress.setText(tx);
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //三级选择器
        pvOptions.setPicker(LocalDataManager.getInstance().getProvinces(), LocalDataManager.getInstance().getCities(), LocalDataManager.getInstance().getAreas());
        pvOptions.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocationManager.getInstance().stopLocation();
    }

    @Override
    public void setListener() {
        setClickListener(R.id.tv_address);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tvAddress) {
            try {
                showPickerView();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onEventTrigger(int type) {
        super.onEventTrigger(type);
        if (type == TitleBar.Event.TV_RIGHT) {
            presenter.bindDevice(deviceAddress,"0755123456",lat,lng);
        }
    }

    @Override
    protected void refreshContentView(View view) {

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

    String lng, lat;
    String deviceAddress;

    @Override
    public void onLocationError(int errorCode, String errorInfo) {

    }
}

