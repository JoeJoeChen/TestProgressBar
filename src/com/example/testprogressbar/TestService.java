package com.example.testprogressbar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class TestService extends Service
{

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        new Thread()
        {
            public void run()
            {
                for (int i = 0; i < 10; i++)
                {
                    Log.i("TestService", "onStartCommand for++" + i);
                    SystemClock.sleep(1000);
                    Intent it = new Intent();
                    it.setAction("test");
                    sendBroadcast(it);
                }
            };
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy()
    {
        Log.w("TestService", "onDestroy");
        super.onDestroy();
    }

}
