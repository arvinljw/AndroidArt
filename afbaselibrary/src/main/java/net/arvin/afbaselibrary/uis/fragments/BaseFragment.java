package net.arvin.afbaselibrary.uis.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.arvin.afbaselibrary.listeners.ICheckPerm;
import net.arvin.afbaselibrary.mvp.presenters.BasePresenter;
import net.arvin.afbaselibrary.mvp.views.IBaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

/**
 * created by arvin on 16/10/24 15:02
 * email：1035407623@qq.com
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected View mRoot;
    protected CompositeSubscription mCompositeSubscription;
    private Unbinder unbinder;
    private BasePresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = LayoutInflater.from(getActivity()).inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, mRoot);
        mCompositeSubscription = new CompositeSubscription();
        mPresenter = new BasePresenter(getActivity(), mRoot);
        init(savedInstanceState);
        return mRoot;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        mCompositeSubscription.unsubscribe();
        super.onDestroyView();
    }

    @Override
    public final <T extends View> T getView(int id) {
        return mPresenter.getView(id);
    }

    @Override
    public void showToast(String message) {
        mPresenter.showToast(message);
    }

    @Override
    public void showProgress(String message) {
        mPresenter.showProgressDialog(message);
    }

    @Override
    public void hideProgress() {
        mPresenter.hideProgress();
    }

    @Override
    public void startActivity(Class clazz) {
        mPresenter.startActivity(clazz, null);
    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        mPresenter.startActivity(clazz, bundle);
    }

    @Override
    public void startActivityForResult(Class clazz, int requestCode) {
        mPresenter.startActivityForResult(clazz, requestCode, null);
    }

    @Override
    public void startActivityForResult(Class clazz, int requestCode, Bundle bundle) {
        mPresenter.startActivityForResult(clazz, requestCode, bundle);
    }

    @Override
    public void checkPermission(ICheckPerm listener, String resString, String... mPerms) {
        mPresenter.checkPermission(listener, resString, mPerms);
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mPresenter.isBackFromSetting(requestCode, resultCode, data)) {
            backFromSetting();
        }
    }

    protected void backFromSetting() {
    }

    @Override
    public abstract int getContentViewId();

    @Override
    public abstract void init(Bundle savedInstanceState);
}
