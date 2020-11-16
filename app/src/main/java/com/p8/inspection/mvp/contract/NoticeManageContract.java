package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:38
 * description : 公告管理业务
 */
public interface NoticeManageContract {

    interface View extends BaseContract.IBaseView {

    }

    interface Presenter extends BaseContract.IBasePresenter<NoticeManageContract.View> {

        /**
         * 请求公告数据列表
         *
         * @param currentPage
         */
        void requestNoticeList(int currentPage);
    }
}

