package com.p8.inspection.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.AdaptScreenUtils;

/**
 * @author : WX.Y
 * date : 2020/10/21 18:10
 * description :
 */
public class GridPaddingDecoration extends RecyclerView.ItemDecoration {


    public GridPaddingDecoration() {
    }

    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {


        int spanCount = getSpanCount(parent);
        int itemPosition = parent.getChildAdapterPosition(view);

        if (itemPosition != 0) {
            itemPosition = itemPosition - 1;
            if (itemPosition % spanCount == 0) {
                outRect.left = AdaptScreenUtils.pt2Px(8);
                outRect.right = AdaptScreenUtils.pt2Px(4);
            } else if ((itemPosition + 1) % spanCount == 0) {
                outRect.right = AdaptScreenUtils.pt2Px(8);
                outRect.left = AdaptScreenUtils.pt2Px(4);
            } else {
                outRect.right = AdaptScreenUtils.pt2Px(4);
                outRect.left = AdaptScreenUtils.pt2Px(4);
            }

            outRect.top = itemPosition < spanCount ? 8 : 4;
            outRect.bottom = 4;

        }


    }
}

