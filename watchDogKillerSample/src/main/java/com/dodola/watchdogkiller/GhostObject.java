package com.dodola.watchdogkiller;

import android.util.Log;

/**
 * Created by dodola on 2018/12/3.
 */
public class GhostObject {
    @Override
    protected void finalize() throws Throwable {
        Log.d("ghost", "=============fire finalize============="+Thread.currentThread().getName());
        super.finalize();
        Thread.sleep(80000);//每个手机触发 Timeout 的时长不同，比如 vivo 的某些rom 是2分钟，模拟器统一都是10秒钟，所以在模拟器上效果明显
    }
}
