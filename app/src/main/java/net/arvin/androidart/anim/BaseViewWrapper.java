package net.arvin.androidart.anim;

import android.view.View;

/**
 * created by arvin on 17/2/19 21:10
 * email：1035407623@qq.com
 */
public abstract class BaseViewWrapper {
    protected View mTarget;

    public BaseViewWrapper(View mTarget) {
        this.mTarget = mTarget;
    }
}
