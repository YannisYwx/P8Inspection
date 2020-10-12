package com.p8.inspection.data.prefs;

import com.p8.common.utils.SPUtils;

import javax.inject.Inject;

/**
 * author : WX.Y
 * date : 2020/9/8 16:51
 * description :
 */
public class AppPreferencesHelper implements PreferencesHelper {

    public static final String KEY_TOKEN = "_KEY_TOKEN";
    public static final String KEY_USER_ID = "_KEY_KEY_USER_ID";

    @Inject
    public AppPreferencesHelper() {

    }

    @Override
    public void saveToken(String token) {
        SPUtils.getInstance().putData(KEY_TOKEN, token);
    }

    @Override
    public String getToken() {
        return (String) SPUtils.getInstance().getData(KEY_TOKEN,"");
    }

    @Override
    public void saveUserId(String userId) {
        SPUtils.getInstance().putData(KEY_USER_ID, userId);
    }

    @Override
    public String getUserId() {
        return (String) SPUtils.getInstance().getData(KEY_USER_ID,"");
    }
}

