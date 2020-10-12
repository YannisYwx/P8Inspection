package com.p8.inspection.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.p8.common.recycler.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.p8.common.recycler.groupedadapter.holder.BaseViewHolder;
import com.p8.inspection.R;

import java.util.List;

/**
 * author : WX.Y
 * date : 2020/9/27 14:11
 * description :
 */
public abstract class NameAdapter<T> extends GroupedRecyclerViewAdapter {
    List<T> dataList;
    private int selectIndex = -1;
    private Context context;
    public NameAdapter(Context context,List<T> dataList) {
        super(context);
        this.context = context;
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return 0;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_address;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        TextView tv = holder.get(R.id.tv);
        if (dataList != null) {
            setText(dataList.get(childPosition), tv, childPosition);
        }

        if (selectIndex == childPosition) {
            tv.setTextColor(holder.itemView.getResources().getColor(R.color.main_default_color));
        } else {
            tv.setTextColor(holder.itemView.getResources().getColor(R.color.main_first_level_text_color));
        }
    }

    public abstract void setText(T data, TextView textView, int position);

}

