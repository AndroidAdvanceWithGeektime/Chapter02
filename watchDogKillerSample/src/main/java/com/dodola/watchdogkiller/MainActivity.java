package com.dodola.watchdogkiller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
                // 触发生效
                Runtime.getRuntime().gc();
                System.runFinalization();
                resetWatchDogStatus();
            }
        });
        findViewById(R.id.fire_timeout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 因为 stopWatchDog需要下一次循环才会生效，这里先post一下
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                fireTimeout();
                                Runtime.getRuntime().gc();
                                System.runFinalization();
                            }
                        }).start();
                    }
                }, 100);

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
