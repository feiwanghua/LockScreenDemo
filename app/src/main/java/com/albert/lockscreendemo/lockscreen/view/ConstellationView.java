package com.albert.lockscreendemo.lockscreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.albert.lockscreendemo.R;

/**
 * Created by feiwh on 2017/3/14.
 */

class ConstellationView {
    private static ConstellationView mConstellationView;
    private View mRootView;

    private ConstellationView(Context context) {
        initView(context);
    }

    public static ConstellationView getInstance(Context context) {
        synchronized (ConstellationView.class) {
            if (mConstellationView == null) {
                synchronized (ConstellationView.class) {
                    mConstellationView = new ConstellationView(context);
                }
            }
        }
        return mConstellationView;
    }

    public View getRootView() {
        return mRootView;
    }

    private void initView(final Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.page_constellation, null);
    }
}
