package com.albert.lockscreendemo.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.albert.lockscreendemo.lockscreen.activity.LockScreenActivity;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LockScreenReceiver extends BroadcastReceiver {
    public static boolean isLocked = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            isLocked = false;
            Intent lockIntent = new Intent(context,LockScreenActivity.class);
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lockIntent);
            Toast.makeText(context, "ACTION_SCREEN_OFF", Toast.LENGTH_SHORT).show();
        } else if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)){
            Toast.makeText(context, "ACTION_POWER_CONNECTED", Toast.LENGTH_SHORT).show();
        }else if(intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)){
            Toast.makeText(context, "ACTION_POWER_DISCONNECTED", Toast.LENGTH_SHORT).show();
        }
    }
}
