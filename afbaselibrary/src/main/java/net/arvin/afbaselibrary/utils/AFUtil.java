package net.arvin.afbaselibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * created by arvin on 16/12/23 11:38
 * email：1035407623@qq.com
 */
public class AFUtil {
    /**
     * 获取版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void toCall(Context context, String mobile) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + mobile);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * @param url 需要以http或https开头
     */
    public static void toHtml(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 打开系统计算器
     */
    @TargetApi(15)
    public static void callCalculator(Context context) {
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        final PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = pm.getInstalledPackages(0);
        for (PackageInfo pi : packs) {
            if (pi.packageName.toLowerCase().contains("calcul")) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("appName", pi.applicationInfo.loadLabel(pm));
                map.put("packageName", pi.packageName);
                items.add(map);
            }
        }
        if (items.size() >= 1) {
            String packageName = (String) items.get(0).get("packageName");
            Intent i = pm.getLaunchIntentForPackage(packageName);
            if (i != null)
                context.startActivity(i);
        } else {
            Toast.makeText(context, "没有安装计算器", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开系统日历
     */
    public static void callCalendar(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("content://com.android.calendar/time"));
        context.startActivity(intent);
    }
}
