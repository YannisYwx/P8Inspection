package com.p8.inspection.mvp.ui.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.bean.Machine;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Street;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.MonitorContract;
import com.p8.inspection.mvp.presenter.MonitorPresenter;
import com.p8.inspection.mvp.ui.main.adapter.BerthAdapter;
import com.p8.inspection.utils.CarItemDecoration;
import com.p8.inspection.widget.cityselect.CitySelectView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static com.p8.inspection.data.Constants.PAGE_SIZE;
import static com.p8.inspection.widget.SelectCityView.CAR_TYPE_VIEW;
import static com.p8.inspection.widget.SelectCityView.LOCATION_VIEW;

/**
 * @author : WX.Y
 * date : 2020/9/27 16:39
 * description : 停车监控
 */
public class ParkingMonitorFragment extends DaggerMvpFragment<MonitorPresenter, MonitorContract.View> implements MonitorContract.View,
        CitySelectView.OnSelectListener, OnRefreshListener, OnLoadMoreListener {

    public static ParkingMonitorFragment newInstance() {
        return new ParkingMonitorFragment();
    }

    CitySelectView mSelectCityView;

    View vLocation;
    View vCarType;
    TextView tvLocation, tvCarType;

    BerthAdapter mAdapter;

    SmartRefreshLayout refreshLayout;

    RecyclerView rvBerth;

    List<Machine> mMachines;

    List<String> mStreets;

    private int currentPage = 0;

    private String selectProvince, selectCity, selectArea, selectStreet, selectCarType, address;

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        try {
            mSelectCityView = $(R.id.scv_location_car);
            vLocation = $(R.id.iv_arrow_location);
            vCarType = $(R.id.iv_arrow_car);
            tvLocation = $(R.id.tv_location);
            tvCarType = $(R.id.tv_car_type);
            refreshLayout = $(R.id.srl_berth);
            rvBerth = $(R.id.rv_berth);
            setStatusBarLightMode(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if (mSelectCityView.getVisibility() == View.GONE) {
            mSelectCityView.setViewGone();
            mSelectCityView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int setTitle() {
        return R.string.title_parking_monitor;
    }

    @Override
    public void initData() {
        mSelectCityView.setArrowViews(vLocation, vCarType);
        BarUtils.setStatusBarColor(_mActivity, Color.argb(0, 0, 0, 0));
        mMachines = new ArrayList<>();
        mStreets = new ArrayList<>();
        mAdapter = new BerthAdapter(mMachines);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 2);
        gridLayoutManager.offsetChildrenVertical(AdaptScreenUtils.pt2Px(200));
        rvBerth.setLayoutManager(gridLayoutManager);
        rvBerth.addItemDecoration(new CarItemDecoration(2));
        rvBerth.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableClipFooterWhenFixedBehind(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public void setListener() {
        vLocation.setOnClickListener(this);
        vCarType.setOnClickListener(this);
        mSelectCityView.setOnSelectListener(this);
        tvLocation.setOnClickListener(this);
        tvCarType.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_location:
            case R.id.iv_arrow_location:
                mSelectCityView.openOrClose(LOCATION_VIEW);
                break;
            case R.id.tv_car_type:
            case R.id.iv_arrow_car:
                mSelectCityView.openOrClose(CAR_TYPE_VIEW);
                break;
            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_parking_mointor;
    }

    @Override
    public void onGetStreetsSuccess(Streets streets) {
        Logger.e("街道数据总数：" + streets.getList().size());
        mStreets.clear();
        if (streets.getList().size() > 0) {
            for (Street street : streets.getList()) {
                mStreets.add(street.getAddress());
            }
        }
        mSelectCityView.fillStreetData(mStreets);
    }

    @Override
    public void onGetLocationFail(String errorMsg, int type, String id) {

    }

    @Override
    public void onGetMachines(Machines machines) {

        if (currentPage != 1) {
            int startPosition = mMachines.size() - 1;
            mMachines.addAll(machines.getList());
            mAdapter.notifyItemRangeChanged(startPosition, mMachines.size());
            if (machines.getList().size() < PAGE_SIZE) {
                mAdapter.loadMoreEnd();
                refreshLayout.setNoMoreData(true);
            }
        } else {
            mMachines.clear();
            mMachines.addAll(machines.getList());
            mAdapter.notifyDataSetChanged();
            refreshLayout.setEnableLoadMore(machines.getTotal() >= PAGE_SIZE);
            mAdapter.setEmptyView(R.layout.pager_empty, rvBerth);
        }

        refreshLayout.setNoMoreData(machines.getTotal() == mMachines.size());


        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
        if (refreshLayout.isLoading()) {
            refreshLayout.finishLoadMore();
        }
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShort("请选择监控地点");
            refreshLayout.finishRefresh();
            return;
        }
        if (selectCarType.equals(Constants.DeviceStatus.NOT_CHOICE)) {
            ToastUtils.showShort("请选择泊位状态");
            refreshLayout.finishRefresh();
            return;
        }
        currentPage = 1;
        requestMachines(address, selectCarType, currentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//        if(mMachines.size() == 11) {
//            mAdapter.loadMoreEnd();
//        }
        currentPage++;
        requestMachines(address, selectCarType, currentPage);

    }


    /**
     * 根据地址请求设备信息列表
     *
     * @param address
     * @param parkingState
     * @param currentPage
     */
    private void requestMachines(String address, String parkingState, int currentPage) {
        presenter.getMachines(address, parkingState, currentPage);
    }

    @Override
    public void onSelect(String province, String city, String area, String street, String carType) {
        tvLocation.setText(street);
        selectProvince = province;
        selectCity = city;
        selectArea = area;
        selectStreet = street;
        selectCarType = carType;
        currentPage = 1;
        if (!TextUtils.isEmpty(selectStreet) && !selectCarType.equals(Constants.DeviceStatus.NOT_CHOICE)) {
            address = selectProvince + selectCity + selectArea + selectStreet;
            Logger.e("查询设备 ：address = " + address + " , carType = " + carType);
            presenter.getMachines(address, selectCarType, 1);
        }
    }

    @Override
    public void requestStreet(String province, String city, String area) {
        Logger.e("requestStreet---------------------------------------requestStreet");
        String address = province + city + area;
        Logger.e("查询街道 ：address = " + address);
        presenter.getStreets(address);
    }

    @Override
    public void onCarTypeSelect(String carType) {
        tvCarType.setText(carType == null ? "全部" : (carType.equals(Constants.DeviceStatus.HAVE_CAR) ? "有车" : "无车"));
    }

}

