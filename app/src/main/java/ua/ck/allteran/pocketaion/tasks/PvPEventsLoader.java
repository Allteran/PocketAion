package ua.ck.allteran.pocketaion.tasks;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.databases.CreateDatabaseHelper;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.PvPEventsLoaderResult;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;
import ua.ck.allteran.pocketaion.utilities.Const;
import ua.ck.allteran.pocketaion.utilities.Utils;

/**
 * Created by Allteran on 7/7/2015.
 * Current loader will download string with time from network and add to database all events, if
 * it's first launch of application
 */
public class PvPEventsLoader extends android.support.v4.content.AsyncTaskLoader<PvPEventsLoaderResult> {

    public static final String URL_EXTRA = "link_for_time";
    private static final String TAG = "PvPEventLoaderTag ";
    private Context mContext;
    private String mUrl;


    public PvPEventsLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            mUrl = args.getString(URL_EXTRA);
        } else {
            mUrl = Const.TIME_PULL_URL;
        }
        mContext = context;
    }

    @Override
    public PvPEventsLoaderResult loadInBackground() {
        PvPEventsLoaderResult loaderResult = new PvPEventsLoaderResult();
        PreferenceHelper pHelper = PreferenceHelper.getInstance(mContext);
        if (pHelper.isFirstAppLaunch()) {
            CreateDatabaseHelper dbHelper = new CreateDatabaseHelper();
            dbHelper.createEventDatabase(mContext, mContext.getString(R.string.default_database_name));
            pHelper.launchAppFirstTime(false);
        }

        loaderResult.setAllEvents(new RealmHelper().getAllEvents(mContext, mContext.getString(R.string.default_database_name)));

        OkHttpClient client = new OkHttpClient();
        String time = "";
        Log.i(TAG, "loader started");
        if (Utils.isNetworkAvailable(mContext)) {
            Log.i(TAG, "network is available");
            try {
                Log.i(TAG, "trying to get time from network");
                Request request = new Request.Builder()
                        .url(mUrl)
                        .build();
                Response response = client.newCall(request).execute();
                time = response.body().string();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.i(TAG, "failure, catching exception");
                time = getTimeFromDevice(time);
            }
        } else {
            Log.i(TAG, "network isn't available");
            time = getTimeFromDevice(time);
        }

        loaderResult.setTimeFromNetwork(time);
        return loaderResult;
    }

    private String getTimeFromDevice(String timeFromNetwork) {
        Calendar calendar = Calendar.getInstance();
        String nativeTimeZone = calendar.getTimeZone().getID();

        calendar.setTimeZone(TimeZone.getTimeZone("America/Chicago"));

        timeFromNetwork = "{\"fulldate\":\"" + getDayInString(calendar.get(Calendar.DAY_OF_WEEK)) + "\",\"hours\":" +
                calendar.get(Calendar.HOUR_OF_DAY) + ",\"minutes\":" + calendar.get(Calendar.MINUTE) + ",\"seconds\":" + calendar.get(Calendar.SECOND) + "}";
        Log.i(TAG, timeFromNetwork);
        calendar.setTimeZone(TimeZone.getTimeZone(nativeTimeZone));
        return timeFromNetwork;
    }

    private String getDayInString(int dayOfWeek) {
        switch (dayOfWeek) {
            case 1:
                return Const.DAY_SUNDAY;
            case 2:
                return Const.DAY_MONDAY;
            case 3:
                return Const.DAY_TUESDAY;
            case 4:
                return Const.DAY_WEDNESDAY;
            case 5:
                return Const.DAY_THURSDAY;
            case 6:
                return Const.DAY_FRIDAY;
            case 7:
                return Const.DAY_SATURDAY;
            default:
                return Const.DAY_ERROR;
        }
    }
}
