package net.arvin.androidart.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import net.arvin.afbaselibrary.listeners.IWeakHandler;
import net.arvin.afbaselibrary.utils.WeakHandler;
import net.arvin.androidart.R;

/**
 * created by arvin on 17/2/23 23:29
 * email：1035407623@qq.com
 */
public class ForegroundService extends Service implements IWeakHandler {
    /**
     * id不可设置为0,否则不能设置为前台service
     */
    private static final int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 1;

    public static final int START_FORE = 1001;
    public static final int STOP_FORE = 1002;

    private boolean isRemove = false;//是否需要移除

    private Messenger mMessenger;

    /**
     * Notification
     */
    public void createNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setShowWhen(true);
        builder.setContentTitle("这是一个前台服务");
        builder.setContentText("你好啊～");
        Notification notification = builder.build();
        startForeground(NOTIFICATION_DOWNLOAD_PROGRESS_ID, notification);
    }

    @Override
    public void onDestroy() {
        if (isRemove) {
            stopForeground(true);
            isRemove = false;
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mMessenger = new Messenger(new WeakHandler(this));
        return mMessenger.getBinder();
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case START_FORE:
                if (!isRemove) {
                    createNotification();
                    isRemove = true;
                }
                break;
            case STOP_FORE:
                if (isRemove) {
                    stopForeground(true);
                    isRemove = false;
                }
                break;
        }
    }
}
