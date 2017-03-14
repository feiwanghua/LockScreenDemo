package com.albert.lockscreendemo.lockscreen.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.albert.lockscreendemo.lockscreen.receiver.LockScreenReceiver;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LockScreenService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("albert","LockScreenService onCreate");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        BroadcastReceiver mReceiver = new LockScreenReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("albert","LockScreenService onDestroy");
    }
}
