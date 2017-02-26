package net.arvin.androidart.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import net.arvin.androidart.multiProcess.aidlImpl.IntegerAddImpl;

/**
 * created by arvin on 17/2/23 22:22
 * email：1035407623@qq.com
 */
public class LifeService extends Service {
    final String TAG = "LifeService";

    @Override
    public void onCreate() {
        super.onCreate();
        //第一次被创建，不管是启动还是start都会调用，但只调用一次。
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //每次startService时都会调用
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        //第一次被绑定时调用
        Log.d(TAG, "onBind");//返回null，则onServiceConnected不会回调
        return new IntegerAddImpl();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //最后一个解绑的时候调用
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
