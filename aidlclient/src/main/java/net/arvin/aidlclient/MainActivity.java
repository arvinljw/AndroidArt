package net.arvin.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.androidart.aidl.IIntegerAdd;
import net.arvin.androidart.aidl.IPersonCount;
import net.arvin.androidart.aidl.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by arvin on 17/2/14 21:47
 * email：1035407623@qq.com
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.et_num1)
    EditText etNum1;
    @BindView(R.id.et_num2)
    EditText etNum2;
    @BindView(R.id.edit_show_result)
    EditText editShowResult;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_age)
    EditText edAge;
    @BindView(R.id.tv_person_count)
    TextView tvPersonCount;

    private IIntegerAdd iIntegerAdd;

    private boolean needCalculate = false;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iIntegerAdd = IIntegerAdd.Stub.asInterface(service);
            showToast("已绑定远程计算");
            if (needCalculate) {
                calculateResult();
                needCalculate = false;
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iIntegerAdd = null;
        }
    };

    private IPersonCount iPersonCount;
    private ServiceConnection connPerson = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iPersonCount = IPersonCount.Stub.asInterface(service);
            showToast("已绑定人物统计");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iIntegerAdd = null;
        }
    };
    private BinderPool mBinderPool;

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mBinderPool = BinderPool.getInstance(this);
        bind();
        bindPersonCount();
    }


    private void bind() {
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("net.arvin.androidart",
//                "net.arvin.androidart.multiProcess.IntegerAddService"));
//        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        iIntegerAdd = IIntegerAdd.Stub.asInterface(mBinderPool.queryBinder(BinderPool.BINDER_COMPUTE));
    }

    private void bindPersonCount() {
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("net.arvin.androidart",
//                "net.arvin.androidart.multiProcess.PersonCountService"));
//        bindService(intent, connPerson, Context.BIND_AUTO_CREATE);

        iPersonCount = IPersonCount.Stub.asInterface(mBinderPool.queryBinder(BinderPool.BINDER_PERSON_COUNT));
    }


    @OnClick(R.id.btn_count)
    public void calculateResult() {
        int mNum1 = Integer.parseInt(etNum1.getText().toString());
        int mNum2 = Integer.parseInt(etNum2.getText().toString());
        int mTotal = 0;
        try {
            if (iIntegerAdd == null) {
                needCalculate = true;
                bind();
                return;
            }
            mTotal = iIntegerAdd.add(mNum1, mNum2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        editShowResult.setText(mTotal + "");
    }

    @OnClick(R.id.btn_add)
    public void onAdd() {
        String name = getEditTextStr(edName);
        String age = getEditTextStr(edAge);
        if (TextUtils.isEmpty(name)) {
            edName.setError("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(age)) {
            edAge.setError("请输入年龄");
            return;
        }
        try {
            if (iPersonCount == null) {
                bindPersonCount();
                return;
            }
            boolean isExit = iPersonCount.addPerson(new Person(name, Integer.valueOf(age)));
            if (isExit) {
                showToast("该人物已存在");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_select)
    public void onSelected() {
        try {
            if (iPersonCount == null) {
                bindPersonCount();
                return;
            }
            List<Person> persons = iPersonCount.getPersons();
            StringBuilder sb = new StringBuilder();
            for (Person person : persons) {
                sb.append(person.toString());
            }
            if (persons.size() == 0) {
                sb.append("暂无数据.");
            }
            tvPersonCount.setText(sb.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private String getEditTextStr(TextView tv) {
        if (tv == null) {
            return "";
        }
        return tv.getText().toString().trim();
    }

    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
