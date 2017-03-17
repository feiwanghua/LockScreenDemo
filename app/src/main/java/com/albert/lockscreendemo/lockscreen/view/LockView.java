package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.albert.lockscreendemo.R;
import com.albert.lockscreendemo.lockscreen.utils.BatteryUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feiwh on 2017/3/16.
 */

public class LockView {
    private static LockView mLockView;
    private View mRootView;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private List<View> mViewList = new ArrayList<>();
    private LockView(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView(context);
        initParams();
    }

    public static LockView getInstance(Context context){
        synchronized (IndexView.class) {
            if (mLockView == null) {
                synchronized (IndexView.class) {
                    mLockView = new LockView(context);
                }
            }
        }
        return mLockView;
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

    private void initParams() {
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
    }

    private void initView(final Context context){
        mRootView = LayoutInflater.from(context).inflate(R.layout.window_lock_view, null);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.lock_view_viewpager);
        mViewList.add(IndexView.getInstance(context).getRootView());
        mViewList.add(ConstellationView.getInstance(context).getRootView());
        mPagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));
                return mViewList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
        updateTimeAndDate(context);
        batteryChanged(context);
    }

    public void updateTimeAndDate(Context context) {
        Time time = new Time();
        time.setToNow();
        IndexView.getInstance(context).setDateTime(time.year + "/" + (time.month + 1) + "/" + time.monthDay + " " + time.hour + ":" + time.minute);
    }

    public void batteryChanged(Context context) {
        if(BatteryUtil.isCharging(context)) {
            IndexView.getInstance(context).setBatteryLevel(BatteryUtil.getBatteryLevel());
        }
    }

}
