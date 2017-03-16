package com.albert.lockscreendemo.lockscreen.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import java.util.List;

import static android.content.pm.PackageManager.GET_ACTIVITIES;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LauncherActivity extends Activity {
    private String mPackageName;
    private String mClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("albert", "LauncherActivity onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getLauncherPackageName(this);
        if (isEffective(mPackageName) && isEffective(mClassName)) {
            Intent systemIntent = new Intent();
            systemIntent.setComponent(new ComponentName(mPackageName, mClassName));
            startActivity(systemIntent);
        }/*else{*/
        //LockScreenView.getInstance(getApplicationContext()).showWindow();
//            Intent lockIntent = new Intent(getApplicationContext(),LockScreenActivity.class);
//            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(lockIntent);
//        }
        finish();
    }

    private void getLauncherPackageName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, GET_ACTIVITIES);
        if (resInfoList != null) {
            ResolveInfo resInfo;
            for (int i = 0; i < resInfoList.size(); i++) {
                resInfo = resInfoList.get(i);
                if ((resInfo.activityInfo.applicationInfo.flags &
                        ApplicationInfo.FLAG_SYSTEM) > 0) {
                    mPackageName = resInfo.activityInfo.packageName;
                    mClassName = resInfo.activityInfo.name;
                    break;
                }
            }
        }
    }

    private boolean isEffective(String string) {
        if ((string == null) || ("".equals(string)) || (" ".equals(string))
                || ("null".equals(string)) || ("\n".equals(string)))
            return false;
        else
            return true;
    }
}
