package com.example.testprogressbar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity
{
    public static boolean isRuning = false;

    ProgressBar bar;
    int i = 0;
    TestHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new TestHelper();

        bar = (ProgressBar) findViewById(R.id.test);
        bar.setMax(10);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isRuning = true;
                i++;
                Log.i("sendsend", "send" + i);
                 Intent intent = new Intent(MainActivity.this, TestIntentService.class);
                 startService(intent);
//                helper.add(new ReqBean(i + ""));
            }
        });

        Button soptBtn = (Button) findViewById(R.id.btn2);
        soptBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                isRuning = false;
                Intent intent = new Intent(MainActivity.this, TestIntentService.class);
                stopService(intent);
            }
        });
        Button exitBtn = (Button) findViewById(R.id.btn3);
        exitBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.exit(0);
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction("test");
        filter.addAction("onDestroy");
        registerReceiver(receiver, filter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            if (action != null)
            {
                Log.i("onReceive", "action");
                if (action.equals("test"))
                {
                    i += 1;
                    bar.setProgress(i);
                }
                else if (action.equals("onDestroy"))
                {
                    Toast.makeText(MainActivity.this, "onDestroy", 0).show();
                }
            }
        }
    };

    protected void onDestroy()
    {
        unregisterReceiver(receiver);
        super.onDestroy();

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
