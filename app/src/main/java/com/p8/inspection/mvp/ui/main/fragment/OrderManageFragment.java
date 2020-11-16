package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.bean.Order;
import com.p8.inspection.data.bean.Orders;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.OrderManageContract;
import com.p8.inspection.mvp.presenter.OrderManagePresenter;
import com.p8.inspection.mvp.ui.main.adapter.OrderInfoAdapter;
import com.p8.inspection.utils.RecycleViewDivider;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/11 10:02
 * description :订单管理
 */
public class OrderManageFragment extends DaggerMvpFragment<OrderManagePresenter, OrderManageContract.View> implements OrderManageContract.View,
        BaseQuickAdapter.OnItemClickListener {

    public static OrderManageFragment newInstance() {
        return new OrderManageFragment();
    }

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private OrderInfoAdapter mAdapter;
    private List<Order> mOrderList;

    private int currentPage = 0;

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
        mOrderList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        RecycleViewDivider divider = new RecycleViewDivider(mContext, RecyclerView.VERTICAL, AdaptScreenUtils.pt2Px(20), getResources().getColor(R.color.window_bg));
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new OrderInfoAdapter(mOrderList);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mAdapter.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        currentPage = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemClickListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            presenter.requestOrderList(currentPage, Constants.PAGE_SIZE);
        });
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            presenter.requestOrderList(currentPage, Constants.PAGE_SIZE);
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_order_manage;
    }

    @Override
    public int setTitle() {
        return R.string.title_order_manage;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        start(OrderInfoFragment.newInstance(mAdapter.getData().get(position)));
    }

    @Override
    public void onRequestOrdersSuccess(Orders orders) {
        if (orders.getList().size() > 0) {
            if (currentPage == 1) {
                mOrderList.clear();
            }
            int startRefreshIndex = mOrderList.size();
            mOrderList.addAll(orders.getList());
            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeChanged(startRefreshIndex, mOrderList.size() - startRefreshIndex);
            mSmartRefreshLayout.setEnableLoadMore(mOrderList.size() < orders.getTotal());
        } else {
            mAdapter.setEmptyView(com.p8.common.R.layout.pager_empty, (ViewGroup) mRecyclerView.getParent());
        }
        if (mSmartRefreshLayout.isRefreshing()) {
            mSmartRefreshLayout.finishRefresh();
        }
        if (mSmartRefreshLayout.isLoading()) {
            mSmartRefreshLayout.finishLoadMore();
        }
    }

}

