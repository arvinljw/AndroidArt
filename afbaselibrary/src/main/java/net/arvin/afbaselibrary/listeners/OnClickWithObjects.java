package net.arvin.afbaselibrary.listeners;

import android.view.View;

/**
 * created by arvin on 16/11/3 17:16
 * email：1035407623@qq.com
 */
public abstract class OnClickWithObjects implements View.OnClickListener {
    private Object[] objects;

    public OnClickWithObjects(Object... objects) {
        this.objects = objects;
    }

    @Override
    public void onClick(View view) {
        onClick(view, objects);
    }

    public abstract void onClick(View view, Object[] objects);
}
