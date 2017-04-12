package net.arvin.androidart.multiProcess;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import net.arvin.androidart.multiProcess.aidlImpl.BinderPoolImpl;

/**
 * created by arvin on 17/2/18 17:52
 * email：1035407623@qq.com
 */
public class BinderPoolService extends Service {
    private BinderPoolImpl binderPool;

    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("net.arvin.androidart.permission.BinderPoolService");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            return null;
//        }
        binderPool = new BinderPoolImpl() {
            @Override
            public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
                int check = checkCallingOrSelfPermission("net.arvin.androidart.permission.BinderPoolService");
                if (check == PackageManager.PERMISSION_DENIED) {
                    return false;
                }

                String packageName;
                String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
                if (packages != null && packages.length > 0) {
                    packageName = packages[0];
                    if (!packageName.startsWith("net.arvin")) {
                        return false;
                    }
                }
                return super.onTransact(code, data, reply, flags);
            }
        };
        return binderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binderPool.onDestroy();
    }
}
