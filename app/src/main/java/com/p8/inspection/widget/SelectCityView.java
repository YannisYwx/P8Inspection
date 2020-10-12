package com.p8.inspection.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p8.inspection.R;
import com.p8.inspection.data.bean.Area;
import com.p8.inspection.data.bean.Areas;
import com.p8.inspection.data.bean.Cities;
import com.p8.inspection.data.bean.City;
import com.p8.inspection.data.bean.Province;
import com.p8.inspection.data.bean.Provinces;
import com.p8.inspection.data.bean.Street;
import com.p8.inspection.data.bean.Streets;
import com.p8.inspection.mvp.ui.adapter.NameAdapter;
import com.p8.inspection.utils.ValueAnimatorUtils;

import java.util.ArrayList;

/**
 * author : WX.Y
 * date : 2020/9/25 11:59
 * description :
 */
public class SelectCityView extends ConstraintLayout implements View.OnClickListener {

    NameAdapter<Area> areasAdapter;
    NameAdapter<City> citiesAdapter;
    NameAdapter<Province> provincesAdapter;
    NameAdapter<Street> streetsAdapter;
    NameAdapter<String> carTypeAdapter;

    Provinces mProvinces;
    Cities mCities;
    Areas mAreas;
    Streets mStreets;

    RecyclerView rvProvinces;
    RecyclerView rvCities;
    RecyclerView rvAreas;
    RecyclerView rvStreets;
    RecyclerView rvCartType;
    View view1, view2, line1, line2, line3;
    FrameLayout maskLayout;
    ConstraintLayout cityLayout;
    LinearLayout carTypeLayout;

    Province currentProvince;
    City currentCity;
    Area currentArea;
    Street currentStreet;
    String address = "";

    ValueAnimatorUtils valueAnimatorUtils;

    int selectType;

    int carType = -1;

    ArrayList<String> strings = new ArrayList<>();

    public SelectCityView(@NonNull Context context) {
        super(context);
    }

