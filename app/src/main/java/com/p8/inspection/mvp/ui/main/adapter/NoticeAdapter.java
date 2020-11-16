package com.p8.inspection.mvp.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.ClockRecord;
import com.p8.inspection.data.bean.Notice;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/28 14:49
 * description : 公告
 */
public class NoticeAdapter extends BaseQuickAdapter<Notice, BaseViewHolder> {
    public NoticeAdapter(@Nullable List<Notice> data) {
        super(R.layout.item_notice, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Notice item) {
        helper.addOnClickListener(R.id.tv_more);
//        helper.setText(R.id.tv_title,item.getTitle());
//        helper.setText(R.id.tv_content,item.getContent());
    }
}

