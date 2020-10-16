package com.p8.inspection.mvp.ui.holder;

import android.view.View;

import com.p8.inspection.R;
import com.p8.inspection.base.recycler.BaseRecycleHolder;
import com.p8.inspection.utils.GlideUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : WX.Y
 * date : 2020/10/15 17:47
 * description :
 */
public class MeHeaderHolder extends BaseRecycleHolder<String> {
    CircleImageView civUserHeader;

    public String url = "http://p8bucket.oss-cn-shenzhen.aliyuncs.com/img_2dd1b6dfcef347c193ab9b53efd692ca_1602668196167.jpg";

    public MeHeaderHolder(View itemView) {
        super(itemView);
        civUserHeader = itemView.findViewById(R.id.civ_user_header);

    }

    @Override
    protected void refreshViewHolder(String data) {
        GlideUtils.setImageViewForUrl(mContext, civUserHeader, url);
    }
}

