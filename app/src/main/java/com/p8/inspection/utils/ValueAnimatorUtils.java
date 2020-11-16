package com.p8.inspection.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.SizeUtils;

public class ValueAnimatorUtils {

    private boolean isMoving = false;

    public boolean isMoving() {
        return isMoving;
    }

    public void monitorCityOpenOrClose(boolean isLeft, final View layout, final View icon, final View otherLayout, final View otherIcon
            , final FrameLayout maskLayout, int duration) {
        if (isMoving) {
            return;
        }
        maskLayout.setAlpha(0.8f);
        int height = AdaptScreenUtils.pt2Px(isLeft ? 1240 : 620);

        if (layout.getVisibility() == View.GONE) {
            int closeHeight;
            int openHeight;
            if (otherLayout != null && otherLayout.getVisibility() == View.VISIBLE) {
                closeHeight = AdaptScreenUtils.pt2Px(isLeft ? 620 : 1240);
                openHeight = AdaptScreenUtils.pt2Px(isLeft ? 1240 : 620);
                monitorClose(closeHeight, otherLayout, otherIcon, duration, new Animator.AnimatorListener() {

                    @Override
                    public void onAnimationStart(Animator animator) {
                        isMoving = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        isMoving = false;
                        otherLayout.setVisibility(View.GONE);
                        monitorOpen(openHeight, layout, icon, duration, null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            } else {
                showMask(maskLayout, duration);
                maskLayout.setVisibility(View.VISIBLE);
                monitorOpen(height, layout, icon, duration, null);
            }
        } else {
            monitorClose(height, layout, icon, duration, new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animator) {
                    isMoving = true;
                    dismissMask(maskLayout, duration);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isMoving = false;
                    maskLayout.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    //请求泊位信息
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    public void monitorCityOpenOrClose2(final View layout, final View icon, final FrameLayout maskLayout, int duration) {
        if (isMoving) {
            return;
        }
        int height = SizeUtils.dp2px(220);
        if (layout.getVisibility() == View.GONE) {
            maskLayout.setVisibility(View.VISIBLE);

            monitorOpen(height, layout, icon, duration, null);
        } else {
            monitorClose(height, layout, icon, duration, new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animator) {
                    isMoving = true;
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isMoving = false;
                    maskLayout.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    //请求泊位信息
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    public void monitorOpen(int height, final View layout, final View icon, int duration, Animator.AnimatorListener animatorListener) {
        layout.setVisibility(View.VISIBLE);

        ObjectAnimator layoutAnimator = ObjectAnimator.ofFloat(layout, "translationY", -height, 0);
        if (animatorListener != null) {
            layoutAnimator.addListener(animatorListener);
        }
        layoutAnimator.setDuration(duration);
        layoutAnimator.start();

        if (icon != null) {//当且仅当图标不为空的情况下做旋转
            ObjectAnimator iconAnimator = ObjectAnimator.ofFloat(icon, "rotation", 0f, 180f);
            iconAnimator.setDuration(duration);
            iconAnimator.start();
        }
    }

    public static void monitorClose(int height, final View layout, final View icon, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator layoutAnimator = ObjectAnimator.ofFloat(layout, "translationY", 0, -height);
        if (animatorListener != null) {
            layoutAnimator.addListener(animatorListener);
        }
        layoutAnimator.setDuration(duration);
        layoutAnimator.start();

        if (icon != null) {//当且仅当图标不为空的情况下做旋转
            ObjectAnimator iconAnimator = ObjectAnimator.ofFloat(icon, "rotation", 180f, 0);
            iconAnimator.setDuration(duration);
            iconAnimator.start();
        }
    }

    public static void dismissMask(View view, int duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 0f);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    public static void showMask(View view, int duration) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.8f);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
}
