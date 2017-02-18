// IBinderPool.aidl
package net.arvin.androidart.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
    * @param binderCode, the unique token of specific Binder<br/>
    * @return specific Binder who's token is binderCode.
    */
    IBinder queryBinder(int binderCode);
}
