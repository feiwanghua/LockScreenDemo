package com.albert.lockscreendemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.albert.lockscreendemo.lockscreen.service.LockScreenService;
import com.albert.lockscreendemo.lockscreen.utils.Util;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    findViewById(R.id.open_lock_screen).setEnabled(true);
                }
            }
        }
    }

    private void initView() {
        ((Switch) findViewById(R.id.open_lock_screen)).setChecked(Util.isLockScreenOn(getApplicationContext()));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M||Settings.canDrawOverlays(this)) {
            findViewById(R.id.open_lock_screen).setEnabled(true);
        }
        ((Switch) findViewById(R.id.open_lock_screen)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Util.disableKeyguard(getApplicationContext());
                    startService(new Intent(MainActivity.this, LockScreenService.class));
                    Toast.makeText(getApplicationContext(), "startService", Toast.LENGTH_SHORT);
                    Util.setLockScreenStatus(getApplicationContext(),true);
                }else{
                    stopService(new Intent(MainActivity.this, LockScreenService.class));
                    Toast.makeText(getApplicationContext(), "stopService", Toast.LENGTH_SHORT);
                    Util.setLockScreenStatus(getApplicationContext(),false);
                }
            }
        });

        findViewById(R.id.request_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.requestPermission(MainActivity.this, REQUEST_CODE);
            }
        });
    }
}
