package net.arvin.androidart.multiProcess.aidlImpl;

import android.os.IBinder;
import android.os.RemoteException;

import net.arvin.androidart.aidl.IBinderPool;

/**
 * created by arvin on 17/2/18 18:22
 * email：1035407623@qq.com
 */
public class BinderPoolImpl extends IBinderPool.Stub {
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_PERSON_COUNT = 1;

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BINDER_PERSON_COUNT: {
                binder = new PersonCountImpl();
                break;
            }
            case BINDER_COMPUTE: {
                binder = new IntegerAddImpl();
                break;
            }
            default:
                break;
        }
        return binder;
    }
}
