package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.p8.common.widget.TitleBar;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.bean.LordDevice;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.AttendanceContract;
import com.p8.inspection.mvp.presenter.AttendancePresenter;
import com.p8.inspection.mvp.ui.main.adapter.LordDeviceAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/26 15:57
 * description : 搜索大主
 */
public class SearchLordFragment extends DaggerMvpFragment<AttendancePresenter, AttendanceContract.View> implements AttendanceContract.View {

    private RecyclerView mRecyclerView;
    private LordDeviceAdapter mAdapter;
    private List<LordDevice> mLordDevices;
    private EditText etLordName;
    private Button btnSearch;
    private SmartRefreshLayout mSmartRefreshLayout;

    public static SearchLordFragment newInstance() {
        return new SearchLordFragment();
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_lord);
        mSmartRefreshLayout = $(R.id.srl_lord);
        etLordName = $(R.id.et_lord_name);
        btnSearch = $(R.id.btn_search);
        mTitleBar.setRightImageViewRes(R.mipmap.add);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public int setTitle() {
        return R.string.title_search;
    }

    @Override
    public void onTitleBarRightClick() {
        start(DeviceBindingFragment.newInstance());
    }

    @Override
    public void initData() {
        mLordDevices = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new LordDeviceAdapter(mLordDevices);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setEmptyView(com.p8.common.R.layout.pager_empty, (ViewGroup) mRecyclerView.getParent());
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mAdapter.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(true);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> new Handler().postDelayed(() -> {
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mLordDevices.add(new LordDevice("深圳张三", "0755123456", "2020-10-31\n14:30"));
            mAdapter.notifyDataSetChanged();
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.setNoMoreData(true);
        }, 1000));

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> new Handler().postDelayed(() -> {
            refresh();
            mSmartRefreshLayout.finishRefresh();
        }, 1000));
    }

    private void refresh() {
        mLordDevices.clear();
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mLordDevices.add(new LordDevice("深圳李四", "0755123456", "2020-10-31\n14:30"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_search_lord;
    }

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public void onClockInSuccess() {

    }

    @Override
    public void onClockOutSuccess() {

    }

    @Override
    public void onClockFailed() {

    }
}

