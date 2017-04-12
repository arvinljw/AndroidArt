package net.arvin.androidart.jni;

/**
 * created by arvin on 17/3/2 11:33
 * email：1035407623@qq.com
 */
public class NdkTest {
    static {
        System.loadLibrary("NdkTest");
    }

    public static native String getSomethingFromNDK();

    public static native int reduce(int params1, int params2);
}
