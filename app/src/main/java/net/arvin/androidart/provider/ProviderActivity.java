package net.arvin.androidart.provider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.arvin.afbaselibrary.nets.callbacks.AbsAPICallback;
import net.arvin.afbaselibrary.nets.exceptions.ApiException;
import net.arvin.afbaselibrary.uis.activities.BaseRefreshLoadingActivity;
import net.arvin.afbaselibrary.uis.adapters.BaseAdapter;
import net.arvin.afbaselibrary.uis.adapters.MultiItemTypeAdapter;
import net.arvin.afbaselibrary.uis.adapters.holders.CommonHolder;
import net.arvin.androidart.R;
import net.arvin.greendao.entities.User;
import net.arvin.greendao.gen.UserDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * created by arvin on 17/2/25 15:01
 * email：1035407623@qq.com
 */
public class ProviderActivity extends BaseRefreshLoadingActivity<User> implements MultiItemTypeAdapter.OnItemLongClickListener<User> {
    private static final int REQ_CODE = 1001;
    private AlertDialog.Builder deleteBuilder;

    @Override
    protected String getTitleText() {
        return "内容提供者";
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_provider;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);
        mInnerAdapter.setOnItemLongClickListener(this);
    }

    @OnClick(R.id.tv_add)
    public void addUser() {
        startActivityForResult(AddUserActivity.class, REQ_CODE);
    }

    @Override
    protected MultiItemTypeAdapter<User> getAdapter() {
        return new BaseAdapter<User>(this, R.layout.item_user, mItems) {
            @Override
            protected void convert(CommonHolder holder, User item, int position) {
                holder.setText(R.id.tv_id, "ID:" + item.getId());
                holder.setText(R.id.tv_name, "姓名:" + item.getName());
                holder.setText(R.id.tv_age, "年龄:" + item.getAge());
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE:
                    autoRefresh();
                    break;
            }
        }
    }

    @Override
    protected void loadData(int page) {
        Observable.just("").map(new Func1<String, List<User>>() {
            @Override
            public List<User> call(String s) {
                List<User> users = new ArrayList<>();
                Cursor query = getContentResolver().query(UserProvider.CONTENT_URI,
                        new String[]{UserDao.Properties.Id.columnName, UserDao.Properties.Name.columnName,
                                UserDao.Properties.Age.columnName}, null, null, null);
                if (query != null) {
                    while (query.moveToNext()) {
                        User user = new User();
                        user.setId(query.getLong(0));
                        user.setName(query.getString(1));
                        user.setAge(query.getInt(2));
                        users.add(user);
                    }
                    query.close();
                }
                return users;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<List<User>>() {
                    @Override
                    protected void onResultError(ApiException ex) {
                        showToast(ex.getDisplayMessage());
                        refreshComplete(false);
                    }

                    @Override
                    public void onNext(List<User> users) {
                        mItems.clear();
                        mItems.addAll(users);
                        refreshComplete(true);
                    }
                });
    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, User item, int position) {
        if (mItems.size() == 0) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(AddUserActivity.KEY, item);
        startActivityForResult(AddUserActivity.class, REQ_CODE, bundle);
    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, User item, int position) {
        showDeleteDialog(position);
        return true;
    }

    private void showDeleteDialog(final int position) {
        if (deleteBuilder == null) {
            deleteBuilder = new AlertDialog.Builder(this).setTitle("提示")
                    .setPositiveButton("否", null);
        }
        deleteBuilder.setMessage("确认删除用户《" + mItems.get(position).getName() + "》吗？")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(position);
                    }
                });
        deleteBuilder.show();
    }

    private void deleteUser(int pos) {
        Observable.just(pos).map(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer pos) {
                Uri uri = Uri.parse(UserProvider.CONTENT_URI_STRING + "/" + mItems.get(pos).getId());
                return getContentResolver().delete(uri, null, null);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new AbsAPICallback<Integer>() {
                    @Override
                    protected void onResultError(ApiException ex) {
                        showToast("删除失败");
                    }

                    @Override
                    public void onNext(Integer result) {
                        if (result == 1) {
                            showToast("删除成功");
                            autoRefresh();
                        } else {
                            showToast("删除失败");
                        }
                    }
                });

    }
}
