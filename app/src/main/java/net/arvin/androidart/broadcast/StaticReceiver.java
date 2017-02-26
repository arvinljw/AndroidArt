package net.arvin.androidart.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * created by arvin on 17/2/24 00:02
 * email：1035407623@qq.com
 */
public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "静态注册的广播", Toast.LENGTH_SHORT).show();
//        abortBroadcast();//拦截掉
    }
}
