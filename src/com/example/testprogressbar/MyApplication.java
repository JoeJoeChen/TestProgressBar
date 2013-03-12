package com.example.testprogressbar;

import android.app.Application;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class MyApplication extends Application
{
    private static final HandlerThread thread = new HandlerThread("MyApplication");
    private static final Handler handler;

    static
    {
        thread.start();
        Looper localLooper = thread.getLooper();
        handler = new Handler(localLooper);
    }

    public void test(Runnable paramRunnable)
    {
        boolean bool = handler.post(paramRunnable);
    }

    public void onCreate()
    {
        super.onCreate();
    }
}