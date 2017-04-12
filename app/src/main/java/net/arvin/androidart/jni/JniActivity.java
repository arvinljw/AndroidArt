package net.arvin.androidart.jni;

import android.os.Bundle;
import android.view.View;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by arvin on 17/3/2 14:22
 * email：1035407623@qq.com
 */
public class JniActivity extends BaseSwipeBackActivity {
    @Override
    protected String getTitleText() {
        return "JNI的使用";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_jni;
    }

    @OnClick({R.id.tv_reduce, R.id.tv_get_something})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reduce:
                showToast("JNI reduce " + NdkTest.reduce(12, 5));
                break;
            case R.id.tv_get_something:
                showToast(NdkTest.getSomethingFromNDK());
                break;
        }
    }
}
