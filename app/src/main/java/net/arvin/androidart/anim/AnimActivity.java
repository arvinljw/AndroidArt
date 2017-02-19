package net.arvin.androidart.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.afbaselibrary.utils.ScreenUtil;
import net.arvin.androidart.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by arvin on 17/2/19 19:58
 * email：1035407623@qq.com
 */
public class AnimActivity extends BaseSwipeBackActivity {
    @BindView(R.id.v_square)
    View vSquare;
    @BindView(R.id.v_change_bg)
    View vChangeBg;
    @BindView(R.id.v_width_change)
    View vWidthChange;
    private AnimatorSet bgAndTransYSet;
    private ObjectAnimator widthAnim;
    private ViewWidthWrapper widthWrapper;

    @Override
    public int getContentViewId() {
        return R.layout.activity_anim;
    }

    @Override
    protected String getTitleText() {
        return "属性动画";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @OnClick(R.id.v_square)
    public void onSquareClicked() {
        //默认时间为300ms
//        ObjectAnimator.ofFloat(vSquare, "translationY", vSquare.getHeight()).start();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(vSquare, "translationY", 0, vSquare.getHeight(), vSquare.getHeight() / 2,
                vSquare.getHeight() * 2);
        translationY.setDuration(3000);
        translationY.setInterpolator(new LinearInterpolator());

        translationY.start();
    }

    @OnClick(R.id.v_change_bg)
    public void onChangeBgClicked() {
        if (bgAndTransYSet == null) {
            ObjectAnimator colorAnim = ObjectAnimator.ofInt(vChangeBg, "backgroundColor", getResources().getColor(R.color.colorAccent),
                    getResources().getColor(R.color.colorPrimary));
            colorAnim.setDuration(2000);
            colorAnim.setEvaluator(new ArgbEvaluator());//颜色变化推荐使用这个插值器
            colorAnim.setRepeatCount(ValueAnimator.INFINITE);
            colorAnim.setRepeatMode(ValueAnimator.REVERSE);

            ObjectAnimator translationY = ObjectAnimator.ofFloat(vChangeBg, "translationY", vSquare.getHeight());
            translationY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                }
            });
            translationY.setRepeatCount(ValueAnimator.INFINITE);

            bgAndTransYSet = new AnimatorSet();
            bgAndTransYSet.addListener(new AnimListener());
            bgAndTransYSet.playTogether(colorAnim, translationY);
        }
        if (!bgAndTransYSet.isStarted()) {
            bgAndTransYSet.start();
        }
    }

    @OnClick(R.id.v_width_change)
    public void onWidthChange() {
        widthWrapper = new ViewWidthWrapper(vWidthChange);
        if (widthAnim == null) {
            widthAnim = ObjectAnimator.ofInt(widthWrapper, "width", 2 * widthWrapper.getWidth());
            widthAnim.setRepeatCount(ValueAnimator.INFINITE);
            widthAnim.setRepeatMode(ValueAnimator.REVERSE);
        }
        if (!widthAnim.isStarted()) {
            widthAnim.start();
        }
    }

    @OnClick(R.id.tv_reset)
    public void resetAnim() {
        ObjectAnimator.ofFloat(vSquare, "translationY", 0).start();
        if (bgAndTransYSet != null) {
            bgAndTransYSet.cancel();
            vChangeBg.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            ObjectAnimator.ofFloat(vChangeBg, "translationY", 0).start();
        }
        if (widthAnim != null) {
            widthAnim.cancel();
            ObjectAnimator.ofInt(widthWrapper, "width", widthWrapper.getWidth()).start();
        }
    }

public class AnimListener implements Animator.AnimatorListener{

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}

}
