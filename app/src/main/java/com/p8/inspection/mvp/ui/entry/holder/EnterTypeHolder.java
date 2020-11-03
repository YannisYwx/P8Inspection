package com.p8.inspection.mvp.ui.entry.holder;

import android.view.View;

import com.p8.inspection.base.recycler.BaseRecycleHolder;
import com.p8.inspection.data.bean.UserType;

/**
 * @author : WX.Y
 * date : 2020/10/20 17:42
 * description :
 */
public class EnterTypeHolder extends BaseRecycleHolder<UserType> {
    public EnterTypeHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(UserType data) {
//        TextView tvType = itemView.findViewById(R.id.tv_type);
//        tvType.setText(data.type);
    }
}

