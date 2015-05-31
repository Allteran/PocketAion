package ua.ck.allteran.pocketaion.tasks;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allteran on 5/28/2015.
 */
public class ParseTimeFromJSON {
    private String mParsedTimeHours, mParsedTimeMinutes;

    public ParseTimeFromJSON(String jsonObjectInString) {
        parseTime(jsonObjectInString);
    }

    public String getParsedTimeHours() {
        return mParsedTimeHours;
    }

    public String getParsedTimeMinutes() {
        return mParsedTimeMinutes;
    }

    private void parseTime(String incTime) {
        JSONObject timeToParse;
        try {
            timeToParse = new JSONObject(incTime);
            String hours = timeToParse.getString("hour");
            String minutes = timeToParse.getString("minute");
            mParsedTimeHours = hours;
            mParsedTimeMinutes = minutes;
        } catch (JSONException e) {
            e.printStackTrace();
            mParsedTimeMinutes = "Error";
            mParsedTimeHours = "Error";
        }
    }
}
