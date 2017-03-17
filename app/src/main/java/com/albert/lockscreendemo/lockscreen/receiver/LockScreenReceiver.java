package com.albert.lockscreendemo.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.albert.lockscreendemo.lockscreen.activity.DisableKeyguardActivity;
import com.albert.lockscreendemo.lockscreen.service.LockScreenService;
import com.albert.lockscreendemo.lockscreen.utils.BatteryUtil;
import com.albert.lockscreendemo.lockscreen.utils.Util;
import com.albert.lockscreendemo.lockscreen.view.LockView;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LockScreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_SCREEN_OFF:
                Log.v("albert", "ACTION_SCREEN_OFF");
                LockView.getInstance(context).showWindow();
                Util.disableKeyguard(context);
                break;
            case Intent.ACTION_SCREEN_ON:
                Log.v("albert", "ACTION_SCREEN_ON");
                sendBroadcastToFinishActivity(context);
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.v("albert", "ACTION_POWER_CONNECTED");
                LockView.getInstance(context).switchViewMode(Intent.ACTION_POWER_CONNECTED);
                LockView.getInstance(context).showWindow();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Log.v("albert", "ACTION_POWER_DISCONNECTED");
                LockView.getInstance(context).switchViewMode(Intent.ACTION_POWER_DISCONNECTED);
                break;
            case Intent.ACTION_BATTERY_CHANGED:
                Log.v("albert", "ACTION_BATTERY_CHANGED");
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                BatteryUtil.setBatteryLevel(level, scale);
                LockView.getInstance(context).batteryChanged();
                break;
            case Intent.ACTION_BATTERY_OKAY:
                Log.v("albert", "ACTION_BATTERY_OKAY");
                break;
            case Intent.ACTION_TIME_CHANGED:
            case Intent.ACTION_TIME_TICK:
            case Intent.ACTION_TIMEZONE_CHANGED:
                Log.v("albert", "ACTION_TIME_CHANGED|ACTION_TIME_TICK|ACTION_TIMEZONE_CHANGED");
                LockView.getInstance(context).updateTimeAndDate();
                break;
            case LockScreenService.ALARM_ALERT_ACTION:
                Log.v("albert", "ALARM_ALERT_ACTION");
                LockView.getInstance(context).hideWindow();
                break;
        }
    }

    /*
    * send Broadcast To Finish DisableKeyguardActivity
    * */
    public void sendBroadcastToFinishActivity(Context context) {
        Intent intent = new Intent();
        intent.setAction(DisableKeyguardActivity.class.getName());
        context.sendBroadcast(intent);
    }
}
