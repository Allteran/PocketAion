package ua.ck.allteran.pocketaion.tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.TimeZone;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.databases.CreateDatabaseHelper;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.LoaderResult;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/7/2015.
 * Current loader will download string with time from network and add to database all events, if
 * it's first launch of application
 */
public class PvPEventsLoader extends android.support.v4.content.AsyncTaskLoader<LoaderResult> {

    public static final String URL_EXTRA = "link_for_time";
    private static final String TAG = "TimeZone_TAG: ";
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
    public LoaderResult loadInBackground() {
        LoaderResult loaderResult = new LoaderResult();
        Realm realm = Realm.getInstance(mContext, mContext.getString(R.string.default_database_name));
        PreferenceHelper pHelper = PreferenceHelper.getInstance(mContext);
        if (pHelper.isFirstLaunch()) {
            CreateDatabaseHelper dbHelper = new CreateDatabaseHelper();
            dbHelper.createEventDatabase(realm);
            pHelper.launchFirstTime(false);
        }

        loaderResult.setAllEvents(new RealmHelper().getAllEvents(realm));

        OkHttpClient client = new OkHttpClient();
        String timeFromNetwork = "";
        try {
            Request request = new Request.Builder()
                    .url(mUrl)
                    .build();
            Response response = client.newCall(request).execute();
            timeFromNetwork = response.body().string();
        } catch (IOException ex) {
            ex.printStackTrace();
            Calendar calendar = Calendar.getInstance();
            String nativeTimeZone = calendar.getTimeZone().getID();

            calendar.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
            //TODO: check, if there difference in days depends on current time (there shouldn't be like it's 1 am CDT, day is Sun, but displaying Sat (cuz Eu/Kiev has other time)

            timeFromNetwork = "{\"fulldate\":\"" + getDayInString(calendar.get(Calendar.DAY_OF_WEEK)) + "\",\"hours\":" +
                    calendar.get(Calendar.HOUR_OF_DAY) + ",\"minutes\":" + calendar.get(Calendar.MINUTE) + ",\"seconds\":" + calendar.get(Calendar.SECOND) + "}";
            Log.i(TAG, timeFromNetwork);
            calendar.setTimeZone(TimeZone.getTimeZone(nativeTimeZone));
        }

        loaderResult.setTimeFromNetwork(timeFromNetwork);
        realm.close();
        return loaderResult;
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
