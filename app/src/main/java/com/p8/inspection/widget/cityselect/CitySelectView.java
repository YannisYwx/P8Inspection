package com.p8.inspection.widget.cityselect;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.p8.inspection.R;
import com.p8.inspection.data.Constants;
import com.p8.inspection.utils.ValueAnimatorUtils;
import com.p8.inspection.widget.cityselect.data.Province;
import com.p8.inspection.widget.cityselect.data.ProvinceData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/11/6 15:57
 * description :
 */
public class CitySelectView extends ConstraintLayout implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener,
        RadioGroup.OnCheckedChangeListener {

    private final static int TAB_PROVINCE = 1;
    private final static int TAB_CITY = 2;
    private final static int TAB_AREA = 3;
    private final static int TAB_STREET = 4;

    //0 无车/1 有车/2 等待激活/3 初始化中/4 异常

    public static final int LOCATION_VIEW = 0x01;
    public static final int CAR_TYPE_VIEW = 0x11;

    private RecyclerView rvAddress;
    private TextView mProvinceTextView;
    private TextView mCityTextView;
    private TextView mAreaTextView;
    private TextView mStreetTextView;
    private View mIndicatorView;
    private RadioGroup mRadioGroup;

    FrameLayout maskLayout;
    ConstraintLayout cityLayout;
    ConstraintLayout carTypeLayout;
    View view1, view2;

    private List<Province> mProvinces;
    private List<String> mProvinceData = new ArrayList<>();
    private List<String> mCityData = new ArrayList<>();
    private List<String> mAreaData = new ArrayList<>();
    private List<String> mStreetData = new ArrayList<>();
    private List<String> mData = new ArrayList<>();

    private int provinceSelectIndex = -1;
    private int citySelectIndex = -1;
    private int areaSelectIndex = -1;
    private int streetSelectIndex = -1;
    private int tabSelect = TAB_PROVINCE;
    private String currentSelectType = "-1";

    private Province province;
    private List<Province.City> mCities;
    private Province.City city;

    private SelectAdapter mSelectAdapter;

    private String selectProvince;
    private String selectCity;
    private String selectArea;
    private String selectStreet;

    private OnSelectListener mSelectListener;

    private int indicationColor = Color.parseColor("#2096EF");
    private int selectTextColor = Color.parseColor("#2096EF");
    private int selectBackgroundColor = Color.parseColor("#F4F4F6");
    private int normalTextColor = Color.parseColor("#333333");

    ValueAnimatorUtils valueAnimatorUtils;


    public CitySelectView(@NonNull Context context) {
        this(context, null);
    }

    public CitySelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CitySelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_address_select, this, true);
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CitySelectView);
        indicationColor = a.getColor(R.styleable.CitySelectView_indicationColor, Color.parseColor("#2096EF"));
        selectTextColor = a.getColor(R.styleable.CitySelectView_indicationColor, Color.parseColor("#2096EF"));
        selectBackgroundColor = a.getColor(R.styleable.CitySelectView_indicationColor, Color.parseColor("#F4F4F6"));
        normalTextColor = a.getColor(R.styleable.CitySelectView_normalTextColor, Color.parseColor("#333333"));
        a.recycle();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ProvinceData provinceData = new ProvinceData();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Province>>() {
        }.getType();
        mProvinces = gson.fromJson(provinceData.data, type);

        provinceSelectIndex = -1;
        citySelectIndex = -1;
        areaSelectIndex = -1;
        streetSelectIndex = -1;
        tabSelect = TAB_PROVINCE;

        mSelectAdapter = new SelectAdapter(mData);
        mSelectAdapter.setEmptyView(com.p8.common.R.layout.pager_empty, (ViewGroup) rvAddress.getParent());
        mSelectAdapter.openLoadAnimation();
        mSelectAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvAddress.setLayoutManager(manager);
        rvAddress.setAdapter(mSelectAdapter);
        mSelectAdapter.setOnItemClickListener(this);
        fillProvincesData();
        valueAnimatorUtils = new ValueAnimatorUtils();
    }

    private void initView() {
        maskLayout = findViewById(R.id.mask_layout);
        carTypeLayout = findViewById(R.id.view_car_type);
        cityLayout = findViewById(R.id.view_city);
        mProvinceTextView = findViewById(R.id.tv_province);
        mCityTextView = findViewById(R.id.tv_city);
        mAreaTextView = findViewById(R.id.tv_area);
        mStreetTextView = findViewById(R.id.tv_street);
        mIndicatorView = findViewById(R.id.indicator);
        rvAddress = findViewById(R.id.rv_address);
        mRadioGroup = findViewById(R.id.rg_car_type);

        mProvinceTextView.setOnClickListener(this);
        mCityTextView.setOnClickListener(this);
        mAreaTextView.setOnClickListener(this);
        mStreetTextView.setOnClickListener(this);
        maskLayout.setOnClickListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);

        mProvinceTextView.measure(0, 0);
        ViewGroup.LayoutParams layoutParams = mIndicatorView.getLayoutParams();
        layoutParams.width = mProvinceTextView.getMeasuredWidth();
        mIndicatorView.setLayoutParams(layoutParams);
        mIndicatorView.setBackgroundColor(indicationColor);
        mProvinceTextView.setTextColor(selectTextColor);
    }

    @Override
    protected void onFinishInflate() {
        initView();
        initData();
        super.onFinishInflate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_province: {
                if (tabSelect == TAB_PROVINCE) {
                    return;
                }
                tabSelect = TAB_PROVINCE;
                mData.clear();
                mData.addAll(mProvinceData);
                mSelectAdapter.openLoadAnimation();
                mSelectAdapter.notifyDataSetChanged();
                selectProvinceTab();
                doIndicatorAnim(mProvinceTextView);
            }
            break;
            case R.id.tv_city: {
                if (tabSelect == TAB_CITY) {
                    return;
                }
                tabSelect = TAB_CITY;
                mData.clear();
                mData.addAll(mCityData);
                mSelectAdapter.openLoadAnimation();
                mSelectAdapter.notifyDataSetChanged();
                selectCityTab();
                doIndicatorAnim(mCityTextView);
            }
            break;
            case R.id.tv_area: {
                if (tabSelect == TAB_AREA) {
                    return;
                }
                tabSelect = TAB_AREA;
                mData.clear();
                mData.addAll(mAreaData);
                mSelectAdapter.openLoadAnimation();
                mSelectAdapter.notifyDataSetChanged();
                selectAreaTab();
                doIndicatorAnim(mAreaTextView);
            }
            break;
            case R.id.tv_street: {
                if (tabSelect == TAB_STREET) {
                    return;
                }
                tabSelect = TAB_STREET;
                mData.clear();
                mData.addAll(mStreetData);
                mSelectAdapter.openLoadAnimation();
                mSelectAdapter.notifyDataSetChanged();
                selectAreaTab();
                doIndicatorAnim(mStreetTextView);
            }
            break;
            case R.id.mask_layout: {
                if (carTypeLayout.getVisibility() == View.VISIBLE) {
                    openOrClose(CAR_TYPE_VIEW);
                } else if (cityLayout.getVisibility() == View.VISIBLE) {
                    openOrClose(LOCATION_VIEW);
                }
            }
            break;
            default:
                break;
        }
    }

    /**
     * 填充省数据
     */
    private void fillProvincesData() {
        mProvinceData.clear();
        for (Province province : mProvinces) {
            mProvinceData.add(province.name);
        }
        mData.clear();
        mData.addAll(mProvinceData);
        mSelectAdapter.notifyDataSetChanged();
    }

    /**
     * 填充市数据
     */
    private void fillCityData() {
        mCityData.clear();
        if (province != null) {
            mCities = province.city;
            for (Province.City city : mCities) {
                mCityData.add(city.name);
            }
            mData.clear();
            mData.addAll(mCityData);
            mSelectAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 填充区县市
     */
    private void fillAreaData() {
        mAreaData.clear();
        if (city != null) {
            List<String> areas = city.area;
            mAreaData.addAll(areas);
            mData.clear();
            mData.addAll(mAreaData);
            mSelectAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 填充街道
     */
    public void fillStreetData(List<String> streetData) {
        if (streetData != null && streetData.size() > 0) {
            mStreetData.clear();
            mStreetData.addAll(streetData);
            mData.clear();
            mData.addAll(mStreetData);
        } else {
            mData.clear();
        }
        mSelectAdapter.notifyDataSetChanged();
    }

    /**
     * 选上街道TAB
     */
    private void selectStreetTab() {
        mStreetTextView.setTextColor(selectTextColor);
        mProvinceTextView.setTextColor(normalTextColor);
        mCityTextView.setTextColor(normalTextColor);
        mAreaTextView.setTextColor(normalTextColor);
    }

    /**
     * 选上区TAB
     */
    private void selectAreaTab() {
        mAreaTextView.setTextColor(selectTextColor);
        mProvinceTextView.setTextColor(normalTextColor);
        mCityTextView.setTextColor(normalTextColor);
        mStreetTextView.setTextColor(normalTextColor);
    }

    /**
     * 选上市TAB
     */
    private void selectCityTab() {
        mCityTextView.setTextColor(selectTextColor);
        mProvinceTextView.setTextColor(normalTextColor);
        mStreetTextView.setTextColor(normalTextColor);
        mAreaTextView.setTextColor(normalTextColor);
    }

    /**
     * 选上省TAB
     */
    private void selectProvinceTab() {
        mProvinceTextView.setTextColor(selectTextColor);
        mCityTextView.setTextColor(normalTextColor);
        mStreetTextView.setTextColor(normalTextColor);
        mAreaTextView.setTextColor(normalTextColor);
    }


    /**
     * 游标动画
     */
    private void doIndicatorAnim(final TextView tabTextView) {
        tabTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float endX = tabTextView.getX();
                float endWidth = tabTextView.getMeasuredWidth();
                doAnim(endX, endWidth);
                tabTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * 真正做动画
     *
     * @param endX
     * @param endWidth
     */
    private void doAnim(float endX, float endWidth) {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mIndicatorView, "X", mIndicatorView.getX(), endX);
        final ViewGroup.LayoutParams layoutParams = mIndicatorView.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofFloat(layoutParams.width, endWidth);
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                layoutParams.width = (int) animatedValue;
                mIndicatorView.setLayoutParams(layoutParams);
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(translationAnimator, widthAnimator);
        set.start();
    }

    public void setViewGone() {
        cityLayout.setVisibility(GONE);
        carTypeLayout.setVisibility(GONE);
    }

    /**
     * 根据type判断，关闭或者打开城市及车辆类型布局
     *
     * @param type
     */
    public void openOrClose(int type) {
        switch (type) {
            case LOCATION_VIEW:
                valueAnimatorUtils.monitorCityOpenOrClose(true, cityLayout, view1
                        , carTypeLayout, view2, maskLayout, 300);
                break;
            case CAR_TYPE_VIEW:
                valueAnimatorUtils.monitorCityOpenOrClose(false, carTypeLayout, view2
                        , cityLayout, view1, maskLayout, 300);
                break;
            default:
                break;
        }
    }

    public String getCarType() {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_have_car:
                return Constants.DeviceStatus.HAVE_CAR;
            case R.id.rb_no_car:
                return Constants.DeviceStatus.NO_CAR;
            case R.id.rb_all:
                return Constants.DeviceStatus.ALL;
            default:
                return Constants.DeviceStatus.NOT_CHOICE;
        }
    }

    public void close() {
        if (cityLayout.getVisibility() == View.VISIBLE) {
            openOrClose(LOCATION_VIEW);
        }

        if (carTypeLayout.getVisibility() == View.VISIBLE) {
            openOrClose(CAR_TYPE_VIEW);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (tabSelect) {
            case TAB_PROVINCE:
                //省
                provinceSelectIndex = position;
                citySelectIndex = -1;
                areaSelectIndex = -1;
                province = mProvinces.get(position);
                String provinceName = province.name;
                mProvinceTextView.setText(provinceName);
                mCityTextView.setVisibility(View.VISIBLE);
                mCityTextView.setText("请选择");
                mAreaTextView.setVisibility(INVISIBLE);
                mStreetTextView.setVisibility(INVISIBLE);
                selectCityTab();
                fillCityData();
                tabSelect = TAB_CITY;
                doIndicatorAnim(mCityTextView);
                rvAddress.scrollToPosition(0);
                break;
            case TAB_CITY:
                //市
                citySelectIndex = position;
                areaSelectIndex = -1;
                city = mCities.get(position);
                String cityName = city.name;
                mCityTextView.setText(cityName);
                mAreaTextView.setVisibility(VISIBLE);
                mStreetTextView.setVisibility(INVISIBLE);
                mAreaTextView.setText("请选择");
                selectAreaTab();
                fillAreaData();
                tabSelect = TAB_AREA;
                doIndicatorAnim(mAreaTextView);
                rvAddress.scrollToPosition(0);
                break;
            case TAB_AREA:
                //区
                areaSelectIndex = position;
                String area = mAreaData.get(position);
                mAreaTextView.setText(area);
                mStreetTextView.setVisibility(View.VISIBLE);
                mStreetTextView.setText("请选择");
                selectStreetTab();
                tabSelect = TAB_STREET;
                doIndicatorAnim(mStreetTextView);

                selectProvince = mProvinceData.get(provinceSelectIndex);
                selectCity = mCityData.get(citySelectIndex);
                selectArea = mAreaData.get(areaSelectIndex);

                fillStreetData(null);

                if (mSelectListener != null) {
                    mSelectListener.requestStreet(selectProvince, selectCity, selectArea);
                }

                break;
            case TAB_STREET:
                streetSelectIndex = position;
                String street = mStreetData.get(position);
                mStreetTextView.setText(street);
                doIndicatorAnim(mStreetTextView);
                mSelectAdapter.notifyDataSetChanged();
                selectProvince = mProvinceData.get(provinceSelectIndex);
                selectCity = mCityData.get(citySelectIndex);
                selectArea = mAreaData.get(areaSelectIndex);
                selectStreet = mStreetData.get(streetSelectIndex);
                mSelectAdapter.notifyDataSetChanged();
                if (mSelectListener != null) {
                    mSelectListener.onSelect(selectProvince, selectCity, selectArea, selectStreet, String.valueOf(currentSelectType));
                }
                if (currentSelectType.equals(Constants.DeviceStatus.NOT_CHOICE)) {
                    openOrClose(CAR_TYPE_VIEW);
                }
                break;
            default:
                break;
        }
    }

    public void setArrowViews(View v1, View v2) {
        this.view1 = v1;
        this.view2 = v2;
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.mSelectListener = listener;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_have_car:
                currentSelectType = Constants.DeviceStatus.HAVE_CAR;
                break;
            case R.id.rb_no_car:
                currentSelectType = Constants.DeviceStatus.NO_CAR;
                break;
            default:
                currentSelectType = Constants.DeviceStatus.ALL;
                break;
        }
        if (!TextUtils.isEmpty(selectStreet)) {
            close();
            if (mSelectListener != null) {
                mSelectListener.onSelect(selectProvince, selectCity, selectArea, selectStreet, String.valueOf(currentSelectType));
            }
        }
        if (mSelectListener != null) {
            mSelectListener.onCarTypeSelect(currentSelectType);
        }
    }

    public String getSelectProvince() {
        return selectProvince;
    }

    public void setSelectProvince(String selectProvince) {
        this.selectProvince = selectProvince;
    }

    public String getSelectCity() {
        return selectCity;
    }

    public void setSelectCity(String selectCity) {
        this.selectCity = selectCity;
    }

    public String getSelectArea() {
        return selectArea;
    }

    public void setSelectArea(String selectArea) {
        this.selectArea = selectArea;
    }

    public String getSelectStreet() {
        return selectStreet;
    }

    public void setSelectStreet(String selectStreet) {
        this.selectStreet = selectStreet;
    }

    public interface OnSelectListener {

        /**
         * 请求街道信息
         *
         * @param province 省
         * @param city     市
         * @param area     区
         */
        void requestStreet(String province, String city, String area);

        /**
         * 车辆类型
         *
         * @param carType 车辆类型
         */
        void onCarTypeSelect(String carType);

        /**
         * 地址和泊位类型被选择
         *
         * @param province 省
         * @param city     市
         * @param area     区
         * @param street   街道
         * @param carType  车辆类型
         */
        void onSelect(String province, String city, String area, String street, String carType);
    }

    class SelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public SelectAdapter(@Nullable List<String> data) {
            super(R.layout.item_city_name, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_text, item);
            helper.setVisible(R.id.cv_select, false);
            CheckView checkView = helper.getView(R.id.cv_select);
            checkView.setColor(selectTextColor);
            int position = helper.getAdapterPosition();
            switch (tabSelect) {
                case TAB_PROVINCE:
                    //省
                    helper.setVisible(R.id.cv_select, provinceSelectIndex == position);
                    helper.setTextColor(R.id.tv_text, provinceSelectIndex == position ? selectTextColor : normalTextColor);
                    helper.itemView.setBackgroundColor(provinceSelectIndex == position ? selectBackgroundColor : Color.TRANSPARENT);
                    break;
                case TAB_CITY:
                    //市
                    helper.setVisible(R.id.cv_select, citySelectIndex == position);
                    helper.setTextColor(R.id.tv_text, citySelectIndex == position ? selectTextColor : normalTextColor);
                    helper.itemView.setBackgroundColor(citySelectIndex == position ? selectBackgroundColor : Color.TRANSPARENT);
                    break;
                case TAB_AREA:
                    //区
                    helper.setVisible(R.id.cv_select, areaSelectIndex == position);
                    helper.setTextColor(R.id.tv_text, areaSelectIndex == position ? selectTextColor : normalTextColor);
                    helper.itemView.setBackgroundColor(areaSelectIndex == position ? selectBackgroundColor : Color.TRANSPARENT);
                    break;
                case TAB_STREET:
                    //街道
                    helper.setVisible(R.id.cv_select, streetSelectIndex == position);
                    helper.setTextColor(R.id.tv_text, streetSelectIndex == position ? selectTextColor : normalTextColor);
                    helper.itemView.setBackgroundColor(streetSelectIndex == position ? selectBackgroundColor : Color.TRANSPARENT);
                    break;
                default:
                    break;
            }
        }
    }
}

