package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.common.utils.DataCleanManager;
import com.p8.common.widget.CommonItemView;
import com.p8.inspection.R;
import com.p8.inspection.widget.DialogUtils;

import static com.p8.common.utils.DataCleanManager.CACHE_CLEAR;

/**
 * @author : WX.Y
 * date : 2020/10/27 17:34
 * description : 用户中心
 */
public class UserCenterFragment extends BaseStatusPagerFragment {

    CommonItemView civClearCache;
    String cacheSize = null;

    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        civClearCache = $(R.id.civ_clear_cache);
    }

    private void initAppCacheView() {
        cacheSize = DataCleanManager.getTotalCacheSize(this.mContext);
        civClearCache.setTextRight(DataCleanManager.getTotalCacheSize(this.mContext),
                cacheSize.equals(CACHE_CLEAR) ? getResources().getColor(R.color.line_gray) : getResources().getColor(R.color.orangeRed));
    }

    @Override
    public void initData() {
        initAppCacheView();
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
                if (cacheSize.equals(CACHE_CLEAR)) {
                    ToastUtils.showShort("缓存已清除");
                } else {
                    DialogUtils.showClearCacheDialog(mContext, dialog -> {
                        DataCleanManager.clearAllCache(mContext);
                        dialog.dismiss();
                        ToastUtils.showShort("App缓存已清理");
                        initAppCacheView();
                    });
                }
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