    public SelectCityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
        strings.add("全部泊位");
        strings.add("无车");
        strings.add("有车");
        strings.add("等待激活");
        strings.add("初始化中");
        strings.add("异常");
        valueAnimatorUtils = new ValueAnimatorUtils();
    }

    public SelectCityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_city_select, this, true);
        if (attrs == null) {
            return;
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectCityView);
        selectType = a.getInteger(R.styleable.SelectCityView_selectType, 0);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        rvProvinces = findViewById(R.id.rv_provinces);
        rvCities = findViewById(R.id.rv_cities);
        rvAreas = findViewById(R.id.rv_areas);
        rvStreets = findViewById(R.id.rv_street);
        rvCartType = findViewById(R.id.rv_car_type);
        cityLayout = findViewById(R.id.city_layout);
        carTypeLayout = findViewById(R.id.car_type_layout);
        maskLayout = findViewById(R.id.mask_layout);
        line1 = findViewById(R.id.v1);
        line2 = findViewById(R.id.v2);
        line3 = findViewById(R.id.v3);
        maskLayout.setOnClickListener(this);
        initCarType();
        super.onFinishInflate();
    }

    //初始化省份信息列表
    public void initProvincesList(Provinces provinces) {
        if (provinces == null || provinces.getList().size() == 0) return;
        mProvinces = provinces;
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        provincesAdapter = new NameAdapter<Province>(this.getContext(), mProvinces.getList()) {
            @Override
            public void setText(Province data, TextView textView, int position) {
                textView.setText(data.getProvince());
            }
        };
        rvProvinces.setVisibility(VISIBLE);
        line1.setVisibility(VISIBLE);
        line2.setVisibility(GONE);
        line3.setVisibility(GONE);
        rvProvinces.setLayoutManager(manager);
        rvProvinces.setAdapter(provincesAdapter);
        provincesAdapter.notifyDataChanged();
        provincesAdapter.setOnChildClickListener((adapter, holder, groupPosition, childPosition) -> {
            if (valueAnimatorUtils.isMoving()) return;

            if (setAdapterSelector(provincesAdapter, childPosition)) return;

            clearCurrentInfo();
            currentProvince = provincesAdapter.getDataList().get(childPosition);
            //重置数据隐藏区和街道
            rvCities.setAdapter(null);
            rvAreas.setAdapter(null);
            rvStreets.setAdapter(null);
            rvAreas.setVisibility(View.GONE);
            rvStreets.setVisibility(View.GONE);
            line2.setVisibility(GONE);
            line3.setVisibility(GONE);
            //获取城市
            if (mItemSelectListener != null) {
                mItemSelectListener.onItemSelect(PROVINCE, currentProvince.getProvinceId());
            }
//            Preferences.saveString(Preferences.KEY_PROVINCE_SELETED_POSITION, "" + childPosition);
        });
    }

    /**
     * 初始化城市信息列表
     *
     * @param cities
     */
    public void initCitiesList(Cities cities) {
        if (cities == null || cities.getList().size() == 0) return;
        mCities = cities;
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        citiesAdapter = new NameAdapter<City>(this.getContext(), cities.getList()) {
            @Override
            public void setText(City data, TextView textView, int position) {
                textView.setText(data.getCity());
            }
        };
        rvCities.setLayoutManager(manager);
        rvCities.setAdapter(citiesAdapter);
        citiesAdapter.setOnChildClickListener((adapter, holder, groupPosition, childPosition) -> {
            if (valueAnimatorUtils.isMoving()) return;

            if (setAdapterSelector(citiesAdapter, childPosition)) return;

            currentCity = citiesAdapter.getDataList().get(childPosition);
            //重置数据
            rvAreas.setAdapter(null);
            rvStreets.setAdapter(null);
            //获取区
            if (mItemSelectListener != null) {
                mItemSelectListener.onItemSelect(CITY, currentCity.getCityId());
            }
        });
    }

    /**
     * 初始化区域列表
     *
     * @param areas
     */
    public void initAreasList(Areas areas) {
        if (areas == null || areas.getList().size() == 0) return;
        mAreas = areas;
        rvAreas.setVisibility(View.VISIBLE);
        line1.setVisibility(VISIBLE);
        line2.setVisibility(VISIBLE);
        line3.setVisibility(GONE);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        areasAdapter = new NameAdapter<Area>(this.getContext(), areas.getList()) {
            @Override
            public void setText(Area data, TextView textView, int position) {
                textView.setText(data.getArea());
            }
        };
        rvAreas.setLayoutManager(manager);
        rvAreas.setAdapter(areasAdapter);
        areasAdapter.setOnChildClickListener((adapter, holder, groupPosition, childPosition) -> {
            if (valueAnimatorUtils.isMoving()) return;

            if (setAdapterSelector(areasAdapter, childPosition)) return;

            currentArea = areasAdapter.getDataList().get(childPosition);
            //重置数据
            rvStreets.setAdapter(null);
            //获取街道
            if (mItemSelectListener != null) {
                mItemSelectListener.onItemSelect(AREA, getAreaAddress());
            }
        });
    }

    /**
     * 初始化街道信息列表
     *
     * @param streets
     */
    public void initStreetsList(Streets streets) {
        if (streets == null || streets.getList().size() == 0) return;
        rvProvinces.setVisibility(View.GONE);
        rvStreets.setVisibility(View.VISIBLE);
        mStreets = streets;
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        streetsAdapter = new NameAdapter<Street>(this.getContext(), streets.getList()) {
            @Override
            public void setText(Street data, TextView textView, int position) {
                textView.setText(data.getAddress());
            }
        };
        rvStreets.setLayoutManager(manager);
        rvStreets.setAdapter(streetsAdapter);
        streetsAdapter.setOnChildClickListener((adapter, holder, groupPosition, childPosition) -> {
            if (valueAnimatorUtils.isMoving()) return;

            if (setAdapterSelector(streetsAdapter, childPosition)) return;

            currentStreet = streetsAdapter.getDataList().get(childPosition);
            address = currentProvince.getProvince()
                    + currentCity.getCity() + currentArea.getArea() + currentStreet.getAddress();
            onLocationSelect();
        });
    }

    /**
     * 初始化车位类型列表
     */
    public void initCarType() {
        LinearLayoutManager carTypeManager = new LinearLayoutManager(this.getContext());
        carTypeManager.setOrientation(RecyclerView.VERTICAL);
        carTypeAdapter = new NameAdapter<String>(this.getContext(), strings) {
            @Override
            public void setText(String data, TextView textView, int position) {
                textView.setText(data);
            }

            @Override
            public int getChildLayout(int viewType) {
                return R.layout.item_car_type;
            }
        };
        rvCartType.setLayoutManager(carTypeManager);
        rvCartType.setAdapter(carTypeAdapter);
        carTypeAdapter.setOnChildClickListener((adapter, holder, groupPosition, childPosition) -> {
            if (valueAnimatorUtils.isMoving()) return;

            if (setAdapterSelector(carTypeAdapter, childPosition)) return;
            carType = childPosition;
            onCarTypeSelect();

        });
    }

    /**
     * 根据type判断，关闭或者打开城市及车辆类型布局
     *
     * @param type
     */
    public void openOrClose(int type) {
        switch (type) {
            case LOCATION_VIEW:
                valueAnimatorUtils.monitorCityOpenOrClose(cityLayout, view1
                        , carTypeLayout, view2, maskLayout, 300);
                break;
            case CAR_TYPE_VIEW:
                valueAnimatorUtils.monitorCityOpenOrClose(carTypeLayout, view2
                        , cityLayout, view1, maskLayout, 300);
                break;
        }
    }

    public interface OnItemSelectListener {
        void onItemSelect(int code, String id);

        void onLocationSelect(Province province, City city, Area area, Street street);
    }

    private OnItemSelectListener mItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.mItemSelectListener = listener;
    }

    public static final int PROVINCE = 0;
    public static final int CITY = 1;
    public static final int AREA = 2;
    public static final int STREET = 3;
    public static final int CAR_TYPE = 4;

    public static final int LOCATION_VIEW = 0x01;
    public static final int CAR_TYPE_VIEW = 0x11;

    /**
     * 窗口选中时选中效果，同时过滤选中同一个
     *
     * @param adapter
     * @param childPosition
     * @return
     */
    public boolean setAdapterSelector(NameAdapter<?> adapter, int childPosition) {
        if (adapter.getSelectIndex() == childPosition) {
            return true;
        }
        adapter.setSelectIndex(childPosition);
        adapter.notifyDataChanged();
        return false;
    }

    /**
     * 选中位置
     */
    public void onLocationSelect() {
        openOrClose(LOCATION_VIEW);
        //重置省份数据
        provincesAdapter.setSelectIndex(-1);
        provincesAdapter.notifyDataSetChanged();
        rvCities.setAdapter(null);
        rvAreas.setAdapter(null);
        rvStreets.setAdapter(null);
        rvAreas.setVisibility(View.GONE);
        rvStreets.setVisibility(View.GONE);
        rvProvinces.setVisibility(View.VISIBLE);
        //获取位置并关闭视图
        if (mItemSelectListener != null) {
            mItemSelectListener.onLocationSelect(currentProvince, currentCity, currentArea, currentStreet);
        }
    }


    /**
     * 选中车类型
     */
    public void onCarTypeSelect() {
        //直接关闭车辆类型视图
        openOrClose(CAR_TYPE_VIEW);
        if (currentProvince != null
                && currentCity != null
                && currentArea != null
                && currentStreet != null
                || !"".equals(address)) {
            if (mItemSelectListener != null) {
                mItemSelectListener.onLocationSelect(currentProvince, currentCity, currentArea, currentStreet);
            }
        }
    }

    public void close() {
        if (cityLayout.getVisibility() == View.VISIBLE) {
            openOrClose(LOCATION_VIEW);
            if (provincesAdapter != null) {
                provincesAdapter.setSelectIndex(-1);
                provincesAdapter.notifyDataSetChanged();
            }
            rvCities.setAdapter(null);
            rvAreas.setAdapter(null);
            rvStreets.setAdapter(null);
            rvAreas.setVisibility(View.GONE);
            rvStreets.setVisibility(View.GONE);
            rvProvinces.setVisibility(VISIBLE);
        }
    }

    //车辆类型重置(选中第0项)
    public void resetCarType() {
        carTypeAdapter.setSelectIndex(0);
        carTypeAdapter.notifyDataSetChanged();
    }

    //车辆类型重置(选中第position项)
    public void resetCarTypeFromPosition() {
        carTypeAdapter.setSelectIndex(carTypeAdapter.getSelectIndex());
        carTypeAdapter.notifyDataSetChanged();
    }

    //设置当前的城市数据
    //Provinces provinces,Cities cities,Area area,Streets streets
    public String getAddress() {
        if (currentProvince != null && currentCity != null && currentArea != null && currentStreet != null) {
            address = currentProvince.getProvince() + currentCity.getCity() + currentArea.getArea() + currentStreet.getAddress();
            return address;
        }
        if (currentProvince != null && currentCity != null && currentArea != null) {
            return currentProvince.getProvince() + currentCity.getCity() + currentArea.getArea();
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 关闭或者打开城市布局
     *
     * @param icon
     */
    public void openOrClose(View icon) {
        valueAnimatorUtils.monitorCityOpenOrClose2(cityLayout, icon, maskLayout, 300);
    }

    public void setArrowViews(View v1, View v2) {
        this.view1 = v1;
        this.view2 = v2;
    }

    public void clearCurrentInfo() {
        currentProvince = null;
        currentCity = null;
        currentArea = null;
        currentStreet = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mask_layout) {//通过判断哪个布局可见来区分关闭后需要的事件
            if (carTypeLayout.getVisibility() == View.VISIBLE) {
                openOrClose(CAR_TYPE_VIEW);
            } else if (cityLayout.getVisibility() == View.VISIBLE) {
                openOrClose(LOCATION_VIEW);
            }
        }
    }

    /***********************************************get()_set()*****************************************************/
    public Province getCurrentProvince() {
        return currentProvince;
    }

    public void setCurrentProvince(Province currentProvince) {
        this.currentProvince = currentProvince;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area currentArea) {
        this.currentArea = currentArea;
    }

    public Street getCurrentStreet() {
        return currentStreet;
    }

    public void setCurrentStreet(Street currentStreet) {
        this.currentStreet = currentStreet;
    }

    public Provinces getProvinces() {
        return mProvinces;
    }

    public void setProvinces(Provinces mProvinces) {
        this.mProvinces = mProvinces;
    }

    public String getAreaAddress() {
        return currentProvince.getProvince() + currentCity.getCity() + currentArea.getArea();
    }

    public RecyclerView getProvincesRv() {
        return rvProvinces;
    }

    public int getCarTypeSelectedPosition() {
        return carTypeAdapter.getSelectIndex();
    }

    public String getCarTypeStateStr() {
        if (carTypeAdapter == null || carType == -1) {
            return "";
        }
        return carTypeAdapter.getDataList().get(carTypeAdapter.getSelectIndex());
    }

    public int getCarType() {
        return carType;
    }
}

