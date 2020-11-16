package com.p8.inspection.mvp.presenter;

import com.p8.common.base.mvp.BasePresenter;
import com.p8.inspection.data.DataManager;
import com.p8.inspection.mvp.contract.NoticeManageContract;
import com.p8.inspection.mvp.contract.SubmitOrderContract;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/10/12 15:41
 * description : 公告管理业务
 */
public class NoticeManagerPresenter extends BasePresenter<NoticeManageContract.View> implements NoticeManageContract.Presenter {
    DataManager mDataManager;

    @Inject
    public NoticeManagerPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void requestNoticeList(int currentPage) {

    }
}

