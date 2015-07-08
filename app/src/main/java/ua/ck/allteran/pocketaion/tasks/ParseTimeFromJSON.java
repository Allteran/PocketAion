package ua.ck.allteran.pocketaion.tasks;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 5/28/2015.
 */
public class ParseTimeFromJSON {
    private static final String TAG = "ParseJson";
    private String mParsedTimeHours, mParsedTimeMinutes, mParsedTimeSeconds, mParsedDay;

    public ParseTimeFromJSON(String jsonObjectInString) {
        parseTime(jsonObjectInString);
    }

    public String getParsedTimeHours() {
        return mParsedTimeHours;
    }

    public String getParsedDay() {
        return mParsedDay;
    }

    public String getParsedTimeSeconds() {
        return mParsedTimeSeconds;
    }

    public String getParsedTimeMinutes() {
        return mParsedTimeMinutes;
    }

    private void parseTime(String incTime) {
        JSONObject timeToParse;
        try {
            timeToParse = new JSONObject(incTime);
            mParsedTimeHours = timeToParse.getString("hours");
            mParsedTimeMinutes = timeToParse.getString("minutes");
            mParsedTimeSeconds = timeToParse.getString("seconds");
            mParsedDay = timeToParse.getString("fulldate").substring(0,3);
        } catch (JSONException e) {
            e.printStackTrace();
            mParsedTimeMinutes = "61";
            mParsedTimeHours = "25";
            mParsedTimeSeconds = "61";
            mParsedDay = Const.DAY_ERROR;
        }
    }
}
