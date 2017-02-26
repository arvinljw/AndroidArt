package net.arvin.androidart.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;
import net.arvin.androidart.multiProcess.aidlImpl.IntegerAddImpl;

import butterknife.OnClick;

/**
 * created by arvin on 17/2/23 22:55
 * email：1035407623@qq.com
 */
public class TestServiceLifeActivity extends BaseSwipeBackActivity {

    private IntegerAddImpl mService;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = (IntegerAddImpl) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;//内存不足，关闭service时调用
        }
    };

    private boolean isStartService = false;

    @Override
    public int getContentViewId() {
        return R.layout.activity_test_service_life;
    }

    @Override
    protected String getTitleText() {
        return "调试Service生命周期";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @OnClick(R.id.tv_start)
    public void onStartService() {
        if (!isStartService) {
            isStartService = true;
            startService(new Intent(this, LifeService.class));
        }
    }

    @OnClick(R.id.tv_stop)
    public void onStopService() {
        if (isStartService) {
            isStartService = false;
            stopService(new Intent(this, LifeService.class));
        }
    }


    @OnClick(R.id.tv_bind)
    public void onBindService() {
        if (mService == null) {
            bindService(new Intent(this, LifeService.class), conn, Context.BIND_AUTO_CREATE);
        }
    }

    @OnClick(R.id.tv_unbind)
    public void onUnbindService() {
        if (mService != null) {
            unbindService(conn);
            mService = null;
        }
    }

    @OnClick(R.id.tv_open)
    public void onOpen() {
        startActivity(TestServiceLifeActivity.class);
    }

    @Override
    protected void onDestroy() {
        onUnbindService();
        onStopService();
        super.onDestroy();
    }
}
