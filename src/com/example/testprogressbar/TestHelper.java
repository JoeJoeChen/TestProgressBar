package com.example.testprogressbar;

import java.util.concurrent.LinkedBlockingQueue;

import android.util.Log;

public class TestHelper implements Runnable
{
    private final static String TAG = TestHelper.class.getSimpleName();

    private LinkedBlockingQueue<ReqBean> tasksQueue = new LinkedBlockingQueue<ReqBean>();

    private boolean isRun = false;

    private ReqBean currentBean = null;

    public TestHelper()
    {
        new Thread(this).start();
    }

    public void add(ReqBean task)
    {
        tasksQueue.add(task);
    }

    public void pause()
    {
        isRun = false;
    }

    public void resume()
    {
        isRun = true;
        currentBean.notifyAll();
    }

    @Override
    public void run()
    {
        isRun = true;
        try
        {
            while ((currentBean = tasksQueue.take()) != null)
            {
                Thread.sleep(1000);
                Log.i(TAG, "run sleep(1000)" + currentBean.getUrl());
                if (!isRun)
                {
                    synchronized (currentBean)
                    {
                        currentBean.wait();
                    }
                }

            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
