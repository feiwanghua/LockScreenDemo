package com.albert.lockscreendemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.albert.lockscreendemo.lockscreen.service.LockScreenService;
import com.albert.lockscreendemo.lockscreen.utils.Util;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.disableKeyguard(getApplicationContext());
                startService(new Intent(MainActivity.this,LockScreenService.class));
                Toast.makeText(getApplicationContext(),"startService",Toast.LENGTH_SHORT);
                finish();
            }
        });

    }


}
