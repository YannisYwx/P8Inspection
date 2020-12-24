package com.p8.inspection.mvp.ui.main.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.p8.inspection.R;
import com.p8.inspection.base.DaggerMvpFragment;
import com.p8.inspection.data.Constants;
import com.p8.inspection.data.bean.Landlord;
import com.p8.inspection.data.bean.Landlords;
import com.p8.inspection.di.component.FragmentComponent;
import com.p8.inspection.mvp.contract.DeviceDebugContract;
import com.p8.inspection.mvp.contract.LandlordManageContract;
import com.p8.inspection.mvp.presenter.DeviceDebugPresenter;
import com.p8.inspection.mvp.presenter.LandlordManagePresenter;

/**
 * @author : WX.Y
 * date : 2020/10/29 10:24
 * description :地主详情界面
 */
public class LandlordInfoFragment extends DaggerMvpFragment<LandlordManagePresenter, LandlordManageContract.View> implements LandlordManageContract.View {

    private EditText etName, etEmail, etPhoneNumber, etIdentify;
    private ImageView ivIdentifyPros, ivIdentifyCons;

    private Landlord mLandlord = null;

    public static LandlordInfoFragment newInstance(Landlord landlord) {
        Bundle args = new Bundle();
        LandlordInfoFragment fragment = new LandlordInfoFragment();
        args.putParcelable(Constants.Key.LANDLORD, landlord);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void beforeInitView() {
        if (getArguments() != null) {
            mLandlord = getArguments().getParcelable(Constants.Key.LANDLORD);
        }
    }

    @Override
    public void initView(View view, @Nullable Bundle savedInstanceState) {
        etName = $(R.id.et_name);
        etEmail = $(R.id.et_email);
        etIdentify = $(R.id.et_identify);
        etPhoneNumber = $(R.id.et_phone_number);
        ivIdentifyPros = $(R.id.iv_identify_pros);
        ivIdentifyCons = $(R.id.iv_identify_cons);
    }

    @Override
    public void initData() {
        mTitleBar.setRightText("保存");
        if (mLandlord != null) {
            etName.setText(mLandlord.getRealName());
            etPhoneNumber.setText(mLandlord.getPhone());
            etIdentify.setText(mLandlord.getIdentify());
            etEmail.setText(mLandlord.getEmail());
        }
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onTitleBarRightClick() {
        super.onTitleBarRightClick();
        Landlord landlord = new Landlord();
        landlord.setPhone(etPhoneNumber.getText().toString());
        landlord.setRealName(etName.getText().toString());
        presenter.addLandlord(landlord);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_landlord_info;
    }

    @Override
    public int setTitle() {
        return mLandlord == null ? R.string.title_landlord_add : R.string.title_landlord_modify;
    }

    @Override
    public void onRequestLandlordSuccess(Landlords landlords) {

    }

    @Override
    public void onAddLandlordSuccess(String message) {

    }
}

