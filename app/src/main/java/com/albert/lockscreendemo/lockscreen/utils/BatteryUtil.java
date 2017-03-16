package com.albert.lockscreendemo.lockscreen.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

/**
 * Created by feiwh on 2017/3/15.
 */

public class BatteryUtil {
    private static IntentFilter mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    private static int mBatteryLevel = 0;

    public static boolean isCharging(Context context) {
        Intent intent = context.registerReceiver(null, mIntentFilter);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
    }

    public static int extraPlugged(Context context) {
        Intent intent = context.registerReceiver(null, mIntentFilter);
        return intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
    }

    public static void setBatteryLevel(int level, int scale) {
        mBatteryLevel = level * 100 / scale;
        Log.v("albert", "BatteryLevel:"+mBatteryLevel);
    }

    public static int getBatteryLevel() {
        return mBatteryLevel;
    }
}
