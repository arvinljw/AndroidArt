package net.arvin.androidart;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.arvin.afbaselibrary.utils.AFUtil;

/**
 * created by arvin on 17/2/8 21:03
 * email：1035407623@qq.com
 */
public class App extends Application {
    public static int count = 0;
    public static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Log.d("App start", "process name is " + AFUtil.getProcessName(this));
//        setDatabase();
    }

    public static App getInstance() {
        return sInstance;
    }

//    /**
//     * 设置greenDao
//     */
//    private void setDatabase() {
//        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
//        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
//        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
//        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
//        mHelper = new DaoMaster.DevOpenHelper(this, "art-db", null);
//        db = mHelper.getWritableDatabase();
//        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
//        mDaoMaster = new DaoMaster(db);
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }

}
