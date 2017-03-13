package com.albert.lockscreemdemo.lockscreen;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.WindowManager;

import com.albert.lockscreemdemo.R;
import java.util.List;

import static android.content.pm.PackageManager.GET_ACTIVITIES;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LauncherHomeActivity extends Activity {
    private String mPackageName;
    private String mClassName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_launcher_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        startService(new Intent(LauncherHomeActivity.this, LockScreenService.class));
        getLauncherPackageName(this);
        if (!LockScreenReceiver.isLocked) {
            if (isEffective(mPackageName) && isEffective(mClassName)) {
                Intent systemIntent = new Intent();
                systemIntent.setComponent(new ComponentName(mPackageName, mClassName));
                startActivity(systemIntent);
                moveTaskToBack(false);
            }
        }
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
