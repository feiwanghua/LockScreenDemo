package com.albert.lockscreendemo.lockscreen.service;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.albert.lockscreendemo.lockscreen.receiver.LockScreenReceiver;
import com.albert.lockscreendemo.lockscreen.view.LockView;

/**
 * Created by feiwh on 2017/3/9.
 */

public class LockScreenService extends Service {

    private final static int GRAY_SERVICE_ID = 1001;
    // A public action send by AlarmService when the alarm has started.
    public static final String ALARM_ALERT_ACTION = "com.android.deskclock.ALARM_ALERT";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("albert", "LockScreenService onCreate");
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        filter.addAction(Intent.ACTION_TIME_TICK);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        filter.addAction(ALARM_ALERT_ACTION);
        BroadcastReceiver mReceiver = new LockScreenReceiver();
        registerReceiver(mReceiver, filter);
        startPhoneStateService();
    }

    @Override
    public void onDestroy() {
        Log.v("albert", "LockScreenService onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

    }

    private void startPhoneStateService() {
        ((TelephonyManager) getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE))
                .listen(new PhoneStateListener() {
                    @Override
                    public void onCallStateChanged(int state, String incomingNumber) {
                        super.onCallStateChanged(state, incomingNumber);
                        switch (state) {
                            case TelephonyManager.CALL_STATE_IDLE:
                                Log.v("albert","CALL_STATE_IDLE");
                                break;
                            case TelephonyManager.CALL_STATE_OFFHOOK:
                                Log.v("albert","CALL_STATE_OFFHOOK");
                                break;
                            case TelephonyManager.CALL_STATE_RINGING:
                                Log.v("albert","CALL_STATE_RINGING");
                                LockView.getInstance(getApplicationContext()).hideWindow();
                                break;
                        }
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
