package com.p8.inspection.mvp.ui.main.me.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/27 14:17
 * description :
 */
public class MeAdapter extends BaseQuickAdapter<MeAdapter.ItemBean, BaseViewHolder> {

    private List<ItemBean> mBeans = new ArrayList<>();

    public MeAdapter() {
        super(R.layout.item_me, getDefaultData());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ItemBean item) {
        helper.setText(R.id.tv_label, item.label);
        helper.setImageResource(R.id.iv_icon, item.iconRes);
    }

    public static List<ItemBean> getDefaultData() {
        List<ItemBean> mBeans = new ArrayList<>();
        mBeans.clear();
        mBeans.add(new ItemBean(R.mipmap.icon_user, "个人中心"));
        mBeans.add(new ItemBean(R.mipmap.icon_parking_moniter, "停车监控"));
        mBeans.add(new ItemBean(R.mipmap.icon_device_settings, "设备安装"));
        mBeans.add(new ItemBean(R.mipmap.icon_order, "工单处理"));
        mBeans.add(new ItemBean(R.mipmap.icon_clock, "签到签出"));
        return mBeans;
    }

    /**
     * 获取大主的菜单项
     *
     * @return
     */
    public static List<ItemBean> getGreatMaster() {
        List<ItemBean> mBeans = new ArrayList<>();
        mBeans.clear();
        mBeans.add(new ItemBean(R.mipmap.icon_user, "个人中心"));
        mBeans.add(new ItemBean(R.mipmap.icon_settings, "J架管理"));
        mBeans.add(new ItemBean(R.mipmap.icon_device_settings, "配件管理"));
        mBeans.add(new ItemBean(R.mipmap.icon_order_manage, "订单管理"));
        mBeans.add(new ItemBean(R.mipmap.icon_money_chart, "财务管理"));
        mBeans.add(new ItemBean(R.mipmap.icon_user_manager, "地主管理"));
        mBeans.add(new ItemBean(R.mipmap.icon_order, "工单管理"));
        mBeans.add(new ItemBean(R.mipmap.icon_noitce, "公告管理"));
        return mBeans;
    }

    public static class ItemBean {
        public int iconRes;
        public String label;
        public int grade;

        public ItemBean(int iconRes, String label) {
            this.iconRes = iconRes;
            this.label = label;
        }

    }
}

