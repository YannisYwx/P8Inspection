package com.p8.common.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * author : WX.Y
 * date : 2020/9/14 18:18
 * description :
 */
public class TimerCount extends CountDownTimer {

    private TextView tvCountDown;
    private String defaultMsg;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimerCount(TextView tvCountDown, String defaultMsg, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.tvCountDown = tvCountDown;
        this.defaultMsg = defaultMsg;
        tvCountDown.setText(defaultMsg);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvCountDown.setText(String.format("%ds", millisUntilFinished / 1000));
        tvCountDown.setClickable(false);
    }

    @Override
    public void onFinish() {
        tvCountDown.setText(defaultMsg);
        tvCountDown.setClickable(true);
    }
}

