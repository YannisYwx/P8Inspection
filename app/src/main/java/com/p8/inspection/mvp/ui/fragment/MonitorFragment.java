package com.p8.inspection.mvp.ui.fragment;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMVPFragment;
import com.p8.inspection.data.bean.Area;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.City;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Page;
import com.p8.inspection.data.bean.Province;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Street;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.MonitorContract;
import com.p8.inspection.mvp.presenter.MonitorPresenter;
import com.p8.inspection.mvp.ui.adapter.BerthMonitorAdapter;
import com.p8.inspection.utils.CarItemDecoration;
import com.p8.inspection.widget.SelectCityView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import static com.p8.inspection.widget.SelectCityView.CAR_TYPE_VIEW;
import static com.p8.inspection.widget.SelectCityView.LOCATION_VIEW;

/**
 * author : WX.Y
 * date : 2020/9/27 16:39
 * description :
 */
public class MonitorFragment extends DaggerMVPFragment<MonitorPresenter, MonitorContract.View> implements MonitorContract.View,
        SelectCityView.OnItemSelectListener, OnRefreshListener, OnLoadMoreListener {

    public static MonitorFragment getInstance() {
        return new MonitorFragment();
    }

    SelectCityView mSelectCityView;

    View vLocation;
    View vCarType;
    TextView tvLocation, tvCarType;

    BerthMonitorAdapter mAdapter;

    SmartRefreshLayout refreshLayout;

    RecyclerView rvBerth;

    Machines mMachines;

    private int currentPage = 0;

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
        mSelectCityView = $(R.id.scv_location_car);
        vLocation = $(R.id.iv_arrow_location);
        vCarType = $(R.id.iv_arrow_car);
        tvLocation = $(R.id.tv_location);
        tvCarType = $(R.id.tv_car_type);
        refreshLayout = $(R.id.srl_berth);
        rvBerth = $(R.id.rv_berth);
        setStatusBarLightMode(false);
    }

    @Override
    public void initData() {
        mSelectCityView.setArrowViews(vLocation, vCarType);
        BarUtils.setStatusBarColor(_mActivity, Color.argb(0, 0, 0, 0));
        presenter.getProvinces();
        mTitleBar.setTitle("停车监控");
//        ConstraintLayout.LayoutParams params= (ConstraintLayout.LayoutParams) mSelectCityView.getLayoutParams();
//        params.topToBottom = R.id.tv_location;
//        params.leftToLeft = R.id.root;
//        params.rightToRight = R.id.root;
//        params.bottomToBottom = R.id.root;
        mAdapter = new BerthMonitorAdapter(_mActivity, null);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, 2);
//        gridLayoutManager.offsetChildrenVertical(AdaptScreenUtils.pt2Px(200));
        rvBerth.setLayoutManager(gridLayoutManager);
        rvBerth.addItemDecoration(new CarItemDecoration(2));
        rvBerth.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableClipFooterWhenFixedBehind(false);
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setEnableLoadMore(false);
    }

    @Override
    public void setListener() {
        vLocation.setOnClickListener(this);
        vCarType.setOnClickListener(this);
        mSelectCityView.setOnItemSelectListener(this);
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
        }
    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mointor;
    }

    @Override
    public void onGetProvincesSuccess(Provinces provinces) {
        mSelectCityView.initProvincesList(provinces);
    }

    @Override
    public void onGetCitiesSuccess(Cities cities) {
        mSelectCityView.initCitiesList(cities);
    }

    @Override
    public void onGetAreasSuccess(Areas areas) {
        mSelectCityView.initAreasList(areas);
    }

    @Override
    public void onGetStreetsSuccess(Streets streets) {
        mSelectCityView.initStreetsList(streets);
    }

    @Override
    public void onGetLocationFail(String errorMsg, int type, String id) {

    }

    @Override
    public void onGetMachines(Machines machines) {
        if (currentPage != 1) {
            Machines moreMachines = mAdapter.getMachines();
            Page page = machines.getPage();
            moreMachines.setPage(page);
            moreMachines.getMachineList().addAll(machines.getMachineList());
            mAdapter.setMachines(moreMachines);
            mAdapter.notifyItemRangeChanged(moreMachines.getMachineList().size() - 1, machines.getMachineList().size());
        } else {
            mAdapter.setMachines(machines);
            mAdapter.notifyDataSetChanged();
            mSelectCityView.resetCarType();
        }
        if(refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onItemSelect(int code, String id) {
        if (code == SelectCityView.PROVINCE) {
            presenter.getCities(id);
        } else if (code == SelectCityView.CITY) {
            presenter.getAreas(id);
        } else if (code == SelectCityView.AREA) {
            presenter.getStreets(id);
        }

    }

    @Override
    public void onLocationSelect(Province province, City city, Area area, Street street) {
        if (!TextUtils.isEmpty(mSelectCityView.getAddress())) {
            tvLocation.setText(mSelectCityView.getAddress());
        }
        if(mSelectCityView.getCarType() != -1) {
            tvCarType.setText(mSelectCityView.getCarTypeStateStr());
        }
        if (TextUtils.isEmpty(mSelectCityView.getAddress()) || mSelectCityView.getCarType() == -1) {
            return;
        }
        currentPage = 1;
        requestMachines(mSelectCityView.getAddress(), mSelectCityView.getCarType(), currentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mSelectCityView.getCurrentStreet() == null && TextUtils.isEmpty(mSelectCityView.getAddress())) {
            refreshLayout.finishRefresh();
            return;
        }
        currentPage = 1;
        requestMachines(mSelectCityView.getAddress(), mSelectCityView.getCarTypeSelectedPosition(), currentPage);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mAdapter.getMachines() == null) {
            return;
        }
        Page page = mAdapter.getMachines().getPage();
        if (page.getPageNum() + 1 <= page.getPageCount()) {
            currentPage = page.getPageNum() + 1;
            requestMachines(mSelectCityView.getAddress(), mSelectCityView.getCarTypeSelectedPosition(), currentPage);
        }
    }


    //根据地址请求设备信息列表
    private void requestMachines(String address, int parkingState, int currentPage) {
        if (mAdapter != null && currentPage <= 1) {//>1时表示为加载更多，加载更多时原数据不能移除
            mAdapter.setMachines(null);
            mAdapter.notifyDataSetChanged();
        }
        presenter.getMachines(address, parkingState, currentPage);
    }
}

