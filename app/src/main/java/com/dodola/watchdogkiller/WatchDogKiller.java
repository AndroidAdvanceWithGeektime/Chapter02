package com.dodola.watchdogkiller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by dodola on 2018/12/3.
 */
public class WatchDogKiller {

    public static boolean checkWatchDogAlive() {
        final Class clazz;
        try {
            clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            final Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            final Object watchdog = field.get(null);
            Method isRunningMethod = clazz.getSuperclass().getDeclaredMethod("isRunning");
            isRunningMethod.setAccessible(true);
            boolean isRunning = (boolean) isRunningMethod.invoke(watchdog);
            return isRunning;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void stopWatchDog() {
        try {
            final Class clazz = Class.forName("java.lang.Daemons$FinalizerWatchdogDaemon");
            final Field field = clazz.getDeclaredField("INSTANCE");
            field.setAccessible(true);
            final Object watchdog = field.get(null);
            try {
                final Field thread = clazz.getSuperclass().getDeclaredField("thread");
                thread.setAccessible(true);
                thread.set(watchdog, null);
            } catch (final Throwable t) {
                try {
                    final Method method = clazz.getSuperclass().getDeclaredMethod("stop");
                    method.setAccessible(true);
                    method.invoke(watchdog);
                } catch (final Throwable e) {
                }
            }
        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }
}
