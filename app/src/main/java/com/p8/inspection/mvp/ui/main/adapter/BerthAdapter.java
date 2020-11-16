package com.p8.inspection.mvp.ui.main.adapter;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.bean.Machine;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/9 15:58
 * description :/泊位状态(0 无车/1 有车/2 等待激活/3 初始化中/4 异常
 */
public class BerthAdapter extends BaseQuickAdapter<Machine, BaseViewHolder> {
    public BerthAdapter(@Nullable List<Machine> data) {
        super(R.layout.item_berth, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Machine item) {
        helper.setText(R.id.tv_berth_number, item.getParkingNumber());
        helper.setTextColor(R.id.tv_berth_number, haveCar(item) ? getColor(R.color.colorPrimary) : getColor(R.color.text_deep_gray));
        helper.setImageResource(R.id.iv_bg, haveCar(item) ? R.mipmap.bg_has_car : R.mipmap.bg_no_car);
    }

    private boolean haveCar(Machine machine) {
        return machine.getParkingNumber().equals(Constants.DeviceStatus.HAVE_CAR);
    }

    @ColorInt
    private int getColor(@ColorRes int res) {
        return mContext.getResources().getColor(res);
    }
}

