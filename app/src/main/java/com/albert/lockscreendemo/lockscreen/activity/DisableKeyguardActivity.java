package com.albert.lockscreendemo.lockscreen.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by feiwh on 2017/3/16.
 */

public class DisableKeyguardActivity extends Activity {
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        Log.v("albert","DisableKeyguardActivity onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(DisableKeyguardActivity.class.getName());
        registerReceiver(mBroadcastReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        Log.v("albert","DisableKeyguardActivity onDestroy");
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }
}
