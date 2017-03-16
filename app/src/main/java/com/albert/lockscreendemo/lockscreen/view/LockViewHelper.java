package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import com.albert.lockscreendemo.lockscreen.utils.BatteryUtil;
import com.albert.lockscreendemo.lockscreen.utils.Util;

/**
 * Created by feiwh on 2017/3/15.
 */

public class LockViewHelper {
    private static int mStatus = -1;
    public static final int LOCK = 1;
    public static final int UNLOCK = 2;
    public static final int CHARGING_LOCK = 3;
    public static void showWindow(Context context){
        Util.disableKeyguard(context);
        if(BatteryUtil.isCharging(context)){
            LockScreenView.getInstance(context).hideWindow();
            ChargingView.getInstance(context).showWindow();
            mStatus = CHARGING_LOCK;
            batteryChanged(context);
        }else {
            ChargingView.getInstance(context).hideWindow();
            LockScreenView.getInstance(context).showWindow();
            mStatus = LOCK;
        }
        updateTimeAndDate(context);
    }

    public static void updateTimeAndDate(Context context){
        Time time=new Time();
        time.setToNow();
        Log.v("albert","year:"+time.year);
        Log.v("albert","month:"+(time.month+1));
        Log.v("albert","monthDay:"+time.monthDay);
        Log.v("albert","hour:"+time.hour);
        Log.v("albert","minute:"+time.minute);
        switch (mStatus){
            case LOCK:
                LockScreenView.getInstance(context).setDateTime(time.year+"/"+(time.month+1)+"/"+time.monthDay+" "+time.hour+":"+time.minute);
                break;
            case CHARGING_LOCK:
                ChargingView.getInstance(context).setDateTime(time.year+"/"+(time.month+1)+"/"+time.monthDay+" "+time.hour+":"+time.minute);
                break;
            case UNLOCK:
                break;
        }
    }

    public static void batteryChanged(Context context){
        switch (mStatus){
            case LOCK:
                break;
            case CHARGING_LOCK:
                ChargingView.getInstance(context).setBatteryLevel(BatteryUtil.getBatteryLevel());
                break;
            case UNLOCK:
                break;
        }
    }

    public static void setStatus(int status){
        mStatus = status;
    }

    public static void hideWindow(Context context){
        switch (mStatus){
            case LOCK:
                LockScreenView.getInstance(context).hideWindow();
                break;
            case CHARGING_LOCK:
                ChargingView.getInstance(context).hideWindow();
                break;
            case UNLOCK:
                break;
        }
    }
}
