package com.p8.inspection.mvp.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.Order;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/28 14:49
 * description : 订单管理
 */
public class OrderInfoAdapter extends BaseQuickAdapter<Order, BaseViewHolder> {
    public OrderInfoAdapter(@Nullable List<Order> data) {
        super(R.layout.item_order, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Order item) {
        helper.setText(R.id.tv_order_time, TimeUtils.millis2String(item.getStartTime()));
        helper.setText(R.id.tv_account_time, TimeUtils.millis2String(item.getStartTime()));
        helper.setText(R.id.tv_parking_number, "车位号：" + item.getParkingNumber());
        helper.setText(R.id.tv_order_number, "订单流水号：" + item.getSerialNumber());
        helper.setText(R.id.tv_duration, item.getDuration() + "分钟");
        helper.setText(R.id.tv_money, item.getPayMoney() + "元");
        helper.setBackgroundRes(R.id.cl_bg, item.getState() == 1 ? R.mipmap.bg_label_unpaying : R.mipmap.bg_label_paying);
    }
}

