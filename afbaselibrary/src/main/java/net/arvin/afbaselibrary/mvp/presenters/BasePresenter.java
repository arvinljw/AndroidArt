package net.arvin.afbaselibrary.mvp.presenters;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import net.arvin.afbaselibrary.R;
import net.arvin.afbaselibrary.listeners.ICheckPerm;
import net.arvin.afbaselibrary.utils.EasyPermissionUtil;

import java.util.List;

/**
 * created by arvin on 16/10/24 14:47
 * email：1035407623@qq.com
 */
public class BasePresenter implements EasyPermissionUtil.PermissionCallbacks {
    private FragmentActivity mActivity;
    private View mRootView;
    private ProgressDialog progressDialog;
    /**
     * 权限回调接口
     */
    private ICheckPerm mListener;
    private static final int RC_PERM = 123;

    public BasePresenter(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    public BasePresenter(FragmentActivity mActivity, View rootView) {
        this.mActivity = mActivity;
        this.mRootView = rootView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        if (mRootView != null) {
            try {
                return (T) mRootView.findViewById(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        try {
            return (T) mActivity.findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void showToast(String message) {
        if (TextUtils.isEmpty(message) || mActivity == null) {
            return;
        }
        Toast.makeText(mActivity, message, message.length() > 8 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }


    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
        }
        progressDialog.setMessage(message != null ? message : "");
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
        mActivity.overridePendingTransition(R.anim.ui_right_in, 0);
    }

    public void startActivityForResult(Class clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(R.anim.ui_right_in, 0);
    }


    public void checkPermission(ICheckPerm listener, String resString, String... mPerms) {
        mListener = listener;
        if (EasyPermissionUtil.hasPermissions(mActivity, mPerms)) {
            if (mListener != null)
                mListener.agreeAllPermission();
        } else {
            EasyPermissionUtil.requestPermissions(this, resString,
                    RC_PERM, mPerms);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public boolean isBackFromSetting(int requestCode, int resultCode, Intent data) {
        return requestCode == EasyPermissionUtil.SETTINGS_REQ_CODE;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // 只同意了部分权限
    }

    @Override
    public void onPermissionsAllGranted() {
        if (mListener != null)
            mListener.agreeAllPermission();//同意了全部权限的回调
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        EasyPermissionUtil.checkDeniedPermissionsNeverAskAgain(this,
                "当前应用缺少必要权限。\n请点击\"设置\"-\"权限\"-打开所需权限。",
                R.string.setting, R.string.cancel, null, perms);
    }

    public void onDestroy() {
        mActivity = null;
        mRootView = null;
    }

}
