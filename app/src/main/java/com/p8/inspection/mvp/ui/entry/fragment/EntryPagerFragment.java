package com.p8.inspection.mvp.ui.entry.fragment;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;
import com.p8.inspection.mvp.ui.entry.adapter.EnterTypeAdapter;
import com.p8.inspection.utils.GridPaddingDecoration;

/**
 * @author : WX.Y
 * date : 2020/10/20 17:11
 * description : 系统进入的界面
 */
public class EntryPagerFragment extends BaseStatusPagerFragment {

    RecyclerView mRecyclerView;

    public static EntryPagerFragment newInstance() {
        return new EntryPagerFragment();
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(R.id.rv_enter);
        mTitleBar.setVisibility(View.GONE);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        BarUtils.setStatusBarLightMode(_mActivity, false);
    }

    @Override
    public void initData() {
        EnterTypeAdapter adapter = new EnterTypeAdapter();
        View view = getLayoutInflater().inflate(R.layout.item_enter_header, (ViewGroup) mRecyclerView.getParent(), false);
        adapter.addHeaderView(view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new GridPaddingDecoration());
        adapter.setOnItemClickListener((adapter1, view1, position) -> {
            switch (position) {
                case 0:
                    break;
                case 1:
                    start(LoginFragment.newInstance());
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                default:
                    break;
            }
        });
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
        return R.layout.fragment_enter_pager;
    }

    @Override
    public boolean hasTitleBar() {
        return false;
    }
}

