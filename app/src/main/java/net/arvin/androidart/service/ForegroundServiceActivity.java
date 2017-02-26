package net.arvin.androidart.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;

import butterknife.OnClick;

/**
 * created by arvin on 17/2/23 23:33
 * email：1035407623@qq.com
 */
public class ForegroundServiceActivity extends BaseSwipeBackActivity {
    private Messenger mService;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_notify_service;
    }

    @Override
    protected String getTitleText() {
        return "前台服务测试";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bindService(new Intent(this, ForegroundService.class), conn, Context.BIND_AUTO_CREATE);
    }

    @OnClick(R.id.tv_start)
    public void onStartFore() {
        Message msg = new Message();
        msg.what = ForegroundService.START_FORE;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_stop)
    public void onStopFore() {
        Message msg = new Message();
        msg.what = ForegroundService.STOP_FORE;
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
