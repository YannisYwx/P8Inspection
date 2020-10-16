package com.p8.inspection.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.p8.inspection.R;
import com.p8.inspection.base.recycler.BaseRecycleHolder;
import com.p8.inspection.mvp.ui.adapter.MeAdapter;

/**
 * @author : WX.Y
 * date : 2020/10/15 17:50
 * description :
 */
public class MeInfoItemHolder extends BaseRecycleHolder<MeAdapter.ItemBean> {

    ImageView ivIcon;
    TextView tvLabel;

    public MeInfoItemHolder(View itemView) {
        super(itemView);
        ivIcon = itemView.findViewById(R.id.iv_icon);
        tvLabel = itemView.findViewById(R.id.tv_label);
    }

    @Override
    protected void refreshViewHolder(MeAdapter.ItemBean data) {
        ivIcon.setImageDrawable(getDrawable(data.iconRes));
        tvLabel.setText(data.label);

    }
}

