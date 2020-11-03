package com.p8.inspection.mvp.ui.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IntDef;

import com.p8.inspection.R;
import com.p8.inspection.base.recycler.BaseRecycleHolder;
import com.p8.inspection.widget.SlackLoadingView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : Yannis.Ywx
 * createTime : 2017/9/22  16:42
 * description : 加载更多
 */
public class LoadMoreHolder extends BaseRecycleHolder<Integer> {

    @IntDef({LoadMoreState.LOADING, LoadMoreState.NONE, LoadMoreState.ERROR, LoadMoreState.NOT_SHOW, LoadMoreState.NO_DATA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreState {
        /**
         * 正在加载
         */
        int LOADING = 1;
        /**
         * 没有数据
         */
        int NONE = 2;
        /**
         * 加载出错
         */
        int ERROR = 3;
        /**
         * 不显示
         */
        int NOT_SHOW = 4;
        /**
         * 没有数据
         */
        int NO_DATA = 5;
    }


    public LoadMoreHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(@LoadMoreState Integer state) {
        LinearLayout llLoading = itemView.findViewById(R.id.ll_loadMore);
        SlackLoadingView loadingImage = itemView.findViewById(R.id.slackLoadingView);
        TextView tvNoMore = itemView.findViewById(R.id.tv_noMore);
        TextView tvRetry = itemView.findViewById(R.id.tv_retry);


        llLoading.setVisibility(View.INVISIBLE);
        tvNoMore.setVisibility(View.INVISIBLE);
        tvRetry.setVisibility(View.INVISIBLE);

        switch (state) {
            case LoadMoreState.LOADING:
                llLoading.setVisibility(View.VISIBLE);
                loadingImage.start();
                break;
            case LoadMoreState.NONE:
                tvNoMore.setVisibility(View.VISIBLE);
                break;
            case LoadMoreState.ERROR:
                tvRetry.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
