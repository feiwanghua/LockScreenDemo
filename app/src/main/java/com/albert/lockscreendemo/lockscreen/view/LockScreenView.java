package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.albert.lockscreendemo.R;

/**
 * Created by feiwh on 2017/3/14.
 */

class LockScreenView {
    private static LockScreenView mLockScreenView;
    private View mRootView;
    private TextView mDateTime;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

    private LockScreenView(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView(context);
        initParams();
    }

    public static LockScreenView getInstance(Context context) {
        synchronized (LockScreenView.class) {
            if (mLockScreenView == null) {
                synchronized (LockScreenView.class) {
                    mLockScreenView = new LockScreenView(context);
                }
            }
        }
        return mLockScreenView;
    }

    public void showWindow() {
        if (mRootView.getParent() == null) {
            mWindowManager.addView(mRootView, mParams);
        }
    }

    public void hideWindow() {
        if (mRootView.getParent() != null) {
            mWindowManager.removeView(mRootView);
        }
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.window_lock_screen, null);
        mRootView.findViewById(R.id.unLock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideWindow();
            }
        });
        mDateTime = (TextView) mRootView.findViewById(R.id.date_time);
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
}
