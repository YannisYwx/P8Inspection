package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.LocalDataManager;

/**
 * @author : WX.Y
 * date : 2020/10/27 17:34
 * description : 用户中心
 */
public class UserCenterFragment extends BaseStatusPagerFragment {

    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        int type = LocalDataManager.getInstance().getUserType();
        switch (type) {
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.LAND:
                break;
            case Constants.UserType.LARGE:
                break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            case Constants.UserType.SMALL:
                break;
            default:
                break;
        }
    }

    @Override
    public void setListener() {
        bindClickListener(R.id.civ_app_update, R.id.civ_modify_password, R.id.civ_clear_cache);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.civ_app_update:
                break;
            case R.id.civ_clear_cache:
                break;
            case R.id.civ_modify_password:
                start(ResetPasswordFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_user_center;
    }

    @Override
    public int setTitle() {
        return R.string.title_user_center;
    }
}

