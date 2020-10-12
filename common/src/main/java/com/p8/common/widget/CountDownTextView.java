package com.p8.common.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * author : WX.Y
 * date : 2020/9/15 11:33
 * description :
 */
public class CountDownTextView extends AppCompatTextView {
    CountDownTimer countDownTimer;

    public CountDownTextView(@NonNull Context context) {
        super(context);
    }

    public CountDownTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startCountDown(){
        this.setClickable(false);
        if(countDownTimer == null){
            countDownTimer = new CountDownTimer(60000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    CountDownTextView.this.setText(String.format("%ds", millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                    CountDownTextView.this.setText("获取验证码");
                    CountDownTextView.this.setClickable(true);
                }
            };
        }
        countDownTimer.start();
    }

    public void stopCountDown(){
        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }
}

