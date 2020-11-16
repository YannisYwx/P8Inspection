package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.data.bean.Order;
import com.p8.inspection.data.bean.Orders;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:38
 * description : 订单管理业务
 */
public interface OrderManageContract {

    interface View extends BaseContract.IBaseView {

        /**
         * 请求订单列表成功
         *
         * @param orders 地主列表
         */
        void onRequestOrdersSuccess(Orders orders);

    }

    interface Presenter extends BaseContract.IBasePresenter<OrderManageContract.View> {

        /**
         * 请求订单列表
         *
         * @param currentPage 当前的页数 currentPage(当前页) pageSize(一页几条)
         * @param pageSize    一页几条
         */
        void requestOrderList(int currentPage, int pageSize);
    }
}

