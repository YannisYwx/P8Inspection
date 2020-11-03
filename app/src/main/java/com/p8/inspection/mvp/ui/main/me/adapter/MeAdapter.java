package com.p8.inspection.mvp.ui.main.me.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.data.bean.UserMenu;

/**
 * @author : WX.Y
 * date : 2020/10/27 14:17
 * description :
 */
public class MeAdapter extends BaseQuickAdapter<UserMenu, BaseViewHolder> {

    public MeAdapter() {
        super(R.layout.item_me, LocalDataManager.getUserMenus(UserMenu.UserType.LAND));
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserMenu item) {
        helper.setText(R.id.tv_label, item.getMenuLabel());
        helper.setImageResource(R.id.iv_icon, item.getIconRes());
    }

}

