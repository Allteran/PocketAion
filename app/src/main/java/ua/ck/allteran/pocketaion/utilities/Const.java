package ua.ck.allteran.pocketaion.utilities;

/**
 * Created by Allteran on 5/25/2015.
 */
public class Const {

    public static final int SLEEP_TIME_10_MIN = 10*60;

    public static final String TIME_PULL_URL = "http://json-time.appspot.com/time.json?tz=America/Chicago";

    public static final String DAY_MONDAY = "Mon";
    public static final String DAY_TUESDAY = "Tue";
    public static final String DAY_WEDNESDAY = "Wed";
    public static final String DAY_THURSDAY = "Thu";
    public static final String DAY_FRIDAY = "Fri";
    public static final String DAY_SATURDAY = "Sat";
    public static final String DAY_SUNDAY = "Sun";

    public static final int NO_EVENT_ID = -1;
    public static final int DISPLAYED_EVENTS_SIZE = 4;
    public static final String DAY_ERROR = "Error";

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
        public static final int TEST_ITEM = 1;

    }
}
