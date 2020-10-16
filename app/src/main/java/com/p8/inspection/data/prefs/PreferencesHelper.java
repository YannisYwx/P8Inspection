package com.p8.inspection.data.prefs;

/**
 * @author : WX.Y
 * date : 2020/9/8 16:50
 * description :
 */
public interface PreferencesHelper {

    /**
     * 保存token
     *
     * @param token
     */
    void saveToken(String token);

    /**
     * 获取当前保存的token
     *
     * @return
     */
    String getToken();

    /**
     * 保存用户id
     *
     * @param userId
     */
    void saveUserId(String userId);

    /**
     * 获取用户id
     *
     * @return
     */
    String getUserId();
}
