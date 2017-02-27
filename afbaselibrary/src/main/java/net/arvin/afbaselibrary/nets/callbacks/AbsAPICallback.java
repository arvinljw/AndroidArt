package net.arvin.afbaselibrary.nets.callbacks;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.orhanobut.logger.Logger;

import net.arvin.afbaselibrary.nets.exceptions.ApiException;
import net.arvin.afbaselibrary.nets.exceptions.ResultException;
import net.arvin.afbaselibrary.utils.ActivityUtil;

import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * created by arvin on 16/10/24 17:20
 * email：1035407623@qq.com
 */
public abstract class AbsAPICallback<T> extends Subscriber<T> {

    //出错提示
    public final String networkMsg = "服务器开小差";
    public final String parseMsg = "数据解析出错";
    public final String net_connection = "网络连接错误";
    public final String unknownMsg = "未知错误";

    protected AbsAPICallback() {
    }


    @Override
    public void onError(Throwable e) {
        e = getThrowable(e);

        if (e instanceof HttpException) {//HTTP错误

            error(e, ((HttpException) e).code(), networkMsg);

        } else if (e instanceof ResultException) {//服务器返回的错误

            ResultException resultException = (ResultException) e;
            error(e, resultException.getErrCode(), resultException.getMessage());
            resultError(resultException);//处理错误

        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {//解析错误

            error(e, ApiException.PARSE_ERROR, parseMsg);

        } else if (e instanceof ConnectException) {
            error(e, ApiException.PARSE_ERROR, net_connection);
        } else {//未知错误
            error(e, ApiException.UNKNOWN, unknownMsg);
        }
    }

    private Throwable getThrowable(Throwable e) {
        Throwable throwable = e;
        while (throwable.getCause() != null) {
            e = throwable;
            throwable = throwable.getCause();
        }
        return e;
    }

    private void resultError(ResultException e) {
//        if (e.getErrCode() == ApiException.RE_LOGIN) {
//            try {
//                Context currentActivity = ActivityUtil.getCurrentActivity();
//                if (currentActivity != null) {
//                    Intent intent = new Intent(currentActivity, Class.forName("登录界面的包名.LoginActivity"));
//                    currentActivity.startActivity(intent);
//                    Toast.makeText(currentActivity, "请重新登录", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
    }

    /**
     * 错误信息回调
     */
    private void error(Throwable e, int errorCode, String errorMsg) {
        ApiException ex = new ApiException(e, errorCode);
        Logger.d(e);
        ex.setDisplayMessage(errorMsg);
        onResultError(ex);
    }

    /**
     * 服务器返回的错误
     */
    protected abstract void onResultError(ApiException ex);

    @Override
    public void onCompleted() {
    }

}
