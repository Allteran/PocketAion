package ua.ck.allteran.pocketaion.helpers;

import android.app.Activity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import ua.ck.allteran.pocketaion.tasks.DownloadString;
import ua.ck.allteran.pocketaion.tasks.ParseTimeFromJSON;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 5/30/2015.
 */
public class StopwatchHelper {

    public int getTimeFromNetwork() {
        ParseTimeFromJSON timeParser;
        DownloadString downloadedString;
        int timeHours;
        downloadedString = new DownloadString(Const.TIME_PULL_URL);
        timeParser = new ParseTimeFromJSON(downloadedString.getDownloadedString());
        timeHours = Integer.valueOf(timeParser.getParsedTimeHours());
        return timeHours;
    }

    /**
     * This method will be update time in all TextViews into EventTimeFragment
     */
    public static void updateTime(final Activity activity, final TextView... time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Calendar instance = Calendar.getInstance();
                            int systemTimeMinutes = 60 - instance.get(Calendar.MINUTE);
                            int systemTimeSeconds = 60 - instance.get(Calendar.SECOND);
                            String formatedTime = String.format("%2d:%2d", systemTimeMinutes,systemTimeSeconds); //format this string to display time like '00:03:01'
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
