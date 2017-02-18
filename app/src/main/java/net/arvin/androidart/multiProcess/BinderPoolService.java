package net.arvin.androidart.multiProcess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import net.arvin.androidart.multiProcess.aidlImpl.BinderPoolImpl;

/**
 * created by arvin on 17/2/18 17:52
 * email：1035407623@qq.com
 */
public class BinderPoolService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return new BinderPoolImpl();
    }
}
