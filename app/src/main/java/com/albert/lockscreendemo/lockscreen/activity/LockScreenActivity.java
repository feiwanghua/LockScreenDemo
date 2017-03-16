package com.albert.lockscreendemo.lockscreen.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.albert.lockscreendemo.R;
import com.albert.lockscreendemo.lockscreen.fragment.LockFragment;
import com.albert.lockscreendemo.lockscreen.utils.Util;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LockScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("albert","LockScreenActivity onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        setContentView(R.layout.activity_lock_container);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.lock_container,new LockFragment())
                .commit();
        Util.hideMenu(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    protected void onPause() {
        Log.v("albert","LockScreenActivity onPause");
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.v("albert","LockScreenActivity onResume");
        super.onResume();
    }

}
