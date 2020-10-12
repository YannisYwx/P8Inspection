package com.p8.inspection.mvp.contract;

import com.p8.common.base.mvp.BaseContract;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.City;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;

/**
 * author : WX.Y
 * date : 2020/9/7 16:32
 * description :
 */
public interface MonitorContract {

    interface View extends BaseContract.IBaseView {

        void onGetProvincesSuccess(Provinces provinces);

        void onGetCitiesSuccess(Cities cities);

        void onGetAreasSuccess(Areas areas);

        void onGetStreetsSuccess(Streets streets);

        void onGetLocationFail(String errorMsg, int type, String id);

        void onGetMachines(Machines machines);

    }

    interface Presenter extends BaseContract.IBasePresenter<View> {

        void getProvinces();

        void getCities(String id);

        void getAreas(String id);

        void getStreets(String id);

        void getMachines(String address,int parkingState,int currentPage);
    }
}

