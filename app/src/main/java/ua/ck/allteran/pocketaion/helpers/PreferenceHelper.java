package ua.ck.allteran.pocketaion.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Allteran on 6/2/2015.
 */
public class PreferenceHelper {
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
}
