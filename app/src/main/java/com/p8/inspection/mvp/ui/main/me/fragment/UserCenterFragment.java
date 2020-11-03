package com.p8.inspection.mvp.ui.main.me.fragment;

import android.graphics.Color;
import android.view.View;

import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;

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
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        setClickListener(R.id.civ_app_update, R.id.civ_modify_password, R.id.civ_message_center, R.id.civ_settings);
    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        if (hasTitleBar()) {
            mTitleBar.setTitle("个人中心");
            mTitleBar.setLeftDrawable(R.mipmap.nav_button_search_back);
            mTitleBar.setBackgroundColor(mContext.getResources().getColor(R.color.main_default_color));
            mTitleBar.getTitleView().setTextColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.civ_app_update:
                break;
            case R.id.civ_settings:
                break;
            case R.id.civ_message_center:
                break;
            case R.id.civ_modify_password:
                start(ResetPasswordFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_user_center;
    }
}

