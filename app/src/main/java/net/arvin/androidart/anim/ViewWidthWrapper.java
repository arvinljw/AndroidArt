package net.arvin.androidart.anim;

import android.view.View;

/**
 * created by arvin on 17/2/19 21:11
 * email：1035407623@qq.com
 */
public class ViewWidthWrapper extends BaseViewWrapper {
    public ViewWidthWrapper(View mTarget) {
        super(mTarget);
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }

    public int getWidth() {
        return mTarget.getWidth();
    }

}
