package com.p8.inspection.data.net;

import com.p8.common.http.HttpResponse;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.LoginResponse;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Province;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.bean.VCode;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author : WX.Y
 * date : 2020/9/8 16:05
 * description :
 */
public interface HttpHelper {

    /**
     * 账号登录
     *
     * @param username
     * @param password
     * @return
     */
    Observable<HttpResponse<LoginInfo>> doLogin(String username, String password);

    /**
     * 获取验证码
     *
     * @param phoneNum 手机号
     * @return
     */
    Observable<HttpResponse<VCode>> getVCode(String phoneNum);

    /**
     * 重置密码
     *
     * @param phoneNum 手机号
     * @param code     验证码
     * @param password 密码
     * @return
     */
    Observable<HttpResponse<String>> resetPassword(String phoneNum, String code, String password);

    /**
     * 获取省会列表
     *
     * @return
     */
    Observable<HttpResponse<Provinces>> getProvinces();

    /**
     * 获取城市列表
     *
     * @return
     */
    Observable<HttpResponse<Cities>> getCities(String provinceId);


    /**
     * 获取区列表
     *
     * @return
     */
    Observable<HttpResponse<Areas>> getAreas(String cityId);

    /**
     * 获取街道列表
     *
     * @return
     */
    Observable<HttpResponse<Streets>> getStreets(String provinces);

    /**
     * 获取设备列表
     *
     * @return
     */
    Observable<HttpResponse<Machines>> getMachines(String address, int parkingStatus, int currentPage);

}
