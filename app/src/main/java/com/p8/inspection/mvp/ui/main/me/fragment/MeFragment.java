package com.p8.inspection.mvp.ui.main.me.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.AliOssManager;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.MeContract;
import com.p8.inspection.mvp.presenter.MePresenter;
import com.p8.inspection.mvp.ui.main.me.adapter.MeAdapter;
import com.p8.inspection.utils.GlideEngine;
import com.p8.inspection.utils.GlideUtils;
import com.p8.inspection.widget.DialogUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author : WX.Y
 * date : 2020/10/26 15:57
 * description :
 */
public class MeFragment extends DaggerMvpFragment<MePresenter, MeContract.View> implements MeContract.View,
        DialogUtils.OnTakePhotoDialogChoiceListener, BaseQuickAdapter.OnItemClickListener {
    CircleImageView civUserHeader;

    private RecyclerView mRecyclerView;
    private View headerView;
    private TextView tvGrade, tvUserName, tvLocation, tvOrder, tvCheck, tvLandlord;
    MeAdapter mAdapter;
    public String url = "http://p8bucket.oss-cn-shenzhen.aliyuncs.com/img_1d38770488b74b32bf0af9a6919b36f6_1603789894569.jpg";

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view) {
        mRecyclerView = $(R.id.rv_item);
        headerView = getLayoutInflater().inflate(R.layout.item_me_header, (ViewGroup) mRecyclerView.getParent(), false);
        civUserHeader = headerView.findViewById(R.id.civ_user_header);
        tvGrade = headerView.findViewById(R.id.tv_grade);
        tvUserName = headerView.findViewById(R.id.tv_name);
        tvLocation = headerView.findViewById(R.id.tv_location);
        tvOrder = headerView.findViewById(R.id.tv_order_value);
        tvCheck = headerView.findViewById(R.id.tv_check_value);
        tvLandlord = headerView.findViewById(R.id.tv_landlord_value);
        mTitleBar.setTitle(mContext.getResources().getStringArray(R.array.userType)[1]);

    }


    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new MeAdapter();
        mAdapter.addHeaderView(headerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        GlideUtils.setImageViewForUrl(mContext, civUserHeader, url);
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemClickListener(this);
        civUserHeader.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position) {
            case 0:
                //个人中心
                startByParent(UserCenterFragment.newInstance());
                break;
            case 1:
                //停车监控
                startByParent(ParkingMonitorFragment.newInstance());
                break;
            case 2:
                //设备安装
                startByParent(DeviceDebugFragment.newInstance());
                break;
            case 3:
                //工单处理
                startByParent(DeviceBindingFragment.newInstance());
                break;
            case 4:
                //签到签出
                startByParent(SearchLordFragment.newInstance());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean hasTitleBar() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.civ_user_header:
                DialogUtils.showTakePhotoDialog(mContext, this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void refreshContentView(View view) {
    }

    @Override
    public boolean isTitleBarBackEnable() {
        return false;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_me_rv;
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
                    AliOssManager.getInstance().uploadFiles(mContext, selectList);
                }
            }
        }
    }

    @Override
    public void onSelectCamera() {
        PictureSelector.create(this)
                // 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .openCamera(PictureMimeType.ofImage())
                // 外部传入图片加载引擎，必传项
                .imageEngine(GlideEngine.createGlideEngine())
                // 最小选择数量
                .minSelectNum(1)
                // 是否使用自定义相机
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                .isUseCustomCamera(false)
                // 多选 or 单选
                .selectionMode(PictureConfig.SINGLE)
                // 是否可预览图片
                .isPreviewImage(true)
                // 是否裁剪
                .isEnableCrop(false)
                // 是否压缩
                .isCompress(true)
                // 图片压缩后输出质量
                .compressQuality(60)
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                // 是否圆形裁剪
                .circleDimmedLayer(true)
                //.setCircleDimmedColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪背景色值
                //.setCircleDimmedBorderColor(ContextCompat.getColor(this, R.color.app_color_white))// 设置圆形裁剪边框色值
                //.setCircleStrokeWidth(3)// 设置圆形裁剪边框粗细
                // 是否开启点击声音
                .isOpenClickSound(true)
                //预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .isPreviewEggs(false)
                // 裁剪输出质量 默认100
                .cutOutQuality(90)
                // 小于100kb的图片不压缩
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onSelectGallery() {
        //选择相册
        PictureSelector.create(MeFragment.this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                // 图片压缩后输出质量 0~ 100
                .compressQuality(80)
                .isCamera(false)
                // 是否显示拍照按钮
                .selectionMode(PictureConfig.SINGLE)
                // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isSingleDirectReturn(false)
                // 是否可预览图片
                .isPreviewImage(true)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                // 是否裁剪
                .isEnableCrop(false)
                // 是否压缩
                .isCompress(true)
                //同步false或异步true 压缩 默认同步
                .synOrAsy(true)
                // 是否圆形裁剪
                .circleDimmedLayer(false)
                // 裁剪输出质量 默认100
                .cutOutQuality(90)
                // 小于100kb的图片不压缩
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

}

