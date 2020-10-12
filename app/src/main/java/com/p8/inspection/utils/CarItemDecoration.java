package com.p8.inspection.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;

public class CarItemDecoration extends  RecyclerView.ItemDecoration{
    private int space;
    private int firstLeft;
    private int secondLeft;


    public CarItemDecoration(int space) {
        this.space = space;
    }

    public CarItemDecoration(int firstLeft, int secondLeft){
        this.firstLeft = firstLeft;
        this.secondLeft = secondLeft;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(parent.getChildLayoutPosition(view)%2 != 0){//奇数
            outRect.left = AdaptScreenUtils.pt2Px(20);
        }else{//偶数
            outRect.right = AdaptScreenUtils.pt2Px(20);
        }
        outRect.top = 20;
        outRect.bottom = 5;
    }
}
