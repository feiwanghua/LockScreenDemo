package com.albert.lockscreendemo.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.albert.lockscreendemo.lockscreen.service.LockScreenService;
import com.albert.lockscreendemo.lockscreen.utils.BatteryUtil;
import com.albert.lockscreendemo.lockscreen.view.LockViewHelper;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_OFF:
                Log.v("albert", "ACTION_SCREEN_OFF");
                LockViewHelper.showWindow(context);
                break;
            case Intent.ACTION_SCREEN_ON:
                Log.v("albert", "ACTION_SCREEN_ON");
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.v("albert", "ACTION_POWER_CONNECTED");
                LockViewHelper.showWindow(context);
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Log.v("albert", "ACTION_POWER_DISCONNECTED");
                LockViewHelper.showWindow(context);
                break;
            case Intent.ACTION_BATTERY_CHANGED:
                Log.v("albert", "ACTION_BATTERY_CHANGED");
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                BatteryUtil.setBatteryLevel(level, scale);
                LockViewHelper.batteryChanged(context);
                break;
            case Intent.ACTION_BATTERY_OKAY:
                Log.v("albert", "ACTION_BATTERY_OKAY");
                break;
            case Intent.ACTION_TIME_CHANGED:
            case Intent.ACTION_TIME_TICK:
            case Intent.ACTION_TIMEZONE_CHANGED:
                Log.v("albert", "ACTION_TIME_CHANGED|ACTION_TIME_TICK|ACTION_TIMEZONE_CHANGED");
                LockViewHelper.updateTimeAndDate(context);
                break;
            case LockScreenService.ALARM_ALERT_ACTION:
                Log.v("albert", "ALARM_ALERT_ACTION");
                LockViewHelper.hideWindow(context);
                break;
        }
    }
}
