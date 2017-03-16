package com.albert.lockscreendemo;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
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

                }
            }
        }
    }

    private void initView() {
        findViewById(R.id.open_lock_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.disableKeyguard(getApplicationContext());
                startService(new Intent(MainActivity.this, LockScreenService.class));
                Toast.makeText(getApplicationContext(), "startService", Toast.LENGTH_SHORT);
                finish();
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
