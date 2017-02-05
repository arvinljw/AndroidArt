package net.arvin.androidart;

import android.os.Bundle;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.androidart.intent.IntentActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
    }

    @OnClick(R.id.tv_intent_analyze)
    public void toSec() {
        startActivity(IntentActivity.class);
    }
}
