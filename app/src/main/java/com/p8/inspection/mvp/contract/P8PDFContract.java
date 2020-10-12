package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;

import java.io.File;

/**
 * author : WX.Y
 * date : 2020/9/24 11:11
 * description :
 */
public interface P8PDFContract {

    interface View extends BaseContract.IBaseView {
        void onDownloadSuccess(String fileName, File file);
    }

    interface Presenter extends BaseContract.IBasePresenter<P8PDFContract.View> {
        void downloadPdf(String url);
    }
}

