package net.arvin.androidart.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import net.arvin.afbaselibrary.uis.activities.BaseActivity;
import net.arvin.afbaselibrary.uis.activities.BaseSwipeBackActivity;
import net.arvin.androidart.R;

import butterknife.OnClick;

/**
 * 隐式intent匹配分析
 */
public class IntentActivity extends BaseSwipeBackActivity {

    @Override
    public int getContentViewId() {
        return R.layout.activity_intent;
    }

    @Override
    protected String getTitleText() {
        return "Intent分析";
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
    }

    @OnClick(R.id.toSec)
    public void toSec() {
        Intent intent = new Intent();
        intent.setAction("SecondActivityAction");//注释掉这个发现，action不能不设置，至少要有一个匹配上
//        intent.setAction("SecondActivityAction1");
//        intent.setAction("SecondActivityAction2");

//        intent.setAction("SecondActivityAction3");//解开这个注释发现，如果intent中包含过滤器中未设置的action，则找不到组件
//        intent.setData(Uri.parse("https://"));//解开这个注释发现，如果过滤器中未设置data，则intent中设置了也找不到对应的组件
        startActivity(intent);
    }

    @OnClick(R.id.toThi)
    public void toThird() {
        Intent intent = new Intent();
        intent.setAction("ThirdActivityAction");
        intent.addCategory("ThirdActivityCategory");//注释掉这个发现，category不设置也可以，因为会有一个默认的category
//        intent.addCategory("ThirdActivityCategory1");
//        intent.addCategory("ThirdActivityCategory2");
        //解开上边任意一个注释，发现只要加入任意一个过滤器中包含的category就能匹配通过

//        intent.addCategory("ThirdActivityCategory3");//解开这个注释发现，如果intent中包含过滤器中未设置的category，则找不到组件

        //所以得出的结论是intent的category只要是过滤器的category的子集，则category匹配通过。
        startActivity(intent);
    }

    @OnClick(R.id.toFour)
    public void toFour() {
        Intent intent = new Intent();
        intent.setAction("FourActivityAction");
//        intent.setData(Uri.parse("http://"));//只保留这个注释，发现若是过滤器中URI和MIME都设置了，则intent中也要设置URI和MIME
        intent.setDataAndType(Uri.parse("http://"), "image/png");
        intent.setDataAndType(Uri.parse("https://"), "image/png");
        intent.setDataAndType(Uri.parse("https://"), "text/*");
        intent.setDataAndType(Uri.parse("http://"), "text/*");
        //以上四个发现只要intent中设置的URI结构和MIME只要在过滤器中包含了，即可通过
        //结论：只要过滤器中的URI结构和MIME在intent中能找到对应的URI和MIME就可以，URI结构的匹配是过滤器中有什么，intent中就必须有什么才能匹配通过
        startActivity(intent);
    }
}
