package net.arvin.afbaselibrary.mvp.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.arvin.afbaselibrary.listeners.ICheckPerm;

/**
 * created by arvin on 16/10/24 14:48
 * email：1035407623@qq.com
 * 所有View模块的父类
 */
public interface IBaseView {
    int getContentViewId();

    void init(Bundle savedInstanceState);

    <T extends View> T getView(int id);

    void showToast(String message);

    void showProgress(String message);

    void hideProgress();

    void startActivity(Class clazz);

    void startActivity(Class clazz, Bundle bundle);

    void startActivityForResult(Class clazz, int requestCode);

    void startActivityForResult(Class clazz, int requestCode, Bundle bundle);

    void checkPermission(ICheckPerm listener, String resString, String... mPerms);
}
