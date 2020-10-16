package com.p8.inspection.data.prefs;

import com.blankj.utilcode.util.SPUtils;

import javax.inject.Inject;

/**
 * @author : WX.Y
 * date : 2020/9/8 16:51
 * description :
 */
public class AppPreferencesHelper implements PreferencesHelper {
    public static final String SP_NAME = "P8_APP";

    public static final String KEY_TOKEN = "_KEY_TOKEN";
    public static final String KEY_USER_ID = "_KEY_KEY_USER_ID";

    @Inject
    public AppPreferencesHelper() {

    }

    @Override
    public void saveToken(String token) {
        getSPUtils().put(KEY_TOKEN, token);
    }

    @Override
    public String getToken() {
        return getSPUtils().getString(KEY_TOKEN,"");
    }

    @Override
    public void saveUserId(String userId) {
        getSPUtils().put(KEY_USER_ID, userId);
    }

    @Override
    public String getUserId() {
        return getSPUtils().getString(KEY_USER_ID,"");
    }

    public static SPUtils getSPUtils(){
        return SPUtils.getInstance(SP_NAME);
    }
}

