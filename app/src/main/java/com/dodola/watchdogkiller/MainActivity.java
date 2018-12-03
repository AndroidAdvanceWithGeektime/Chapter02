package com.dodola.watchdogkiller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private TextView mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetWatchDogStatus();
        findViewById(R.id.kill_watchdog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WatchDogKiller.stopWatchDog();
                resetWatchDogStatus();
            }
        });
        findViewById(R.id.fire_timeout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        fireTimeout();
                        Runtime.getRuntime().gc();
                        System.runFinalization();
                    }
                }).start();
                Toast.makeText(MainActivity.this, "请等待。。。。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fireTimeout() {
        GhostObject object = new GhostObject();
    }

    private void resetWatchDogStatus() {
        boolean alive = WatchDogKiller.checkWatchDogAlive();
        mStatusView = findViewById(R.id.watch_status);
        mStatusView.setText(alive ? "ON" : "OFF");
    }
}
