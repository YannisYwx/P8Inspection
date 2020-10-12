package com.p8.inspection.data;

import com.p8.common.http.HttpResponse;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Province;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.bean.VCode;
import com.p8.inspection.data.db.DBHelper;
import com.p8.inspection.data.net.HttpHelper;
import com.p8.inspection.data.prefs.PreferencesHelper;

import io.reactivex.Observable;

/**
 * author : WX.Y
 * date : 2020/9/8 17:00
 * description :
 */
public class DataManager implements PreferencesHelper, DBHelper, HttpHelper {
    PreferencesHelper mPreferencesHelper;
    DBHelper mDBHelper;
    HttpHelper mHttpHelper;

    public DataManager(PreferencesHelper preferencesHelper, DBHelper dbHelper, HttpHelper httpHelper) {
        this.mPreferencesHelper = preferencesHelper;
        this.mDBHelper = dbHelper;
        this.mHttpHelper = httpHelper;
    }

    @Override
    public void insert() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void modify() {

    }

    @Override
    public void query() {

    }

    @Override
    public void saveToken(String token) {
        mPreferencesHelper.saveToken(token);
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public void saveUserId(String userId) {
        mPreferencesHelper.saveUserId(userId);
    }

    @Override
    public String getUserId() {
        return mPreferencesHelper.getUserId();
    }

    @Override
    public Observable<HttpResponse<LoginInfo>> doLogin(String username, String password) {
        return mHttpHelper.doLogin(username, password);
    }

    @Override
    public Observable<HttpResponse<VCode>> getVCode(String phoneNum) {
        return mHttpHelper.getVCode(phoneNum);
    }

    @Override
    public Observable<HttpResponse<String>> resetPassword(String phoneNum, String code, String password) {
        return mHttpHelper.resetPassword(phoneNum, code, password);
    }

    @Override
    public Observable<HttpResponse<Provinces>> getProvinces() {
        return mHttpHelper.getProvinces();
    }

    @Override
    public Observable<HttpResponse<Cities>> getCities(String provinceId) {
        return mHttpHelper.getCities(provinceId);
    }

    @Override
    public Observable<HttpResponse<Areas>> getAreas(String cityId) {
        return mHttpHelper.getAreas(cityId);
    }

    @Override
    public Observable<HttpResponse<Streets>> getStreets(String provinces) {
        return mHttpHelper.getStreets(provinces);
    }

    @Override
    public Observable<HttpResponse<Machines>> getMachines(String address, int parkingStatus, int currentPage) {
        return mHttpHelper.getMachines(address, parkingStatus, currentPage);
    }
}