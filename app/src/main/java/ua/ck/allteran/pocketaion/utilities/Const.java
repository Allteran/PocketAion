package ua.ck.allteran.pocketaion.utilities;

/**
 * Created by Allteran on 5/25/2015.
 */
public class Const {

    public static final int SLEEP_TIME_10_MIN = 10*60;

    public static final String TIME_PULL_URL = "http://json-time.appspot.com/time.json?tz=America/Chicago";

    public static final String DAY_MONDAY = "Monday";
    public static final String DAY_TUESDAY = "Tuesday";
    public static final String DAY_WEDNESDAY = "Wednesday";
    public static final String DAY_THURSDAY = "Thursday";
    public static final String DAY_FRIDAY = "Friday";
    public static final String DAY_SATURDAY = "Saturday";
    public static final String DAY_SUNDAY = "Sunday";

    public static class Navigation{
        //category's constants
        public static final int CAT_TIMES = 0;
        public static final int CAT_MAPS = 1;
        public static final int CAT_STIGMAS = 2;

        //subcategory's constants
        //for 'Time' category
        public static final int SUBCAT_TIMES_SIEGE = 0;
        public static final int SUBCAT_TIMES_RIFTS = 1;
        public static final int SUBCAT_TIMES_TREES = 2;
        public static final int SUBCAT_TIMES_SN = 3;

        //for 'Maps' category
        public static final int SUBCAT_MAPS_RIFTS = 0;
        public static final int SUBCAT_MAPS_TREES = 1;
        public static final int SUBCAT_MAPS_SN = 2;
        public static final int SUBCAT_MAPS_BMSHUGO = 3;

        //for 'Stigmas' category
        public static final int SUBCAT_STIGMAS_CALCULATE = 0;

    }
}
