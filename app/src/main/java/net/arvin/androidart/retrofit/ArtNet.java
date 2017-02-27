package net.arvin.androidart.retrofit;

import android.content.Context;

import net.arvin.afbaselibrary.nets.BaseNet;
import net.arvin.androidart.App;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by arvin on 17/2/27 20:05
 * email：1035407623@qq.com
 */
public class ArtNet extends BaseNet<ArtApi> {
    private static ArtNet mNet;

    public ArtNet() {
        super();
        converterFactory = GsonConverterFactory.create();
    }

    public static ArtNet getInstance() {
        if (mNet == null) {
            mNet = new ArtNet();
        }
        return mNet;
    }

    @Override
    protected String getBaseUrl() {
        return ArtApi.baseUrl;
    }

    @Override
    protected Context getContext() {
        return App.getInstance();
    }
}
