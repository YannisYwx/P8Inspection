package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
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
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.LandlordManageContract;
import com.p8.inspection.mvp.presenter.LandlordManagePresenter;
import com.p8.inspection.mvp.ui.main.adapter.LandlordManageAdapter;
import com.p8.inspection.utils.RecycleViewDivider;
import com.p8.inspection.widget.DialogUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/11 10:02
 * description :地主管理
 */
public class LandlordManageFragment extends DaggerMvpFragment<LandlordManagePresenter, LandlordManageContract.View> implements LandlordManageContract.View,
        BaseQuickAdapter.OnItemChildClickListener {

    public static LandlordManageFragment newInstance() {
        return new LandlordManageFragment();
    }

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private LandlordManageAdapter mAdapter;
    private List<Landlord> mLandlords;

    private int currentPage = 0;

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv);
        mSmartRefreshLayout = $(R.id.srl);
        mTitleBar.setRightImageViewRes(R.mipmap.add);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSmartRefreshLayout.autoRefresh();
    }

    @Override
    public void onTitleBarRightClick() {
        start(LandlordInfoFragment.newInstance(null));
    }

    @Override
    public void initData() {
        mLandlords = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        RecycleViewDivider divider = new RecycleViewDivider(mContext, RecyclerView.VERTICAL, AdaptScreenUtils.pt2Px(3), getResources().getColor(R.color.window_bg));
        divider.setPadding(AdaptScreenUtils.pt2Px(60));
        mRecyclerView.addItemDecoration(divider);
        mAdapter = new LandlordManageAdapter(mLandlords);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.openLoadAnimation();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mSmartRefreshLayout.setEnableLoadMore(false);
        currentPage = 1;
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemChildClickListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            currentPage++;
            presenter.requestLandlordList(currentPage, Constants.PAGE_SIZE);
        });
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            presenter.requestLandlordList(currentPage, Constants.PAGE_SIZE);
        });
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_srl_rv;
    }

    @Override
    public int setTitle() {
        return R.string.title_landlord_manage;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.tv_delete) {
            DialogUtils.createDefaultDialog(mContext, "警告", "是否删除该地主", "再想想", IDialog::dismiss, "确定", dialog -> {
                mLandlords.remove(position);
                mAdapter.notifyItemRemoved(position);
                dialog.dismiss();
            });
        }
        if (view.getId() == R.id.content_layout) {
            Landlord landlord = mAdapter.getData().get(position);
            if (landlord.isOpen) {
                SwipeRevealLayout layout = (SwipeRevealLayout) view.getParent();
                layout.close(true);
            } else {
                start(LandlordInfoFragment.newInstance(landlord));
            }
        }
    }

    @Override
    public void onRequestLandlordSuccess(Landlords landlords) {
        if (landlords.getList().size() > 0) {
            if (currentPage == 1) {
                mLandlords.clear();
            }
            int startRefreshIndex = mLandlords.size();
            mLandlords.addAll(landlords.getList());
            mAdapter.notifyDataSetChanged();
            mAdapter.notifyItemRangeChanged(startRefreshIndex, mLandlords.size() - startRefreshIndex);
            mSmartRefreshLayout.setEnableLoadMore(mLandlords.size() < landlords.getTotal());
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

    @Override
    public void onAddLandlordSuccess(String message) {
        ToastUtils.showShort(message);
    }
}

