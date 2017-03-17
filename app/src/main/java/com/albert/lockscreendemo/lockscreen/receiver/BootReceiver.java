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
        Log.v("albert", "BootReceiver");
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.v("albert","ACTION_BOOT_COMPLETED");
            if(Util.isLockScreenOn(context)){
                LockView.getInstance(context).showWindow();
                Util.disableKeyguard(context);
            }
            if (!Util.isServiceWork(context, LockScreenService.class.getName())) {
                context.startService(new Intent(context, LockScreenService.class));
            }
        }
    }
}
