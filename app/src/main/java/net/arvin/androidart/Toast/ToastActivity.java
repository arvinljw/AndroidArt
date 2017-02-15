package net.arvin.androidart.toast;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.afbaselibrary.utils.ScreenUtil;
import net.arvin.androidart.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by arvin on 17/2/8 15:45
 * email：1035407623@qq.com
 */
public class ToastActivity extends BaseSwipeBackActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.ed_toast)
    EditText edToast;
    @BindView(R.id.rg_gravity)
    RadioGroup rgGravity;
    @BindView(R.id.rg_during)
    RadioGroup rgDuring;

    private int mDuring;
    private int mGravity;
    private int mY;

    @Override
    protected String getTitleText() {
        return "自定义Toast";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_toast;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mDuring = Toast.LENGTH_SHORT;
        mGravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        mY = ScreenUtil.dp2px(64);

        rgGravity.setOnCheckedChangeListener(this);
        rgDuring.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_top:
                mGravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                mY = ScreenUtil.dp2px(64);
                break;
            case R.id.rb_center:
                mGravity = Gravity.CENTER;
                mY = ScreenUtil.dp2px(0);
                break;
            case R.id.rb_bottom:
                mGravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                mY = ScreenUtil.dp2px(64);
                break;
            case R.id.rb_long:
                mDuring = Toast.LENGTH_LONG;
                break;
            case R.id.rb_short:
                mDuring = Toast.LENGTH_SHORT;
                break;
        }
    }

    @OnClick(R.id.tv_show)
    public void TopShowToast() {
        makeText().show();
//        new Thread(new Runnable() {//显示有问题的
//            @Override
//            public void run() {
//                Looper.prepare();
//                showToast("子线程的Toast");
//            }
//        }).start();
    }

    private Toast makeText() {
        Toast toast = new Toast(this);
        toast.setDuration(mDuring);
        String msg = edToast.getText().toString().trim();
        if (msg.length() == 0) {
            msg = edToast.getHint().toString().trim();
        }
        toast.setView(getToastView(msg));
        toast.setGravity(mGravity, 0, mY);
        return toast;
    }

    private View getToastView(String message) {
        LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.layout_toast, null);
        TextView tvToast = (TextView) view.findViewById(R.id.tv_toast);
        tvToast.setText(message);
        return view;
    }

}
