package net.arvin.afbaselibrary.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;

import net.arvin.afbaselibrary.R;
import net.arvin.afbaselibrary.widgets.GlideCircleTransform;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by arvin on 2016/5/24
 */
public class GlideUtil {
    /**
     * 加载普通图片（http://或者file://）
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).into(imageView);
    }

    /**
     * 加载为圆形图片（一般为头像加载）
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.img_default_avatar).error(R.drawable.img_default_avatar).
                transform(new GlideCircleTransform(context)).into(imageView);
    }

    /**
     * 加载本地图片（资源文件）
     */
    public static void loadLocalImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).into(imageView);
    }

    /**
     * 加载本地图片（资源文件）
     */
    public static void loadLocalCircleImage(Context context, int resId, ImageView imageView) {
        Glide.with(context).load(resId).transform(new GlideCircleTransform(context)).into(imageView);
    }

    /**
     * @param okHttpClient 使用Https时的，Net中的okHttpClient
     */
    public static void registerHttps(Context context, OkHttpClient okHttpClient) {
        Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    }
}
