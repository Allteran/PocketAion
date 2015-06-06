package ua.ck.allteran.pocketaion.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Allteran on 6/2/2015.
 */
public class PreferenceHelper {
    private static final String IS_FIRST_LAUNCH = "is_first_launch";
    private static PreferenceHelper mInstance;

    private SharedPreferences mSharedPreferences;

    private PreferenceHelper(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferenceHelper getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new PreferenceHelper(context);
        }
        return mInstance;
    }

    public boolean isFirstLaunch() {
        return mSharedPreferences.getBoolean(IS_FIRST_LAUNCH, true);
    }

    public void launchFirstTime(boolean isFirstLaunch) {
        mSharedPreferences.edit()
                .putBoolean(IS_FIRST_LAUNCH, isFirstLaunch)
                .apply();
    }


}
