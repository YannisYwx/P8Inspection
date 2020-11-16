package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.common.http.HttpResponse;
import com.p8.common.rx.RxUtils;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.data.bean.Orders;
import com.p8.inspection.data.net.observer.P8HttpSubscriber;
import com.p8.inspection.mvp.contract.LandlordManageContract;
import com.p8.inspection.mvp.contract.OrderManageContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description : 订单管理业务
 */
public class OrderManagePresenter extends BasePresenter<OrderManageContract.View> implements OrderManageContract.Presenter {
    DataManager mDataManager;

    @Inject
    public OrderManagePresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void requestOrderList(int currentPage, int pageSize) {
        mDataManager.getOrders(currentPage, pageSize).compose(RxUtils.getDefaultOSchedulers())
                .as(bindLifecycle()).subscribe(new P8HttpSubscriber<HttpResponse<Orders>>(mView) {
            @Override
            protected void onSuccess(HttpResponse<Orders> ordersHttpResponse) {
                if (ordersHttpResponse.getData() != null) {
                    mView.onRequestOrdersSuccess(ordersHttpResponse.getData());
                }
            }
        });
    }
}

