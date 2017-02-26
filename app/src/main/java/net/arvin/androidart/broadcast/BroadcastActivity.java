package net.arvin.androidart.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;

import butterknife.OnClick;

/**
 * created by arvin on 17/2/24 00:00
 * email：1035407623@qq.com
 */
public class BroadcastActivity extends BaseSwipeBackActivity {
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showToast("动态注册的广播");
        }
    };
    final String DYNAMICS_ACTION = "net.arvin.androidart.broadcast.dynamics";
    final String STATIC_ACTION = "net.arvin.androidart.broadcast.static";

    @Override
    public int getContentViewId() {
        return R.layout.activity_broadcast;
    }

    @Override
    protected String getTitleText() {
        return "广播相关";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        registerReceiver(mReceiver, getFilter());
    }

    private IntentFilter getFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DYNAMICS_ACTION);
        return filter;
    }

    @OnClick(R.id.tv_static_broadcast)
    public void sendStaticBroadcast() {
        Intent intent = new Intent();
        intent.setAction(STATIC_ACTION);
        sendBroadcast(intent);
    }

    @OnClick(R.id.tv_dynamics_broadcast)
    public void sendDynamicsBroadcast() {
        Intent intent = new Intent();
        intent.setAction(DYNAMICS_ACTION);
        sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
