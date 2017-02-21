package net.arvin.androidart.handler;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;

import net.arvin.afbaselibrary.listeners.IWeakHandler;
import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.afbaselibrary.utils.WeakHandler;
import net.arvin.androidart.R;

/**
 * created by arvin on 17/2/20 21:41
 * email：1035407623@qq.com
 */
public class HandlerActivity extends BaseSwipeBackActivity implements IWeakHandler {
    private WeakHandler mHandler;

    @Override
    public int getContentViewId() {
        return R.layout.activity_hanlder;
    }

    @Override
    protected String getTitleText() {
        return "消息机制";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mHandler = new WeakHandler(this);
        mHandler.sendEmptyMessageDelayed(0,1000);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showToast("post呵呵");
            }
        },3000);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == 0) {
            showToast("send呵呵");
        }
    }

}
