package com.p8.inspection.data.net.api;

import com.p8.common.http.HttpResponse;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.LoginResponse;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.data.bean.VCode;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * author : WX.Y
 * date : 2020/9/8 16:09
 * description :
 * * ---------------------------------------------------------------------------------------------------
 * * @GET 表明这是get请求
 * * @POST 表明这是post请求
 * * @PUT 表明这是put请求
 * * @DELETE 表明这是delete请求
 * * @PATCH 表明这是一个patch请求，该请求是对put请求的补充，用于更新局部资源
 * * @HEAD 表明这是一个head请求
 * * @OPTIONS 表明这是一个option请求
 * * @HTTP 通用注解, 可以替换以上所有的注解，其拥有三个属性：method，path，hasBody
 * * @Headers 用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在
 * * @Header 作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头
 * * @Body 多用于post请求发送非表单数据, 比如想要以post方式传递json格式数据
 * * @Filed 多用于post请求中表单字段, Filed和FieldMap需要FormUrlEncoded结合使用
 * * @FiledMap 和@Filed作用一致，用于不确定表单参数
 * * @Part 用于表单字段, Part和PartMap与Multipart注解结合使用, 适合文件上传的情况
 * * @PartMap 用于表单字段, 默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传
 * * <p>
 * * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * * 而Filed则相对简单些，通常是字符串键值对。
 * * </p>
 * * Part标志上文的内容可以是富媒体形势，比如上传一张图片，上传一段音乐，即它多用于字节流传输。
 * * 而Filed则相对简单些，通常是字符串键值对。
 * * @Path 用于url中的占位符,{占位符}和PATH只用在URL的path部分，url中的参数使用Query和QueryMap代替，保证接口定义的简洁
 * * @Query 用于Get中指定参数
 * * @QueryMap 和Query使用类似
 * * @Url 指定请求路径
 * * ----------------------------------------------------------------------------------------------------------
 */
public interface P8Api {

    String HOST = "http://service.p8.world";

    /**
     * 登录
     *
     * @param loginName
     * @param password
     * @return
     */
    @POST("/app/inspect/login.html")
    Observable<HttpResponse<LoginInfo>> doLogin(@Query("loginName") String loginName, @Query("password") String password);

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    @GET("/app/inspect/getCode.html")
    Observable<HttpResponse<VCode>> getCode(@Query("mobile") String phone);

    /**
     * 重置密码
     *
     * @param phone
     * @param code
     * @param password
     * @return
     */
    @POST("/app/inspect/forgetPwd.html")
    Observable<HttpResponse<String>> resetPassword(@Query("mobile") String phone, @Query("code") String code, @Query("password") String password);

    /**
     * 获取省列表
     *
     * @return
     */
    @GET("/app/provinces/getProvinces.html")
    Observable<HttpResponse<Provinces>> getProvinces();

    /**
     * 获取城市列表
     *
     * @return
     */
    @GET("/app/provinces/getCities.html")
    Observable<HttpResponse<Cities>> getCites(@Query("provinceId") String provinceId);

    /**
     * 获取区列表
     *
     * @return
     */
    @GET("/app/provinces/getAreas.html")
    Observable<HttpResponse<Areas>> getAreas(@Query("cityId") String cityId);

    /**
     * 获取街道列表
     *
     * @return
     */
    @GET("/app/provinces/getStreet.html")
    Observable<HttpResponse<Streets>> getStreets(@Query("provinces") String provinces);

    /**
     * 获取设备列表
     *
     * @return
     */
    @GET("/app/provinces/getMachine.html")
    Observable<HttpResponse<Machines>> getMachines(@Query("address") String address, @Query("parkingStatus") int parkingStatus, @Query("currentPage") int currentPage);


    public static final String GET_PROVINCES = "/app/provinces/getProvinces.html";//获取省
    public static final String GET_CITIES = "/app/provinces/getCities.html";//获取市
    public static final String GET_AREAS = "/app/provinces/getAreas.html";//获取区
    public static final String GET_STREET = "/app/provinces/getStreet.html";//获取街道
    public static final String GET_MACHINE = "/app/provinces/getMachine.html";//获取设备列表

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

    @GET("/parking/app/order/by_number.html")
    Observable<HttpResponse<LoginResponse>> getOrderNumber(@Query("token") String token, @Query("parkingNumber") String parkingNumber);

    //    @POST("/parking/alipay/pay.html")//ali支付
    @POST("/wx/alipay/pay_data.html")
    //ali支付
    Observable<HttpResponse<LoginResponse>> alipay(@Query("serialNumber") String serialNumber, @Query("money") String money, @Query("ref") String ref);

    // @POST("/parking/wechatpay/pay.html")//wechat支付
    @POST("/wx/wechatPay/pay_data.html")
    Observable<HttpResponse<LoginResponse>> wechatPay(@Query("serialNumber") String serialNumber, @Query("money") String money, @Query("ref") String ref);

}
