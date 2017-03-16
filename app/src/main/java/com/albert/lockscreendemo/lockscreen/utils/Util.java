package com.albert.lockscreendemo.lockscreen.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by feiwh on 2017/3/13.
 */

public class Util {
    /**
     * 判断是否有锁
     * */
    public static boolean isSecure(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return ((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).isKeyguardSecure();
        }else{
            try {
                Class<?> clazz = Class.forName("com.android.internal.widget.LockPatternUtils");
                Constructor<?> constructor = clazz.getConstructor(Context.class);
                constructor.setAccessible(true);
                Object utils = constructor.newInstance(context);
                Method method = clazz.getMethod("isSecure");
                return (Boolean) method.invoke(utils);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 引导解锁
     * */
    public static void disableKeyguard(Context context){
        if(isSecure(context)) {
            //只能禁用滑动锁
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
            keyguardLock.disableKeyguard();
            //密码锁或者图案锁可以引导用户去手动关闭
            /*Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivity(intent);*/
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    public static void hideMenu(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            // lower api
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void requestPermission(Activity activity,int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                !Settings.canDrawOverlays(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent,requestCode);
        }
    }

    public static boolean isServiceWork(Context context, String serviceName) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(40);
        if (list.size() <= 0) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).service.getClassName().toString();
            if (name.equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

}
