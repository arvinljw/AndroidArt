package net.arvin.androidart.provider;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import net.arvin.afbaselibrary.nets.callbacks.AbsAPICallback;
import net.arvin.afbaselibrary.nets.exceptions.ApiException;
import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;
import net.arvin.greendao.entities.User;
import net.arvin.greendao.gen.UserDao;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 17/2/26 14:01
 * email：1035407623@qq.com
 */
public class AddUserActivity extends BaseSwipeBackActivity {
    public static final String KEY = "key";
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_age)
    EditText edAge;
    @BindView(R.id.tv_insert)
    TextView tvInsert;

    private User currentUser;

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_user;
    }

    @Override
    protected String getTitleText() {
        return "添加用户";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(KEY)) {
            currentUser = extras.getParcelable(KEY);
            setData();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setData() {
        if (currentUser != null) {
            String name = currentUser.getName();
            edName.setText(name);
            edName.setSelection(name.length());

            edAge.setText("" + currentUser.getAge());

            tvInsert.setText("修改");
            tvTitle.setText("修改用户");
        }
    }

    @OnClick(R.id.tv_insert)
    public void onClick() {
        String age = edAge.getText().toString().trim();
        String name = edName.getText().toString().trim();
        if (name.length() == 0) {
            showToast("请输入姓名");
            return;
        }
        if (age.length() == 0) {
            showToast("请输入年龄");
            return;
        }

        ContentValues values = new ContentValues();
        values.put(UserDao.Properties.Name.columnName, name);
        values.put(UserDao.Properties.Age.columnName, Integer.valueOf(age));

        if (currentUser == null) {
            addUser(values);
            return;
        }

        updateUser(values);
    }

    private void updateUser(ContentValues values) {
        Observable.just(values).map(new Func1<ContentValues, Integer>() {
            @Override
            public Integer call(ContentValues values) {
                Uri uri = Uri.parse(UserProvider.CONTENT_URI_STRING + "/" + currentUser.getId());
                return getContentResolver().update(uri, values, null, null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<Integer>() {
                    @Override
                    protected void onResultError(ApiException ex) {
                        showToast("修改失败");
                    }

                    @Override
                    public void onNext(Integer result) {
                        if (result == 1) {
                            showToast("修改成功");
                            setResult(RESULT_OK);
                            onBackPressed();
                        } else {
                            showToast("修改失败");
                        }
                    }
                });
    }

    private void addUser(ContentValues values) {
        Observable.just(values).map(new Func1<ContentValues, Uri>() {
            @Override
            public Uri call(ContentValues values) {
                return getContentResolver().insert(UserProvider.CONTENT_URI, values);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<Uri>() {
                    @Override
                    protected void onResultError(ApiException ex) {
                        showToast("添加失败");
                    }

                    @Override
                    public void onNext(Uri uri) {
                        if (uri != null) {
                            showToast("添加成功");
                            setResult(RESULT_OK);
                            onBackPressed();
                        } else {
                            showToast("添加失败");
                        }
                    }
                });
    }
}
