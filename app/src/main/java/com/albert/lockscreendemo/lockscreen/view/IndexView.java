package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.content.Intent;
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
    private Context mContext;
    private View mRootView;
    private Button mUnLock;
    private TextView mDateTime;
    private TextView mBatteryLevel;

    private IndexView(Context context) {
        mContext = context;
        initView();
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

    private void initView() {
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.page_index, null);
        mUnLock = (Button) mRootView.findViewById(R.id.unLock);
        mUnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockView.getInstance(mContext).hideWindow();
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

    public void switchViewMode(String status){
        if(status.equals(Intent.ACTION_POWER_CONNECTED)){
            mBatteryLevel.setVisibility(View.VISIBLE);
            mUnLock.setText("充电解锁");
        }else if(status.equals(Intent.ACTION_POWER_DISCONNECTED)){
            mBatteryLevel.setVisibility(View.INVISIBLE);
            mUnLock.setText("解锁");
        }
    }
}
