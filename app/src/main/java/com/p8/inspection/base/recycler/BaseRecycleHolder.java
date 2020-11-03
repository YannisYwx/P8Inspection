package com.p8.inspection.base.recycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.p8.inspection.data.Constants.EXTRA;


/**
 * @author : Yannis.Ywx
 * createTime : 2017/9/22  16:30
 * description :
 */
public abstract class BaseRecycleHolder<T> extends RecyclerView.ViewHolder implements BaseRecycleHolderImpl<T> {
    private T data;
    protected Context mContext;
    public BaseRecycleHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
    }
    /**
     * 设置数据
     *
     * @param data 绑定的数据
     */
    @Override
    public void setDataAndRefreshHolderView(T data) {
        this.data = data;
        refreshViewHolder(data);
    }

    /**
     * 刷新控件
     *
     * @param data 绑定的数据
     */
    protected abstract void refreshViewHolder(T data);

    /**
     * 根据需求刷新控件
     *
     * @param key key
     */
    protected void refreshViewHolderByKey(Object key) {
    }

    /**
     * 获得数据
     *
     * @return 当前绑定的数据
     */
    public T getData() {
        return data;
    }

    protected void startActivity(Class<? extends Activity> cls, Bundle bundle) {
        if (cls == null) {
            throw new NullPointerException("The Class can not be null");
        }
        Intent intent = new Intent(itemView.getContext(), cls);
        if (bundle != null) {
            intent.putExtra(EXTRA, bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        itemView.getContext().startActivity(intent);
    }

    public int getColor(@ColorRes int colorRes) {
        return itemView.getContext().getResources().getColor(colorRes);
    }

    public String getString(@StringRes int stringRes) {
        return itemView.getContext().getResources().getString(stringRes);
    }

    public Drawable getDrawable(@DrawableRes int drawableRes){
        return ResourcesCompat.getDrawable(itemView.getContext().getResources(), drawableRes, null);
    }

    protected void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }
}
