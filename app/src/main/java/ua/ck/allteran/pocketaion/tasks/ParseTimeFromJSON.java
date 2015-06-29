package ua.ck.allteran.pocketaion.tasks;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allteran on 5/28/2015.
 */
public class ParseTimeFromJSON {
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
            mParsedTimeHours = timeToParse.getString("hour");
            mParsedTimeMinutes = timeToParse.getString("minute");
            mParsedTimeSeconds = timeToParse.getString("second");
            mParsedDay = timeToParse.getString("datetime").substring(0,3);
        } catch (JSONException e) {
            e.printStackTrace();
            mParsedTimeMinutes = "Error";
            mParsedTimeHours = "Error";
            mParsedTimeSeconds = "Error";
        }
    }
}
