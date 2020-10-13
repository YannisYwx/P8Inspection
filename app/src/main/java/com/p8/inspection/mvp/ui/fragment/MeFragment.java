package com.p8.inspection.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMVPFragment;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.MeContract;
import com.p8.inspection.mvp.presenter.MePresenter;
import com.p8.inspection.utils.GlideEngine;
import com.p8.inspection.utils.GlideUtils;
import com.p8.inspection.widget.DialogUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author : WX.Y
 * date : 2020/10/12 15:37
 * description :
 */
public class MeFragment extends DaggerMVPFragment<MePresenter, MeContract.View> implements MeContract.View, DialogUtils.OnTakePhotoDialogChoiceListener {
    CircleImageView civUserHeader;

    PictureParameterStyle mPictureParameterStyle;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
        civUserHeader = $(R.id.civ_user_header);
    }

    @Override
    public void initData() {
    }

    @Override
    public void setListener() {
        $(R.id.civ_berth_monitor).setOnClickListener(this);
        $(R.id.civ_device_update).setOnClickListener(this);
        $(R.id.civ_order).setOnClickListener(this);
        $(R.id.civ_sign).setOnClickListener(this);
        civUserHeader.setOnClickListener(this);
        $(R.id.civ_user_center).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.civ_user_header:
                DialogUtils.showTakePhotoDialog(mContext, this);
                break;
        }
    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                LocalMedia localMedia = selectList.get(0);
                if (localMedia != null) {
                    GlideUtils.setImageViewForUrl(mContext, civUserHeader, localMedia.getPath());
                }
            }
        }
    }

    @Override
    public void onSelectCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .minSelectNum(1)// 最小选择数量
                .isUseCustomCamera(false)// 是否使用自定义相机
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .isPreviewImage(true)// 是否可预览图片
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .compressQuality(60)// 图片压缩后输出质量
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .circleDimmedLayer(true)// 是否圆形裁剪
                //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
                //.setCircleDimmedBorderColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                .isOpenClickSound(true)// 是否开启点击声音
                .isPreviewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onSelectGallery() {
        //选择相册
        PictureSelector.create(MeFragment.this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .isCamera(false)// 是否显示拍照按钮
                .selectionMode(PictureConfig.SINGLE)
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(true)// 是否可预览图片
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .isEnableCrop(false)// 是否裁剪
                .isCompress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步false或异步true 压缩 默认同步
                .circleDimmedLayer(false)// 是否圆形裁剪
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}

