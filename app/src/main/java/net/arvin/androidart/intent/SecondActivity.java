package net.arvin.androidart.intent;

import android.os.Bundle;
import android.widget.TextView;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;

import butterknife.BindView;

public class SecondActivity extends BaseSwipeBackActivity {
    @BindView(R.id.tv_name)
    TextView tvName;

    @Override
    public int getContentViewId() {
        return R.layout.activity_name;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvName.setText(getActivityName());
    }

    @Override
    protected String getTitleText() {
        return "";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    protected String getActivityName() {
        return "SecondActivity";
    }
}
