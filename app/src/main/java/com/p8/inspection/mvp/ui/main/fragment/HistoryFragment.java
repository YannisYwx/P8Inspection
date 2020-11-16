package com.p8.inspection.mvp.ui.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.bean.ClockRecord;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.HistoryContract;
import com.p8.inspection.mvp.presenter.HistoryPresenter;
import com.p8.inspection.mvp.ui.main.adapter.ClockRecordAdapter;
import com.p8.inspection.widget.wheelview.popupwindow.DatePickerPopWin;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/28 11:30
 * description :
 */
public class HistoryFragment extends DaggerMvpFragment<HistoryPresenter, HistoryContract.View> implements HistoryContract.View {

    private RecyclerView mRecyclerView;
    private ClockRecordAdapter mAdapter;
    private List<ClockRecord> mClockRecords;
    private TextView tvDate;
    private Button btnSearch;
    private SmartRefreshLayout mSmartRefreshLayout;
    DatePickerPopWin birthdayPopWin;

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }


    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_history);
        mSmartRefreshLayout = $(R.id.srl_clock);
        tvDate = $(R.id.tv_date);
        tvDate.setText("2020-10");
    }

    private void refresh() {
        mClockRecords.clear();
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mClockRecords.add(new ClockRecord("9:00", "18:30", "10-21"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initData() {
        mClockRecords = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new ClockRecordAdapter(mClockRecords);
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
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mClockRecords.add(new ClockRecord("XXXX", "XXXX:XXXX", "XXXX-XXXX"));
            mAdapter.notifyDataSetChanged();
            mSmartRefreshLayout.finishLoadMore();
            mSmartRefreshLayout.setNoMoreData(true);
        }, 1000));

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> new Handler().postDelayed(() -> {
            refresh();
            mSmartRefreshLayout.finishRefresh();
        }, 1000));
    }

    @Override
    public void loadData() {
        super.loadData();
        birthdayPopWin = new DatePickerPopWin.Builder(mContext,
                (year, month, day, dateDesc) -> tvDate.setText(dateDesc))
                .textConfirm("确定")
                .textCancel("取消")
                .btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .minYear(1950)
                .maxYear(2050)
                .dateChose(tvDate.getText() + "-1")
                .build();
    }

    @Override
    public void setListener() {
        tvDate.setOnClickListener(this);
    }

    @Override
    public void onClick(int viewId) {
        birthdayPopWin.setSelectedDate(tvDate.getText() + "-1");
        birthdayPopWin.showPopWin(_mActivity);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onClockInRecordGetSuccess() {

    }

    @Override
    public int setTitle() {
        return R.string.title_attendance_history;
    }

    @Override
    public boolean hasTitleBar() {
        return super.hasTitleBar();
    }
}

