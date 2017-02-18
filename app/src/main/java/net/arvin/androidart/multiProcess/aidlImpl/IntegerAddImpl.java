package net.arvin.androidart.multiProcess.aidlImpl;

import android.os.RemoteException;

import net.arvin.androidart.aidl.IIntegerAdd;

/**
 * created by arvin on 17/2/18 17:56
 * email：1035407623@qq.com
 */
public class IntegerAddImpl extends IIntegerAdd.Stub {
    @Override
    public int add(int num1, int num2) throws RemoteException {
        return num1 + num2;
    }
}
