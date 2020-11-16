package com.p8.inspection.mvp.ui.main.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.broadcast.BroadcastAction;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.orhanobut.logger.Logger;
import com.p8.common.widget.pickerview.builder.OptionsPickerBuilder;
import com.p8.common.widget.pickerview.view.OptionsPickerView;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.SubmitOrderContract;
import com.p8.inspection.mvp.presenter.SubmitOrderPresenter;
import com.p8.inspection.mvp.ui.main.adapter.GridImageAdapter;
import com.p8.inspection.utils.FullyGridLayoutManager;
import com.p8.inspection.utils.GlideEngine;
import com.p8.inspection.utils.PictureSelectorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/5 11:27
 * description :
 */
public class SubmitWorkOrderFragment extends DaggerMvpFragment<SubmitOrderPresenter, SubmitOrderContract.View>
        implements SubmitOrderContract.View, GridImageAdapter.OnAddPicClickListener {

    public static final String TAG = SubmitWorkOrderFragment.class.getSimpleName();

    RecyclerView mRecyclerView;
    GridImageAdapter mAdapter;

    TextView tvAddress;
    EditText etParkingNumber, etDeviceNumber, etStreet, etRemark;
    Button btnSubmit;
    private String currentProvince = "", currentCity = "", currentArea = "";

    public static SubmitWorkOrderFragment newInstance() {
        Bundle args = new Bundle();
        SubmitWorkOrderFragment fragment = new SubmitWorkOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_pics);
        etParkingNumber = $(R.id.et_parking_number);
        etDeviceNumber = $(R.id.et_device_number);
        etStreet = $(R.id.et_street);
        tvAddress = $(R.id.et_address);
        etRemark = $(R.id.et_remark);
        btnSubmit = $(R.id.btn_submit);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext,
                4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(mContext, 8), true));
        mAdapter = new GridImageAdapter(mContext, this);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }
        mAdapter.setSelectMax(9);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        BroadcastManager.getInstance(_mActivity).registerReceiver(broadcastReceiver,
                BroadcastAction.ACTION_DELETE_PREVIEW_POSITION);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            BroadcastManager.getInstance(_mActivity).unregisterReceiver(broadcastReceiver);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.isEmpty(action)) {
                return;
            }
            Logger.e("BroadcastAction.ACTION_DELETE_PREVIEW_POSITION");
            if (BroadcastAction.ACTION_DELETE_PREVIEW_POSITION.equals(action)) {// 外部预览删除按钮回调
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    int position = extras.getInt(PictureConfig.EXTRA_PREVIEW_DELETE_POSITION);
                    ToastUtils.s(context, "delete image index:" + position);
                    mAdapter.remove(position);
                    mAdapter.notifyItemRemoved(position);
                }
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null) {
            outState.putParcelableArrayList("selectorList",
                    (ArrayList<? extends Parcelable>) mAdapter.getData());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回五种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
            // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
            // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
            for (LocalMedia media : selectList) {
                Log.i(TAG, "是否压缩:" + media.isCompressed());
                Log.i(TAG, "压缩:" + media.getCompressPath());
                Log.i(TAG, "原图:" + media.getPath());
                Log.i(TAG, "是否裁剪:" + media.isCut());
                Log.i(TAG, "裁剪:" + media.getCutPath());
                Log.i(TAG, "是否开启原图:" + media.isOriginal());
                Log.i(TAG, "原图路径:" + media.getOriginalPath());
                Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
            }
            mAdapter.setList(selectList);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setListener() {
        mAdapter.setOnItemClickListener((v, position) -> {
            List<LocalMedia> selectList = mAdapter.getData();
            if (selectList.size() > 0) {
                PictureSelectorUtils.previewPicture(this, position, selectList);
            }
        });

        bindClickListener(R.id.et_address, R.id.btn_submit);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == tvAddress) {
            if (KeyboardUtils.isSoftInputVisible(_mActivity)) {
                KeyboardUtils.hideSoftInput(_mActivity);
            }
            showPickerView();
        }

        if (v == btnSubmit) {
            //提交工单
        }
    }

    private void showPickerView() {// 弹出选择器
        int[] selectIndex = LocalDataManager.getInstance().getSelectAddressIndex(currentProvince, currentCity, currentArea);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (options1, options2, options3, v) -> {
            //返回的分别是三个级别的选中位置
            currentProvince = LocalDataManager.getInstance().getProvinces().size() > 0 ?
                    LocalDataManager.getInstance().getProvinces().get(options1).getPickerViewText() : "";

            currentCity = LocalDataManager.getInstance().getCities().size() > 0
                    && LocalDataManager.getInstance().getCities().get(options1).size() > 0 ?
                    LocalDataManager.getInstance().getCities().get(options1).get(options2) : "";

            currentArea = LocalDataManager.getInstance().getCities().size() > 0
                    && LocalDataManager.getInstance().getAreas().get(options1).size() > 0
                    && LocalDataManager.getInstance().getAreas().get(options1).get(options2).size() > 0 ?
                    LocalDataManager.getInstance().getAreas().get(options1).get(options2).get(options3) : "";

            StringBuilder stringBuilder = new StringBuilder(currentProvince);
            if (!currentProvince.equals(currentCity)) {
                stringBuilder.append(currentCity);
            }

            if (!currentCity.equals(currentArea)) {
                stringBuilder.append(currentArea);
            }

            tvAddress.setText(stringBuilder.toString());
        })
                .setTitleText("城市选择")
                .setTitleColor(getResources().getColor(R.color.text_black))
                .setDividerColor(Color.parseColor("#F4F4F6"))
                .setTextColorCenter(getResources().getColor(R.color.colorPrimary))
                .setSelectTextBackgroundColor(Color.parseColor("#F4F4F6"))
                .setContentTextSize(20)
                .setSelectOptions(selectIndex[0], selectIndex[1], selectIndex[2])
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        //三级选择器
        pvOptions.setPicker(LocalDataManager.getInstance().getProvinces(),
                LocalDataManager.getInstance().getCities(),
                LocalDataManager.getInstance().getAreas());
        pvOptions.show();
    }

    @Override
    public int setTitle() {
        return R.string.title_submit_order;
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_submit_order;
    }

    @Override
    public void onAddPicClick() {
        PictureSelectorUtils.selectPicture(this, PictureSelectorUtils.CAMERA_AND_GALLERY, 6);
    }

}

