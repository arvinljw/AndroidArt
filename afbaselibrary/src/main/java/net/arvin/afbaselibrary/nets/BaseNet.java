package net.arvin.afbaselibrary.nets;

import android.content.Context;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.orhanobut.logger.Logger;

import net.arvin.afbaselibrary.nets.converters.GsonConverterFactory;
import net.arvin.afbaselibrary.utils.CertificateUtil;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * created by arvin on 16/11/21 23:06
 * email：1035407623@qq.com
 */
public abstract class BaseNet<T> {
    private T api;
    private Class<T> clazz;
    private OkHttpClient okHttpClient;
    private String token;
    private String deviceId;

    private Converter.Factory converterFactory;
    private CallAdapter.Factory rxJavaCallAdapterFactory;

    protected BaseNet() {
        converterFactory = GsonConverterFactory.create();
        rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
        clazz = getApiClazz();
    }

    public T getApi() {
        if (api == null) {
            initHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(converterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();

            api = retrofit.create(clazz);
        }
        return api;
    }

    @SuppressWarnings("ConstantConditions")
    private void initHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request;
                    if (!TextUtils.isEmpty(token)) {
                        request = chain.request().newBuilder()
                                .addHeader("token", token)
                                .addHeader("version", "1.0")
                                .build();
                    } else {
                        request = chain.request().newBuilder()
                                .addHeader("version", "1.0")
                                .build();
                    }
                    request = dealRequest(request);
                    Logger.i("request" + request);

                    Response response = chain.proceed(request);
                    dealResponse(response);
                    return response;
                }
            });
            if (isNeedHttps()) {
                try {
                    builder.sslSocketFactory(CertificateUtil.setCertificates(getContext(), getCertificateNames()))
                            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            okHttpClient = builder.build();
        }
    }


    protected void dealResponse(Response response) {
    }

    /**
     * 若需要使用https请求,请设置证书信息 getCertificateNames()
     */
    protected boolean isNeedHttps() {
        return false;
    }

    protected String[] getCertificateNames() {
        return null;
    }

    public void setToken(String token) {
        this.token = token;
        clear();
    }

    public String getToken() {
        return token;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    private void clear() {
        okHttpClient = null;
        api = null;
    }

    protected Request dealRequest(Request request) {
        return request;
    }

    public void registerHttps() {
        Glide.get(getContext()).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }

    protected abstract Class<T> getApiClazz();

    protected abstract String getBaseUrl();

    protected abstract Context getContext();

}
