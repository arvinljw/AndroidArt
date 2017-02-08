package net.arvin.androidart;

import android.os.Bundle;
import android.widget.Toast;

import net.arvin.afbaselibrary.uis.activities.BaseHeaderActivity;
import net.arvin.androidart.intent.IntentActivity;
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
    }

    @OnClick(R.id.tv_intent_analyze)
    public void toIntentAnalyze() {
        startActivity(IntentActivity.class);
    }

    @OnClick(R.id.tv_custom_toast)
    public void toToast() {
        startActivity(ToastActivity.class);
    }

    /**
     * 第一个显示的界面记得重写onBackPressed方法，去掉动画
     */
    @Override
    public void onBackPressed() {
        finish();
    }
}
