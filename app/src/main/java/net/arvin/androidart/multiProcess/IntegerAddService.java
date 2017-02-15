package net.arvin.androidart.multiProcess;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import net.arvin.androidart.aidl.IIntegerAdd;

/**
 * created by arvin on 17/2/14 21:46
 * email：1035407623@qq.com
 */
public class IntegerAddService extends Service {
    private IBinder mBinder = new IIntegerAdd.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            return num1 + num2;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
