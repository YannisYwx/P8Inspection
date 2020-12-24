package com.p8.inspection.data.net;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.p8.common.http.HttpResponse;
import com.p8.inspection.data.bean.Agency;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.Inspection;
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Orders;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.bean.VCode;
import com.p8.inspection.data.net.api.P8Api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * @author : WX.Y
 * date : 2020/9/8 16:09
 * description :
 */
public class RetrofitHelper implements HttpHelper {

    P8Api mApi;

    @Inject
    public RetrofitHelper(P8Api api) {
        this.mApi = api;
    }


    private static RequestBody createJsonRequestBody(@NonNull Object obj) {
        Logger.d("");
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(obj));
    }

    private static String createJsonStr(@NonNull Object obj) {
        String json = null;
        try {
            json = URLEncoder.encode(new Gson().toJson(obj), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override
    public Observable<HttpResponse<LoginInfo>> doLoginByLandlord(String username, String password) {
        return mApi.doLoginByLandlord(username, password);
    }

    @Override
    public Observable<HttpResponse<LoginInfo>> doLoginByLargeMaster(String loginName, String password) {
        return mApi.doLoginByLargeMaster(loginName, password);
    }

    @Override
    public Observable<HttpResponse<Agency>> getAgencyInfo() {
        return mApi.getAgencyInfo();
    }

    @Override
    public Observable<HttpResponse<Inspection>> getInspectInfo() {
        return mApi.getInspectInfo();
    }

    @Override
    public Observable<HttpResponse<Landlords>> getLandlords(int currentPage, int pageSize) {
        return mApi.getLandlords(currentPage, pageSize);
    }

    @Override
    public Observable<HttpResponse<String>> addLandlord(String phone, String realName) {
        return mApi.addLandlord(phone, realName);
    }

    @Override
    public Observable<HttpResponse<Orders>> getOrders(int currentPage, int pageSize) {
        return mApi.getOrders(currentPage, pageSize);
    }

    @Override
    public Observable<HttpResponse<VCode>> getVCode(String phoneNum) {
        return mApi.getCode(phoneNum);
    }

    @Override
    public Observable<HttpResponse<String>> resetPassword(String url, String newPassword, String oldPassword) {
        return mApi.resetPassword(url, newPassword, oldPassword);
    }

    @Override
    public Observable<HttpResponse<Provinces>> getProvinces() {
        return mApi.getProvinces();
    }

    @Override
    public Observable<HttpResponse<Cities>> getCities(String provinceId) {
        return mApi.getCites(provinceId);
    }

    @Override
    public Observable<HttpResponse<Areas>> getAreas(String cityId) {
        return mApi.getAreas(cityId);
    }

    @Override
    public Observable<HttpResponse<Streets>> getStreets(String address) {
        return mApi.getStreets(address);
    }

    @Override
    public Observable<HttpResponse<Machines>> getMachines(String address, String parkingStatus, int currentPage) {
        return mApi.getMachines(address, parkingStatus, currentPage);
    }

    @Override
    public Observable<HttpResponse<Object>> bindDevice(String address, String parkingNumber, String lat, String lng) {
        return mApi.bindDevice(address, parkingNumber, lat, lng);
    }

}

