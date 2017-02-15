package net.arvin.androidart;

import android.app.Application;
import android.util.Log;

import net.arvin.afbaselibrary.utils.AFUtil;

/**
 * created by arvin on 17/2/8 21:03
 * email：1035407623@qq.com
 */
public class App extends Application {
    public static int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("App start", "process name is " + AFUtil.getProcessName(this));
    }

}
