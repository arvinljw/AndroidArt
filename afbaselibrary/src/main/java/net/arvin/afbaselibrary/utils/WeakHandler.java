package net.arvin.afbaselibrary.utils;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import net.arvin.afbaselibrary.listeners.IWeakHandler;

import java.lang.ref.WeakReference;

/**
 * Created by arvin on 2016/2/2 16:48.
 * 避免内存泄露使用弱引用
 */
@SuppressWarnings("all")
public class WeakHandler extends Handler {
    private WeakReference<IWeakHandler> mActivity;

    public WeakHandler(IWeakHandler activity) {
        mActivity = new WeakReference<>(activity);
    }

    public WeakHandler(Looper looper, IWeakHandler activity) {
        super(looper);
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mActivity != null) {
            IWeakHandler weakHandleInterface = mActivity.get();
            if (weakHandleInterface != null) {
                weakHandleInterface.handleMessage(msg);
            }
        }
    }

}
