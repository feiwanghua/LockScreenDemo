package com.albert.lockscreemdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.albert.lockscreemdemo.lockscreen.LockScreenService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,LockScreenService.class));
                Toast.makeText(getApplicationContext(),"startService",Toast.LENGTH_SHORT);
                finish();
            }
        });
    }
}
