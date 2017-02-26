package net.arvin.androidart.service;

import android.os.Bundle;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;

import butterknife.OnClick;

/**
 * created by arvin on 17/2/23 23:37
 * email：1035407623@qq.com
 */
public class ServiceActivity extends BaseSwipeBackActivity {
    @Override
    protected String getTitleText() {
        return "服务相关";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_service;
    }

    @OnClick(R.id.tv_service_life)
    public void onServiceLife(){
        startActivity(TestServiceLifeActivity.class);
    }
    @OnClick(R.id.tv_foreground_service)
    public void onForegroundService(){
        startActivity(ForegroundServiceActivity.class);
    }
}
