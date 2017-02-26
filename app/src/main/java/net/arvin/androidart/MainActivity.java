package net.arvin.androidart;

import android.os.Bundle;
import android.util.Log;

import net.arvin.afbaselibrary.uis.activities.BaseHeaderActivity;
import net.arvin.androidart.anim.AnimActivity;
import net.arvin.androidart.broadcast.BroadcastActivity;
import net.arvin.androidart.gen.DaoSession;
import net.arvin.androidart.handler.HandlerActivity;
import net.arvin.androidart.intent.IntentActivity;
import net.arvin.androidart.multiProcess.ProcessActivity;
import net.arvin.androidart.provider.ProviderActivity;
import net.arvin.androidart.service.ServiceActivity;
import net.arvin.androidart.toast.ToastActivity;

import butterknife.OnClick;

public class MainActivity extends BaseHeaderActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isShowBackView() {
        return false;
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.app_name);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        App.count = 1;
        Log.d("MainActivity", "count is " + App.count);
    }

    @OnClick(R.id.tv_intent_analyze)
    public void toIntentAnalyze() {
        startActivity(IntentActivity.class);
    }

    @OnClick(R.id.tv_custom_toast)
    public void toToast() {
        startActivity(ToastActivity.class);
    }

    @OnClick(R.id.tv_multi_process)
    public void toMultiProcess() {
        Bundle bundle = new Bundle();
        bundle.putInt(ProcessActivity.KEY_1, App.count);
        startActivity(ProcessActivity.class, bundle);

    }

    @OnClick(R.id.tv_anim)
    public void toAnim() {
        startActivity(AnimActivity.class);
    }

//    @OnClick(R.id.tv_handler)
//    public void toHandler() {
//        startActivity(HandlerActivity.class);
//    }

    @OnClick(R.id.tv_service)
    public void toService() {
        startActivity(ServiceActivity.class);
    }

    @OnClick(R.id.tv_broadcast)
    public void toBroadcast() {
        startActivity(BroadcastActivity.class);
    }

    @OnClick(R.id.tv_provider)
    public void toProvider() {
        startActivity(ProviderActivity.class);
    }

    /**
     * 第一个显示的界面记得重写onBackPressed方法，去掉动画
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
