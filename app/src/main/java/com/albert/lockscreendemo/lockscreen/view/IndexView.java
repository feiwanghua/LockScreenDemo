package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.albert.lockscreendemo.R;

/**
 * Created by feiwh on 2017/3/14.
 */

class IndexView {
    private static IndexView mIndexView;
    private View mRootView;
    private Button mUnLock;
    private TextView mDateTime;
    private TextView mBatteryLevel;

    private IndexView(Context context) {
        initView(context);
    }

    public static IndexView getInstance(Context context) {
        synchronized (IndexView.class) {
            if (mIndexView == null) {
                synchronized (IndexView.class) {
                    mIndexView = new IndexView(context);
                }
            }
        }
        return mIndexView;
    }

    public View getRootView(){
        return mRootView;
    }

    private void initView(final Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.page_index, null);
        mUnLock = (Button) mRootView.findViewById(R.id.unLock);
        mUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockView.getInstance(context).hideWindow();
            }
        });
        mDateTime = (TextView) mRootView.findViewById(R.id.date_time);
        mBatteryLevel = (TextView) mRootView.findViewById(R.id.battery_level);
    }

    public void setDateTime(String date){
        mDateTime.setText(date);
    }

    public void setBatteryLevel(int batteryLevel){
        mBatteryLevel.setText(batteryLevel+"%");
    }
}
