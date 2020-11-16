package com.p8.inspection.mvp.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.LordDevice;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/28 14:49
 * description :
 */
public class LordDeviceAdapter extends BaseQuickAdapter<LordDevice, BaseViewHolder> {
    public LordDeviceAdapter(@Nullable List<LordDevice> data) {
        super(R.layout.item_lord_device, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LordDevice item) {
        helper.setText(R.id.tv_lord_name,item.getName());
        helper.setText(R.id.tv_parking_number,item.getParkingNumber());
        helper.setText(R.id.tv_binding_time,item.getBindingTime());
    }
}

