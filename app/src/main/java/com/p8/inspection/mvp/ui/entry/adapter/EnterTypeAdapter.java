package com.p8.inspection.mvp.ui.entry.adapter;


import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.p8.inspection.R;
import com.p8.inspection.data.bean.UserType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : WX.Y
 * date : 2020/10/20 17:22
 * description :
 */
public class EnterTypeAdapter extends BaseQuickAdapter<UserType, BaseViewHolder> {


    private EnterTypeAdapter(@Nullable List<UserType> data) {
        super(R.layout.item_enter_type, data);
    }

    public EnterTypeAdapter() {
        this(getDefaultUserTypes());
    }

    private static List<UserType> getDefaultUserTypes() {
        List<UserType> userTypes = new ArrayList<>();
        userTypes.add(new UserType(0, "大主", 0, Color.parseColor("#3B3B5B")));
        userTypes.add(new UserType(1, "地主", 0, Color.parseColor("#4898F6")));
        userTypes.add(new UserType(2, "场主", 0, Color.parseColor("#1EA388")));
        userTypes.add(new UserType(3, "小主", 0, Color.parseColor("#F77539")));
        userTypes.add(new UserType(4, "台主", 0, Color.parseColor("#EBC82C")));
        userTypes.add(new UserType(5, "中主", 0, Color.parseColor("#3177AC")));
        userTypes.add(new UserType(6, "自主", 0, Color.parseColor("#C0D9F5")));
        userTypes.add(new UserType(7, "施主", 0, Color.parseColor("#14CFA0")));
        userTypes.add(new UserType(8, "其他", 0, Color.parseColor("#706F89")));
        return userTypes;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, UserType userType) {
        baseViewHolder.setText(R.id.tv_name, userType.type);
        baseViewHolder.setText(R.id.tv_label, userType.type.substring(0,1));
        baseViewHolder.itemView.setBackgroundColor(userType.color);
    }
}

