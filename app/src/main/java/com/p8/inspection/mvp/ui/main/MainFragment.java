package com.p8.inspection.mvp.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.p8.common.base.mvp.BaseFragment;
import com.p8.inspection.R;
import com.p8.inspection.mvp.ui.main.attendance.fragment.AttendanceFragment;
import com.p8.inspection.mvp.ui.main.home.fragment.HomeFragment;
import com.p8.inspection.mvp.ui.main.me.fragment.MeFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author : WX.Y
 * date : 2020/10/23 15:07
 * description :
 */
public class MainFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mRadioGroup;
    private SupportFragment[] mFragments = new SupportFragment[3];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFragment = findChildFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = AttendanceFragment.newInstance();
            mFragments[THIRD] = MeFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(AttendanceFragment.class);
            mFragments[THIRD] = findChildFragment(MeFragment.class);
        }
    }

    @Override
    public void initView(View view) {
        mRadioGroup = $(R.id.rg_main_tab);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void triggerLoadData() {

    }

    @Override
    protected void refreshContentView(View view) {

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int position = getCurrentSelectPagerIndex();
        switch (checkedId) {
            case R.id.rb_main_home:
//                showHideFragment(mFragments[FIRST], mFragments[position]);
                showHideFragment(mFragments[FIRST]);
                break;
            case R.id.rb_main_attendance:
//                showHideFragment(mFragments[SECOND], mFragments[position]);
                showHideFragment(mFragments[SECOND]);
                break;
            case R.id.rb_main_me:
//                showHideFragment(mFragments[THIRD], mFragments[position]);
                showHideFragment(mFragments[THIRD]);
                break;
            default:
                break;
        }
    }

    private int getCurrentSelectPagerIndex() {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_main_attendance:
                return SECOND;
            case R.id.rb_main_me:
                return THIRD;
            default:
                return FIRST;
        }
    }

}

