package com.p8.inspection.mvp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.p8.common.recycler.groupedadapter.adapter.GroupedRecyclerViewAdapter;
import com.p8.common.recycler.groupedadapter.holder.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.Machine;
import com.p8.inspection.data.bean.Machines;
import com.p8.inspection.utils.GlideUtils;

import java.text.SimpleDateFormat;

/**
 * author : WX.Y
 * date : 2020/10/9 15:27
 * description :
 */
public class BerthMonitorAdapter extends GroupedRecyclerViewAdapter {

    Machines machines;

    public BerthMonitorAdapter(Context context, Machines machines) {
        super(context);
        this.machines = machines;
    }


    public void setMachines(Machines machines) {
        this.machines = machines;
    }

    public Machines getMachines() {
        return this.machines;
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (machines == null) {
            return 0;
        }
        return machines.getMachineList() == null ? 0 : machines.getMachineList().size();
    }

    @Override
    public boolean hasHeader(int groupPosition) {
        return false;
    }

    @Override
    public boolean hasFooter(int groupPosition) {
        return false;
    }

    @Override
    public int getHeaderLayout(int viewType) {
        return 0;
    }

    @Override
    public int getFooterLayout(int viewType) {
        return 0;
    }

    @Override
    public int getChildLayout(int viewType) {
        return R.layout.item_berth_monitor;
    }

