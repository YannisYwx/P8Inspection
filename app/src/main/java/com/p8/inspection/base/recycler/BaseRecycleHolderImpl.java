package com.p8.inspection.base.recycler;

/**
 * @author : WX.Y
 * createTime : 2017/11/2  16:22
 * description :
 */
public interface BaseRecycleHolderImpl<T> {
    /**
     * 刷新数据接口
     * @param data 数据
     */
    void setDataAndRefreshHolderView(T data);
}
