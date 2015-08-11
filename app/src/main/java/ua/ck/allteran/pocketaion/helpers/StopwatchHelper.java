package ua.ck.allteran.pocketaion.helpers;

import android.app.Activity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Allteran on 5/30/2015.
 */
public class StopwatchHelper {
    /**
     * This method will be update time in all TextViews into PvPEventsFragment
     */
    public void updateTime(final Activity activity, final TextView... time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar instance = Calendar.getInstance();
                            double systemTimeMinutes = (double) 59 - instance.get(Calendar.MINUTE);
                            double systemTimeSeconds = (double) 59 - instance.get(Calendar.SECOND);
                            String formatedTime = String.format("%02.0f:%02.0f", systemTimeMinutes, systemTimeSeconds);
                            time[0].setText("00:" + formatedTime);
                            time[1].setText("01:" + formatedTime);
                            time[2].setText("02:" + formatedTime);
                        }
                    });
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
