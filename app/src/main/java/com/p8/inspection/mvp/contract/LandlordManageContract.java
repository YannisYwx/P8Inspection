package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Landlords;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:38
 * description : 提交工单业务
 */
public interface LandlordManageContract {

    interface View extends BaseContract.IBaseView {

        /**
         * 请求地主列表成功
         *
         * @param landlords 地主列表
         */
        void onRequestLandlordSuccess(Landlords landlords);

        /**
         * 添加地主成功
         * @param message 服务返回消息
         */
        void onAddLandlordSuccess(String message);

    }

    interface Presenter extends BaseContract.IBasePresenter<LandlordManageContract.View> {

        /**
         * 请求地主列表
         *
         * @param currentPage 当前的页数 currentPage(当前页) pageSize(一页几条)
         * @param pageSize    一页几条
         */
        void requestLandlordList(int currentPage, int pageSize);

        /**
         * 删除地主
         *
         * @param landlordId 需要删除的地主id
         */
        void deleteLandlord(int landlordId);

        /**
         * 修改地主
         *
         * @param landlord
         */
        void modifyLandlord(Landlord landlord);

        /**
         * 添加地主
         *
         * @param landlord
         */
        void addLandlord(Landlord landlord);
    }
}

