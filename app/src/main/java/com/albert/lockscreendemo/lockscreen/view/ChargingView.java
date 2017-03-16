package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.albert.lockscreendemo.R;

/**
 * Created by feiwh on 2017/3/14.
 */

class ChargingView {
    private static ChargingView mLockScreenViewUtil;
    private View mRootView;
    private Button mUnLock;
    private TextView mDateTime;
    private TextView mBatteryLevel;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    private ChargingView(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView(context);
        initParams();
    }

    public static ChargingView getInstance(Context context) {
        synchronized (ChargingView.class) {
            if (mLockScreenViewUtil == null) {
                synchronized (ChargingView.class) {
                    mLockScreenViewUtil = new ChargingView(context);
                }
            }
        }
        return mLockScreenViewUtil;
    }

    public void showWindow() {
        if (mRootView.getParent() == null) {
            mWindowManager.addView(mRootView, mParams);
        }
    }

    public void hideWindow() {
        if (mRootView.getParent() != null) {
            mWindowManager.removeView(mRootView);
            LockViewHelper.setStatus(LockViewHelper.UNLOCK);
        }
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.window_charging, null);
        mUnLock = (Button) mRootView.findViewById(R.id.unLock);
        mUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideWindow();
            }
        });
        mDateTime = (TextView) mRootView.findViewById(R.id.date_time);
        mBatteryLevel = (TextView) mRootView.findViewById(R.id.battery_level);
    }

    private void initParams() {
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    public void setDateTime(String date){
        mDateTime.setText(date);
    }

    public void setBatteryLevel(int batteryLevel){
        mBatteryLevel.setText(batteryLevel+"%");
    }
}
