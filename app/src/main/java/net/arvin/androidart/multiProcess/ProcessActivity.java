package net.arvin.androidart.multiProcess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.App;
import net.arvin.androidart.R;

import butterknife.BindView;

/**
 * created by arvin on 17/2/8 20:54
 * email：1035407623@qq.com
 */
public class ProcessActivity extends BaseSwipeBackActivity {
    @BindView(R.id.tv_service_start_status)
    TextView tvStatus;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            tvStatus.setText("计算服务已开启");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    protected String getTitleText() {
        return "多进程通信";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_process;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Log.d("ProcessActivity", "count is " + App.count);
        //和MainActivity打印的对比可以知道，对于每个进程，都创建了一份自己的内存空间，所以同一个变量，在内存中都有着自己的值
        bindService(new Intent(this, IntegerAddService.class), conn, Context.BIND_AUTO_CREATE);

        /**
         * 首先要了解，编译生成的AIDL文件的结构，以及其意义！
         * 1、首先在要通信的两个进程中，添加相同路径和内容的AIDL文件，并编译；（路径与内容相同，是不是就代表了完全相同）
         * 2、创建对应的Service，在onBind中返回对应的Stub（这个就是IBinder）;
         * 3、bindService,在ServiceConnection中获取到IBinder对象，从而获取到对应的接口；
         * 4、拿到接口后，就相当于回调一样简单；
         */
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
