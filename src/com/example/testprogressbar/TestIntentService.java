package com.example.testprogressbar;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public class TestIntentService extends IntentService
{
    private static NotificationManager notificationManager;
    private static Notification notification;
    private static PendingIntent pendingIntent;

    public TestIntentService()
    {
        super("myTestIntentService");
    }

    public TestIntentService(String name)
    {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        test();
        Log.i("TestIntentService", "onHandleIntent");
        try
        {
            for (int i = 0; i < 10; i++)
            {
                if (MainActivity.isRuning)
                {
                    Log.i("TestIntentService", " for ++ " + i);
                    SystemClock.sleep(1000);
                    sendBroadCastReceiver("test");
                }
            }
        } catch (Exception e)
        {
            Log.e("TestIntentService", "onHandleIntent");
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy()
    {
        Log.w("TestIntentService", "onDestroy");
        sendBroadCastReceiver("onDestroy");
        super.onDestroy();
    }

    private void sendBroadCastReceiver(String str)
    {
        Intent i = new Intent();
        i.setAction(str);
        sendBroadcast(i);

        sendNotification();
    }

    private void sendNotification()
    {
        int totalSize = 10;
        int comCount = 0;
        Log.i("TestIntentService", "sendNotification");
        notification.contentView.setProgressBar(R.id.offline_download_tip_progressBar, 10,
                comCount, true);
        notification.contentView.setTextViewText(R.id.offline_download_tip_title, "");
        notification.contentView.setTextViewText(R.id.offline_download_tip_process,
                (100 * comCount) / totalSize + "");
        notificationManager.notify(0, notification);

    }

    public void test()
    {
        int totalSize = 10;
        int comCount = 0;
        comCount++;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(android.R.drawable.stat_sys_download, "离线下载",
                System.currentTimeMillis());
        Intent intent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification.contentIntent = pendingIntent;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.contentView = new RemoteViews(getApplication().getPackageName(),
                R.layout.offline_notification);
        notification.contentView.setProgressBar(R.id.offline_download_tip_progressBar, 10,
                comCount, true);

        notification.contentView.setTextViewText(R.id.offline_download_tip_title, "热推");
        notification.contentView.setTextViewText(R.id.offline_download_tip_status, "正在下载");
        notification.contentView.setTextViewText(R.id.offline_download_tip_process,
                (100 * comCount) / totalSize + "");

        notificationManager.notify(0, notification);
    }
}
