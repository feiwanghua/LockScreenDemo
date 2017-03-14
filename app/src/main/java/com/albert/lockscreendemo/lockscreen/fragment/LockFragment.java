package com.albert.lockscreendemo.lockscreen.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.albert.lockscreendemo.R;
import com.albert.lockscreendemo.lockscreen.receiver.LockScreenReceiver;

/**
 * Created by feiwh on 2017/3/13.
 */

public class LockFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lock_screen,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView(){
        LockScreenReceiver.isLocked = true;
        getActivity().findViewById(R.id.iv_key).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockScreenReceiver.isLocked = false;
                getActivity().finish();
                Toast.makeText(getActivity(), "Screen is unlocked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
