package com.p8.inspection.mvp.ui.main.adapter;

import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.data.bean.UserMenu;

/**
 * @author : WX.Y
 * date : 2020/10/27 14:17
 * description : 主页面菜单
 */
public class MenuAdapter extends BaseQuickAdapter<UserMenu, BaseViewHolder> {

    public MenuAdapter() {
        super(R.layout.item_me, LocalDataManager.getInstance().getUserMenus());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, UserMenu item) {
        helper.setText(R.id.tv_label, item.getMenuLabel());
        helper.setImageResource(R.id.iv_icon, item.getIconRes());
        helper.getView(R.id.v_bottom_line).setVisibility(helper.getLayoutPosition() == getData().size() ? View.GONE : View.VISIBLE);
    }

}

