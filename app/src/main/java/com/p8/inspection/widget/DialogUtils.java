package com.p8.inspection.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.p8.common.dialog.IDialog;
import com.p8.common.dialog.YDialog;
import com.p8.inspection.R;

import java.util.HashMap;

/**
 * author : WX.Y
 * date : 2020/9/18 16:15
 * description :
 */
public class DialogUtils {
    private static HashMap<String, YDialog> hashMap = new HashMap<>();

    /**
     * @param context               Context
     * @param title                 标题
     * @param content               内容
     * @param btn1Str               左边按钮
     * @param negativeClickListener 左边点击事件
     * @param btn2Str               右边按钮
     * @param positiveClickListener 右边点击事件
     */
    public static void createDefaultDialog(Context context, String title, String content, String btn1Str,
                                           IDialog.OnClickListener positiveClickListener, String btn2Str, IDialog.OnClickListener negativeClickListener) {
        YDialog.Builder builder = new YDialog.Builder(context);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        if (!TextUtils.isEmpty(content)) {
            builder.setContent(content);
        }
        if (positiveClickListener != null) {
            if (TextUtils.isEmpty(btn1Str)) {
                builder.setPositiveButton(positiveClickListener);
            } else {
                builder.setPositiveButton(btn1Str, positiveClickListener);
            }
        }
        if (negativeClickListener != null) {
            if (TextUtils.isEmpty(btn2Str)) {
                builder.setNegativeButton(negativeClickListener);
            } else {
                builder.setNegativeButton(btn2Str, negativeClickListener);
            }
        }
        builder.show();
    }

    public static YDialog createProgressDialog(Context context,String msg) {
        closeLoadingDialog(context);
        YDialog.Builder builder = new YDialog.Builder(context);
        YDialog dialog = builder.setDialogView(R.layout.dialog_loading)
                .setAnimStyle(R.style.translate_style)//设置动画 默认没有动画
                .setScreenWidthP(0.85f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.2f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失
                .setAnimStyle(0).setBuildChildListener((iDialog, view, layoutRes) -> {
                    TextView tvMsg = view.findViewById(R.id.tv_msg);
                    tvMsg.setText(msg);
                }).show();
        hashMap.put(context.getClass().getSimpleName(), dialog);
        return dialog;
    }

    /**
     * 关闭loading dialog
     *
     * @param context Context
     */
    public static void closeLoadingDialog(Context context) {
        String dialogKey = context.getClass().getSimpleName();
        YDialog dialog = hashMap.get(dialogKey);
        if (dialog != null) {
            hashMap.remove(dialogKey);
            dialog.dismiss();
        }
    }
}

