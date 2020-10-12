package com.p8.inspection.data.net;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.p8.common.http.HttpResponse;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.Machines;
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
 * author : WX.Y
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
    public Observable<HttpResponse<LoginInfo>> doLogin(String username, String password) {
        return mApi.doLogin(username, password);
    }

    @Override
    public Observable<HttpResponse<VCode>> getVCode(String phoneNum) {
        return mApi.getCode(phoneNum);
    }

    @Override
    public Observable<HttpResponse<String>> resetPassword(String phoneNum, String code, String password) {
        return mApi.resetPassword(phoneNum, code, password);
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
    public Observable<HttpResponse<Streets>> getStreets(String provinces) {
        return mApi.getStreets(provinces);
    }

    @Override
    public Observable<HttpResponse<Machines>> getMachines(String address, int currentPage, int parkingStatus) {
        return mApi.getMachines(address, currentPage, parkingStatus);
    }

}

