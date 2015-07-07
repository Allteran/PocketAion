package ua.ck.allteran.pocketaion.tasks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

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
    private static final String TAG = "Loader_TimeZONE";
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

        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(mUrl);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String buffString;
            while ((buffString = bufferedReader.readLine()) != null) {
                stringBuilder.append(buffString);
            }
        } catch (IOException e) {
            //when server is down - get time from device
            e.printStackTrace();

        }

        loaderResult.setTimeFromNetwork(stringBuilder.toString());
        realm.close();
        return loaderResult;
    }
}
