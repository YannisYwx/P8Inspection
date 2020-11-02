package com.p8.inspection.mvp.ui.main.me.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.ClockRecord;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/28 14:49
 * description :
 */
public class ClockRecordAdapter extends BaseQuickAdapter<ClockRecord, BaseViewHolder> {
    public ClockRecordAdapter(@Nullable List<ClockRecord> data) {
        super(R.layout.item_history, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ClockRecord item) {
        helper.setText(R.id.tv_clock_in,item.getClockIn());
        helper.setText(R.id.tv_clock_out,item.getClockOut());
        helper.setText(R.id.tv_clock_date,item.getClockDate());
    }
}

