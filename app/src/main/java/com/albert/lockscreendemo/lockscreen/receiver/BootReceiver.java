package com.albert.lockscreendemo.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.albert.lockscreendemo.lockscreen.service.LockScreenService;
import com.albert.lockscreendemo.lockscreen.utils.Util;
import com.albert.lockscreendemo.lockscreen.view.LockView;

/**
 * Created by feiwh on 2017/3/9.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("albert", "BootReceiver:" + intent.getAction());
        if (Util.isLockScreenOn(context)) {
            if (!Util.isServiceWork(context, LockScreenService.class.getName())) {
                LockView.getInstance(context).showWindow();
                Util.disableKeyguard(context);
                context.startService(new Intent(context, LockScreenService.class));
                Log.v("albert", "startService LockScreenService");
            }
        }
    }
}
