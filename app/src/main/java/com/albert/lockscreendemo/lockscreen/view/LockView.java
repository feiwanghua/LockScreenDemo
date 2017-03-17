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
    private Context mContext;
    private View mRootView;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private List<View> mViewList = new ArrayList<>();
    private LockView(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView();
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

    private void initView(){
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.window_lock_view, null);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.lock_view_viewpager);
        mViewList.add(IndexView.getInstance(mContext).getRootView());
        mViewList.add(ConstellationView.getInstance(mContext).getRootView());
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
        initDataSet();
    }

    public void initDataSet(){
        updateTimeAndDate();
        batteryChanged();
    }

    public void updateTimeAndDate() {
        Time time = new Time();
        time.setToNow();
        IndexView.getInstance(mContext).setDateTime(time.year + "/" + (time.month + 1) + "/" + time.monthDay + " " + time.hour + ":" + time.minute);
    }

    public void batteryChanged() {
        IndexView.getInstance(mContext).setBatteryLevel(BatteryUtil.getBatteryLevel());
    }

    public void switchViewMode(String status){
        IndexView.getInstance(mContext).switchViewMode(status);
    }
}
