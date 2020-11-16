package com.p8.inspection.mvp.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.common.widget.swipe.SwipeRevealLayout;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.LordDevice;
import com.p8.inspection.utils.GlideUtils;
import com.p8.inspection.utils.ImageLoader;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/28 14:49
 * description :
 */
public class LandlordManageAdapter extends BaseQuickAdapter<Landlord, BaseViewHolder> {
    public LandlordManageAdapter(@Nullable List<Landlord> data) {
        super(R.layout.item_landlord, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Landlord item) {
        helper.setText(R.id.tv_landlord_name, item.getRealName());
        helper.setText(R.id.tv_phone_number, item.getPhone());
        ImageLoader.loadHeadPortrait(mContext,item.getFacadeImg(),helper.getView(R.id.civ_landlord));
        helper.addOnClickListener(R.id.tv_delete, R.id.content_layout);
        SwipeRevealLayout revealLayout = (SwipeRevealLayout) helper.itemView;
        revealLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
            @Override
            public void onClosed(SwipeRevealLayout view) {
                item.isOpen = false;
            }

            @Override
            public void onOpened(SwipeRevealLayout view) {
                item.isOpen = true;
            }

            @Override
            public void onSlide(SwipeRevealLayout view, float slideOffset) {

            }
        });

        if (item.isOpen) {
            revealLayout.open(false);
        } else {
            revealLayout.close(false);
        }
    }
}

