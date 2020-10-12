package com.p8.inspection.data.prefs;

/**
 * author : WX.Y
 * date : 2020/9/8 16:50
 * description :
 */
public interface PreferencesHelper {

    void saveToken(String token);

    String getToken();

    void saveUserId(String userId);

    String getUserId();
}
