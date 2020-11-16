package com.p8.inspection.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureMimeType;
import com.p8.inspection.R;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/29  16:40
 * @email : 923080261@qq.com
 * @description : 图片加载
 */
public class ImageLoader {

    public static void loadCache(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(R.color.wheat)
                .placeholder(R.color.wheat)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(view);
    }

    public static void loadHeadPortrait(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .error(R.mipmap.default_avatar)
                .placeholder(R.mipmap.loading)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(view);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param obj
     * @param view
     */
    public static void loadRoundedCorner(Context context, Object obj, ImageView view) {

        RoundedCornersTransform transform = new RoundedCornersTransform(context, SizeUtils.dp2px(2));
        transform.setNeedCorner(true, false, true, false);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.main_background_deep_color)
                .error(R.color.deepPink)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(transform);
        Glide.with(context)
                .load(obj)
                .apply(options)
                .into(view);
    }


    /**
     * 加载图片
     *
     * @param context
     * @param obj
     * @param errorRes
     * @param placeholderRes
     * @param view
     */
    public static void loadImage(Context context, Object obj, int errorRes, int placeholderRes, ImageView view) {
        Glide.with(context)
                .load(obj)
                .error(errorRes)
                .placeholder(placeholderRes)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(view);
    }

    /**
     * 加载图片
     *
     * @param context
     * @param obj
     * @param imgRes
     * @param view
     */
    public static void loadImage(Context context, Object obj, int imgRes, ImageView view) {
        loadImage(context, obj, imgRes, imgRes, view);
    }


    public static void clear(Context context) {
        Glide.get(context).clearMemory();
    }

    public static void cancel(Context context, ImageView view) {
        Glide.with(context).clear(view);
    }

}