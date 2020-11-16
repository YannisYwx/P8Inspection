package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.p8.common.base.BaseStatusPagerFragment;
import com.p8.inspection.R;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.bean.Order;

/**
 * @author : WX.Y
 * date : 2020/11/12 15:54
 * description : 订单详情
 */
public class OrderInfoFragment extends BaseStatusPagerFragment {

    private TextView tvPartner, tvOrderNumber, tvParkingNumber, tvOrderTime, tvAccountTime,
            tvDuration, tvStatus, tvWechat, tvMoney, tvPartnerShared, tvDescription;

    private Order mOrder;

    public static OrderInfoFragment newInstance(Order order) {
        Bundle args = new Bundle();
        OrderInfoFragment fragment = new OrderInfoFragment();
        args.putParcelable(Constants.Key.ORDER_INFO, order);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        tvPartner = $(R.id.tv_partner);
        tvOrderNumber = $(R.id.tv_order_number);
        tvParkingNumber = $(R.id.tv_parking_number);
        tvOrderTime = $(R.id.tv_order_time);
        tvAccountTime = $(R.id.tv_account_time);
        tvDuration = $(R.id.tv_duration);
        tvStatus = $(R.id.tv_status);
        tvWechat = $(R.id.tv_wechat);
        tvMoney = $(R.id.tv_money);
        tvPartnerShared = $(R.id.tv_partner_shared);
        tvDescription = $(R.id.tv_description);
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        mOrder = getArguments().getParcelable(Constants.Key.ORDER_INFO);
        if (mOrder != null) {
            tvPartner.setText(mOrder.getProfit() + "");
            tvPartnerShared.setText(mOrder.getProfit() + "元");
            tvOrderNumber.setText(mOrder.getSerialNumber());
            tvParkingNumber.setText(mOrder.getParkingNumber());
            tvOrderTime.setText(TimeUtils.millis2String(mOrder.getStartTime()));
            tvAccountTime.setText(TimeUtils.millis2String(mOrder.getEndTime()));
            tvDuration.setText(mOrder.getDuration() + "分钟");
            tvMoney.setText(mOrder.getPayMoney() + "元");
            tvDescription.setText(mOrder.getDescr());
            tvStatus.setText(mOrder.getState() == 1 ? "未支付" : "已支付");
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_order_info;
    }

    @Override
    public int setTitle() {
        return R.string.title_order_info;
    }
}

