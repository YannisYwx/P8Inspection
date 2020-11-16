package com.p8.inspection.data;

import com.p8.common.http.HttpResponse;
import com.p8.inspection.data.bean.Agency;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Orders;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.bean.VCode;
import com.p8.inspection.data.db.DBHelper;
import com.p8.inspection.data.net.HttpHelper;
import com.p8.inspection.data.prefs.PreferencesHelper;

import io.reactivex.Observable;

/**
 * @author : WX.Y
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
    public Observable<HttpResponse<LoginInfo>> doLoginByLandlord(String username, String password) {
        return mHttpHelper.doLoginByLandlord(username, password);
    }

    @Override
    public Observable<HttpResponse<LoginInfo>> doLoginByLargeMaster(String loginName, String password) {
        return mHttpHelper.doLoginByLargeMaster(loginName, password);
    }

    @Override
    public Observable<HttpResponse<Agency>> getAgencyInfo() {
        return mHttpHelper.getAgencyInfo();
    }

    @Override
    public Observable<HttpResponse<Landlords>> getLandlords(int currentPage, int pageSize) {
        return mHttpHelper.getLandlords(currentPage, pageSize);
    }

    @Override
    public Observable<HttpResponse<Orders>> getOrders(int currentPage, int pageSize) {
        return mHttpHelper.getOrders(currentPage, pageSize);
    }

    @Override
    public Observable<HttpResponse<VCode>> getVCode(String phoneNum) {
        return mHttpHelper.getVCode(phoneNum);
    }

    @Override
    public Observable<HttpResponse<String>> resetPassword(String url, String newPassword, String oldPassword) {
        return mHttpHelper.resetPassword(url, newPassword, oldPassword);
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
    public Observable<HttpResponse<Streets>> getStreets(String address) {
        return mHttpHelper.getStreets(address);
    }

    @Override
    public Observable<HttpResponse<Machines>> getMachines(String address, String parkingStatus, int currentPage) {
        return mHttpHelper.getMachines(address, parkingStatus, currentPage);
    }

    @Override
    public Observable<HttpResponse<Object>> bindDevice(String address, String parkingNumber, String lat, String lng) {
        return mHttpHelper.bindDevice(address, parkingNumber, lat, lng);
    }
}