    @Override
    public void onBindHeaderViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindFooterViewHolder(BaseViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(BaseViewHolder holder, int groupPosition, int childPosition) {
        ImageView monitorTitleIv = holder.get(R.id.adapter_monitor_title_iv);//都需要的item title图标
        ImageView monitorBg = holder.get(R.id.adapter_monitor_bg);
        TextView monitorTitleTv = holder.get(R.id.adapter_monitor_title_tv);//都需要的item title文字
        TextView monitorNumberTv = holder.get(R.id.adapter_monitor_number_tv);//都需要的item title 车位编号

        //车位故障(0)
        TextView monitorErrorStartTimeTv = holder.get(R.id.adapter_monitor_error_start_time_tv);//车位故障（开始时间）
        TextView monitorErrorEndTimeTv = holder.get(R.id.adapter_monitor_error_end_time_tv);//车位故障（结束时间）

        //车位有车(1)
        ImageView monitorTimeIv = holder.get(R.id.adapter_monitor_time_iv);//开始计时图标
        TextView monitorStartTimeTv = holder.get(R.id.adapter_monitor_start_time_tv);//开始计时文字
        TextView monitorStartTimeNumberTv = holder.get(R.id.adapter_monitor_start_time_number_tv);//开始计时时间

        //车位没车(2)
        TextView monitorEmptyBg = holder.get(R.id.adapter_monitor_empty_bg);//车位没车图标
        Machine machine = machines.getMachineList().get(childPosition);
        switch (machine.getParkingStatus()) {
            case 4://故障
                GlideUtils.setImageViewForResources(mContext, monitorTitleIv, R.drawable.page_logo_fault_default);
                GlideUtils.setImageViewForResources(mContext, monitorBg, R.drawable.pic_fault);
                monitorTitleTv.setText("设备故障:029");
                monitorTitleTv.setTextColor(mContext.getResources().getColor(R.color.device_error));
                monitorNumberTv.setText(machine.getParkingNumber());
                monitorNumberTv.setTextColor(mContext.getResources().getColor(R.color.device_number));

                monitorErrorStartTimeTv.setVisibility(View.VISIBLE);
                monitorErrorEndTimeTv.setVisibility(View.VISIBLE);
//                monitorErrorStartTimeTv.setText("开始2019.05.15 13:38");
                monitorErrorStartTimeTv.setText("开始" + TimeUtils.millis2String(machine.getEntryTime(), new SimpleDateFormat("yyyy.MM.dd HH:mm")));//
                monitorErrorEndTimeTv.setText("通告2019.05.15 13:38");

                monitorTimeIv.setVisibility(View.GONE);
                monitorStartTimeTv.setVisibility(View.GONE);
                monitorStartTimeNumberTv.setVisibility(View.GONE);

                monitorEmptyBg.setVisibility(View.GONE);
                break;
//            case 3://占用（计费）
            case 1://占用（计费）
                GlideUtils.setImageViewForResources(mContext, monitorTitleIv, R.drawable.page_logo_have_default);
                GlideUtils.setImageViewForResources(mContext, monitorBg, R.drawable.pic_parking);
                monitorTitleTv.setText("车位编号");
                monitorTitleTv.setTextColor(mContext.getResources().getColor(R.color.main_second_level_text_color));
                monitorNumberTv.setText(machine.getParkingNumber());
                monitorNumberTv.setTextColor(mContext.getResources().getColor(R.color.device_number));

                monitorErrorStartTimeTv.setVisibility(View.GONE);
                monitorErrorEndTimeTv.setVisibility(View.GONE);

                GlideUtils.setImageViewForResources(mContext, monitorTimeIv, R.drawable.page_logo_timing_default);
                monitorStartTimeNumberTv.setText(TimeUtils.millis2String(machine.getEntryTime(), new SimpleDateFormat("yyyy.MM.dd HH:mm")));
                monitorStartTimeTv.setText("开始计时");
                monitorTimeIv.setVisibility(View.VISIBLE);
                monitorStartTimeTv.setVisibility(View.VISIBLE);
                monitorStartTimeNumberTv.setVisibility(View.VISIBLE);

                monitorEmptyBg.setVisibility(View.GONE);
                break;
//            case 1://空闲
            case 0://空闲
                GlideUtils.setImageViewForResources(mContext, monitorTitleIv, R.drawable.page_logo_empty_default);
                GlideUtils.setImageViewForResources(mContext, monitorBg, R.drawable.pic_vacancy);
                monitorTitleTv.setText("车位编号");
                monitorTitleTv.setTextColor(mContext.getResources().getColor(R.color.main_content_text_color));
                monitorNumberTv.setText(machine.getParkingNumber());
                monitorNumberTv.setTextColor(mContext.getResources().getColor(R.color.main_content_text_color));

                monitorErrorStartTimeTv.setVisibility(View.GONE);
                monitorErrorEndTimeTv.setVisibility(View.GONE);

                monitorTimeIv.setVisibility(View.GONE);
                monitorStartTimeTv.setVisibility(View.GONE);
                monitorStartTimeNumberTv.setVisibility(View.GONE);

                monitorEmptyBg.setText("空闲");
                monitorEmptyBg.setVisibility(View.VISIBLE);
                break;
//            case 2://占用（待用）
            case 1235://占用（待用）
                GlideUtils.setImageViewForResources(mContext, monitorTitleIv, R.drawable.page_logo_inactive_default);
                GlideUtils.setImageViewForResources(mContext, monitorBg, R.drawable.pic_inactive);
                monitorTitleTv.setText("车位编号");
                monitorTitleTv.setTextColor(mContext.getResources().getColor(R.color.main_content_text_color));
                monitorNumberTv.setText(machine.getParkingNumber());
                monitorNumberTv.setTextColor(mContext.getResources().getColor(R.color.main_content_text_color));

                monitorErrorStartTimeTv.setVisibility(View.GONE);
                monitorErrorEndTimeTv.setVisibility(View.GONE);

                GlideUtils.setImageViewForResources(mContext, monitorTimeIv, R.drawable.page_logo_timing_default_);
                monitorStartTimeTv.setText("免费占用");
//                monitorStartTimeNumberTv.setText(TimeUtils.millis2String(machine.getEntryTime(),new SimpleDateFormat("yyyy.MM.dd HH:mm")));
                monitorTimeIv.setVisibility(View.VISIBLE);
                monitorStartTimeTv.setVisibility(View.VISIBLE);
                monitorStartTimeNumberTv.setVisibility(View.GONE);

                monitorEmptyBg.setVisibility(View.GONE);
                break;
        }
    }
}

