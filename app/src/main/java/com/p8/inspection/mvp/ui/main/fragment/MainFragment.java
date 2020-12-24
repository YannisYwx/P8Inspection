package com.p8.inspection.mvp.ui.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CacheDoubleUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.p8.common.utils.AntiShakeUtils;
import com.p8.common.widget.CircleImageView;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.AliOssManager;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.LocalDataManager;
import com.p8.inspection.data.bean.Agency;
import com.p8.inspection.data.bean.Inspection;
import com.p8.inspection.data.bean.LoginInfo;
import com.p8.inspection.data.bean.UserMenu;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.MainContract;
import com.p8.inspection.mvp.presenter.MainPresenter;
import com.p8.inspection.mvp.ui.main.adapter.MenuAdapter;
import com.p8.inspection.utils.GlideUtils;
import com.p8.inspection.utils.ImageLoader;
import com.p8.inspection.utils.PictureSelectorUtils;
import com.p8.inspection.widget.DialogUtils;

import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/26 15:57
 * description :
 */
public class MainFragment extends DaggerMvpFragment<MainPresenter, MainContract.View> implements MainContract.View,
        DialogUtils.OnTakePhotoDialogChoiceListener, BaseQuickAdapter.OnItemClickListener {

    private CircleImageView civUserHeader;
    private RecyclerView mRecyclerView;
    private View headerView;
    private TextView tvGrade, tvUserName, tvLocation, tvOrder, tvCheck, tvLandlord;
    private MenuAdapter mAdapter;
    public String url = "http://p8bucket.oss-cn-shenzhen.aliyuncs.com/img_1d38770488b74b32bf0af9a6919b36f6_1603789894569.jpg";

    LoginInfo mLoginInfo;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = $(R.id.rv_item);
        headerView = getLayoutInflater().inflate(R.layout.item_me_header, (ViewGroup) mRecyclerView.getParent(), false);
        civUserHeader = headerView.findViewById(R.id.civ_user_header);
        tvGrade = headerView.findViewById(R.id.tv_grade);
        tvUserName = headerView.findViewById(R.id.tv_name);
        tvLocation = headerView.findViewById(R.id.tv_location);
        tvOrder = headerView.findViewById(R.id.tv_order_value);
        tvCheck = headerView.findViewById(R.id.tv_check_value);
        tvLandlord = headerView.findViewById(R.id.tv_landlord_value);
    }

    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        mAdapter = new MenuAdapter();
        mAdapter.addHeaderView(headerView);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        GlideUtils.setImageViewForUrl(mContext, civUserHeader, url);
        mLoginInfo = CacheDoubleUtils.getInstance().getParcelable(Constants.Key.LOGIN_INFO, LoginInfo.CREATOR);
        Logger.e(mLoginInfo.toString());
        mTitleBar.setTitle(mContext.getResources().getStringArray(R.array.userType)[mLoginInfo.getUserType()]);
        requestUserInfo();
    }

    private void requestUserInfo() {
        @Constants.UserType int uType = mLoginInfo.getUserType();
        switch (uType) {
            case Constants.UserType.LAND:
                presenter.requestInspectionInfo();
                break;
            case Constants.UserType.BUILD:
                break;
            case Constants.UserType.LARGE:
                presenter.requestAgencyInfo();
                break;
            case Constants.UserType.MEDIUM:
                break;
            case Constants.UserType.ONESELF:
                break;
            case Constants.UserType.OTHER:
                break;
            case Constants.UserType.PLACE:
                break;
            case Constants.UserType.PLATFORM:
                break;
            case Constants.UserType.SMALL:
                break;
            default:
                break;
        }
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
        if (AntiShakeUtils.isInvalidClick(view)) {
            return;
        }
        UserMenu userMenu = mAdapter.getData().get(position);
        switch (userMenu.getMenuTye()) {
            case UserMenu.MenuType.USER_CENTER:
                //个人中心
                start(UserCenterFragment.newInstance());
                break;
            case UserMenu.MenuType.PARKING_MONITOR:
                //停车监控
                start(ParkingMonitorFragment.newInstance());
                break;
            case UserMenu.MenuType.DEVICE_BINDING:
                //设备绑定
                start(DeviceBindingFragment.newInstance());
                break;
            case UserMenu.MenuType.DEVICE_DEBUG:
                //设备调试
                start(DeviceDebugFragment.newInstance());
                break;
            case UserMenu.MenuType.WORK_ORDER_PROCESSING:
                //工单处理
                start(LocalDataManager.getInstance().getUserType() == Constants.UserType.LAND ?
                        WorkOrderFragment.newInstance() : WorkOrderDisposeFragment.newInstance());
                break;
            case UserMenu.MenuType.CLOCK:
                //签到签出
                start(AttendanceFragment.newInstance());
                break;
            case UserMenu.MenuType.FINANCE_MANAGE:
                //财务管理
                break;
            case UserMenu.MenuType.J_MANAGE:
                //J架管理
                break;
            case UserMenu.MenuType.LAND_MANAGE:
                //地主管理
                start(LandlordManageFragment.newInstance());
                break;
            case UserMenu.MenuType.MOUNTINGS_MANAGE:
                //配件管理
                break;
            case UserMenu.MenuType.NOTICE_MANAGE:
                //公告管理
                start(NoticeManageFragment.newInstance());
                break;
            case UserMenu.MenuType.ORDER_MANAGE:
                //订单管理
                start(OrderManageFragment.newInstance());
                break;
            case UserMenu.MenuType.APP_UPDATE:
                break;
            case UserMenu.MenuType.CLEAR_CACHE:
                break;
            case UserMenu.MenuType.MESSAGE_CENTER:
                break;
            case UserMenu.MenuType.MODIFY_PASSWORD:
                break;
            case UserMenu.MenuType.SETTINGS:
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
    public void onClick(int viewId) {
        if (viewId == R.id.civ_user_header) {
            DialogUtils.showTakePhotoDialog(mContext, this);
        }
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
        //拍照
        PictureSelectorUtils.photograph(this);
    }

    @Override
    public void onSelectGallery() {
        //选择相册
        PictureSelectorUtils.selectSinglePictureFromGallery(this);
    }

    @Override
    public void onRequestAgencyInfoSuccess(Agency agency) {
        tvLocation.setText(agency.getAddress());
        tvUserName.setText(agency.getName());
        ImageLoader.loadHeadPortrait(this.mContext, agency.getFacadeImg(), civUserHeader);
    }

    @Override
    public void onRequestInspectionSuccess(Inspection inspection) {
        tvUserName.setText(inspection.getRealName());
    }
}

