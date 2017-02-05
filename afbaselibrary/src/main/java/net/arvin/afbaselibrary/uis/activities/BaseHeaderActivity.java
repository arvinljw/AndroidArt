package net.arvin.afbaselibrary.uis.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import net.arvin.afbaselibrary.R;

/**
 * created by arvin on 16/10/24 15:22
 * email：1035407623@qq.com
 * 带有标题和返回按钮的activity包含使用id:pre_tv_title（TextView）和pre_v_back（View）的组件即可
 */
public abstract class BaseHeaderActivity extends BaseActivity {
    protected TextView tvTitle;
    protected View vBack;

    @Override
    public void init(Bundle savedInstanceState) {
        initHeader();
        initViews(savedInstanceState);
    }

    private void initHeader() {
        try {
            tvTitle = getView(R.id.pre_tv_title);
            tvTitle.setText(getTitleText());
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTitleClicked(v);
                }
            });
        } catch (Exception e) {
            Logger.w("未设置标题id~");
        }

        try {
            vBack = getView(R.id.pre_v_back);
            vBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            vBack.setVisibility(isShowBackView() ? View.VISIBLE : View.GONE);
        } catch (Exception e) {
            Logger.w("未设置返回图标id~");
        }
    }

    protected boolean isShowBackView() {
        return true;
    }

    protected void onTitleClicked(View view) {
    }

    protected abstract String getTitleText();

    protected abstract void initViews(Bundle savedInstanceState);
}
