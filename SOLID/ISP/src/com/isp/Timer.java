package com.isp;

import java.util.TimerTask;

public class Timer
{
    public void register(long timeOut, final TimerClient timerClient)
    {
        java.util.Timer timerUtility = new java.util.Timer();
        timerUtility.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                timerClient.timeOutCallback();
            }
        }, timeOut);
    }
}
