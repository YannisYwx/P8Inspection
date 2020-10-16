package com.p8.inspection.mvp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.p8.inspection.R;
import com.p8.inspection.base.recycler.BaseRecyclerAdapter;
import com.p8.inspection.mvp.ui.holder.MeHeaderHolder;
import com.p8.inspection.mvp.ui.holder.MeInfoItemHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/15 17:40
 * description :
 */
public class MeAdapter extends BaseRecyclerAdapter<MeAdapter.ItemBean> {


    public MeAdapter(List<ItemBean> dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected MeInfoItemHolder getCommonHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me, parent, false);
        return new MeInfoItemHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_me_header, parent, false);
        return new MeHeaderHolder(view);
    }

    @Override
    protected int initOtherItemCount() {
        return 1;
    }

    @Override
    protected boolean hasLoadMore() {
        return true;
    }

    @Override
    protected List<ItemBean> onLoadMoreData() {
        return null;
    }


    public static class ItemBean {
        public int iconRes;
        public String label;

        public ItemBean(int iconRes, String label) {
            this.iconRes = iconRes;
            this.label = label;
        }

    }

    private List<ItemBean> mBeans = new ArrayList<>();

    public static List<ItemBean> initData() {
        List<ItemBean> mBeans = new ArrayList<>();
        mBeans.clear();
        mBeans.add(new ItemBean(R.mipmap.icon_phone,"停车监控"));
        mBeans.add(new ItemBean(R.mipmap.icon_phone,"停车监控"));
        mBeans.add(new ItemBean(R.mipmap.icon_phone,"停车监控"));
        mBeans.add(new ItemBean(R.mipmap.icon_phone,"停车监控"));
        mBeans.add(new ItemBean(R.mipmap.icon_phone,"停车监控"));
        return mBeans;
    }
}

