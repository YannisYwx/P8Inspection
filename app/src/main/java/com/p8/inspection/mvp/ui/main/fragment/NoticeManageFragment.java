package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.p8.common.dialog.IDialog;
import com.p8.common.widget.swipe.SwipeRevealLayout;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Notice;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.NoticeManageContract;
import com.p8.inspection.mvp.presenter.NoticeManagerPresenter;
import com.p8.inspection.mvp.ui.main.adapter.LandlordManageAdapter;
import com.p8.inspection.mvp.ui.main.adapter.NoticeAdapter;
import com.p8.inspection.utils.RecycleViewDivider;
import com.p8.inspection.widget.DialogUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/11 10:02
 * description :公告管理
 */
public class NoticeManageFragment extends DaggerMvpFragment<NoticeManagerPresenter, NoticeManageContract.View> implements NoticeManageContract.View,
        BaseQuickAdapter.OnItemChildClickListener {

    public static NoticeManageFragment newInstance() {
        return new NoticeManageFragment();
    }

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private NoticeAdapter mAdapter;
    private List<Notice> mNotices;


    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv);
        mSmartRefreshLayout = $(R.id.srl);
    }

    @Override
    public void initData() {
        mNotices = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        RecycleViewDivider divider = new RecycleViewDivider(mContext, RecyclerView.VERTICAL, AdaptScreenUtils.pt2Px(30), getResources().getColor(R.color.window_bg));
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new NoticeAdapter(mNotices);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableAutoLoadMore(true);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> new Handler().postDelayed(() -> {
            mNotices.add(new Notice());
            mNotices.add(new Notice());
            mNotices.add(new Notice());
            mNotices.add(new Notice());
            mNotices.add(new Notice());
            mNotices.add(new Notice());
            mNotices.add(new Notice());
            mNotices.add(new Notice());
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
    public void onResume() {
        super.onResume();
        mSmartRefreshLayout.autoRefresh();
    }

    private void refresh() {
        mNotices.clear();
        mNotices.add(new Notice());
        mNotices.add(new Notice());
        mNotices.add(new Notice());
        mNotices.add(new Notice());
        mNotices.add(new Notice());
        mNotices.add(new Notice());
        mSmartRefreshLayout.setEnableLoadMore(mNotices.size() == 6);
        mAdapter.notifyDataSetChanged();

//        mAdapter.setEmptyView(com.p8.common.R.layout.pager_empty, (ViewGroup) mRecyclerView.getParent());

    }

    @Override
    public void setListener() {
        mAdapter.setOnItemChildClickListener(this);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_srl_rv;
    }

    @Override
    public int setTitle() {
        return R.string.title_notice_manage;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_more) {
            ToastUtils.showShort("没有更多 - -！");
        }
    }

}

