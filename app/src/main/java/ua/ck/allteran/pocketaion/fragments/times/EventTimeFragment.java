package ua.ck.allteran.pocketaion.fragments.times;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.databases.CreateDatabaseHelper;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;
import ua.ck.allteran.pocketaion.helpers.StopwatchHelper;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Alteran on 5/22/2015.
 */
public class EventTimeFragment extends BasicFragment {
    private static final String TAG = EventTimeFragment.class.getSimpleName();

    private TextView mTime0hTextView, mTime1hTextView, mTime2hTextView,
            mEventCurrent, mEvent0h, mEvent1h, mEvent2h;

    private AppCompatActivity mActivity;
    private PreferenceHelper mPreferenceHelper;
    private StopwatchHelper mStopwatchHelper;
    private RealmHelper mRealmDatabaseHelper;

    private Realm mRealmFaveEvents;
    private Realm mRealmSchedule;

    /**
     * @param mNeededEvents - define 4 events, that will be displayed on screen
     * @param mAllEvents - define all events that will be stored in inner database
     */
    private List<List<PvPEvent>> mNeededEvents;
    private List<PvPEvent> mAllEvents;
    private String mDay;
    private int mTimeHours;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mRealmFaveEvents = Realm.getInstance(mActivity, getString(R.string.fave_events_database_name));
        mPreferenceHelper = PreferenceHelper.getInstance(mActivity);
        mStopwatchHelper = new StopwatchHelper();
        mRealmDatabaseHelper = new RealmHelper();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRealmFaveEvents != null) {
            mRealmFaveEvents.close();
        }
        if (mRealmSchedule != null) {
            mRealmSchedule.close();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_siege_and_event_time, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mActivity.setTitle(R.string.subcategory_time_siege);
        mAllEvents = null;

        startInnerAsyncTask();

        mTime0hTextView = (TextView) view.findViewById(R.id.time_0h);
        mTime1hTextView = (TextView) view.findViewById(R.id.time_1h);
        mTime2hTextView = (TextView) view.findViewById(R.id.time_2h);

        mEventCurrent = (TextView) view.findViewById(R.id.event_current);
        mEvent0h = (TextView) view.findViewById(R.id.event_0h);
        mEvent1h = (TextView) view.findViewById(R.id.event_1h);
        mEvent2h = (TextView) view.findViewById(R.id.event_2h);

        mStopwatchHelper.updateTime(mActivity, mTime0hTextView, mTime1hTextView, mTime2hTextView);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.siege_and_event, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_show_whole_schedule:
                Toast.makeText(getActivity(), "Actions show whole schedule", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_show_favorites:
                Toast.makeText(getActivity(), "Actions show favorites", Toast.LENGTH_SHORT).show();
                break;
            default:
                return true;
        }
        return true;
    }

    public void cleanUpEvents(List<List<PvPEvent>> events) {
        List<PvPEvent> innerList = new ArrayList<>();
        innerList.add(new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).size() == 10) {
                events.remove(i);
                events.add(i, innerList);
            }
        }
    }

    public void showNextEvents() {
        String textToShowCurrent = "";
        String textToShow0h = "";
        String textToShow1h = "";
        String textToShow2h = "";
        for (int i = 0; i < mNeededEvents.get(0).size(); i++) {
            textToShowCurrent += mNeededEvents.get(0).get(i).getEventName() + "";
        }
        for (int i = 0; i < mNeededEvents.get(1).size(); i++) {
            textToShow0h += mNeededEvents.get(1).get(i).getEventName() + "";
        }
        for (int i = 0; i < mNeededEvents.get(2).size(); i++) {
            textToShow1h += mNeededEvents.get(2).get(i).getEventName() + "";
        }
        for (int i = 0; i < mNeededEvents.get(3).size(); i++) {
            textToShow2h += mNeededEvents.get(3).get(i).getEventName() + "";
        }
        mEventCurrent.setText(textToShowCurrent);
        mEvent0h.setText(textToShow0h);
        mEvent1h.setText(textToShow1h);
        mEvent2h.setText(textToShow2h);
    }

    /**
     * Next method do some really hard BDSM games with data.
     * To display all events that could be up now - I use 2-dimensional lists. Inner
     */
    public List<List<PvPEvent>> defineNextEvents(String day, int serverHour, List<PvPEvent> events) {
        String[] days = defineDaysLine(day);
//        List<List<PvPEvent>> definedEvents = new ArrayList<>();
//        List<PvPEvent> innerEvents;
        //Fill all defined events with 'Default event'
//        for (int i = 0; i < Const.DISPLAYED_EVENTS_SIZE; i++) {
//            innerEvents = new ArrayList<>();
//            for (int j = 0; j < 10; j++) {
//                innerEvents.add(new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
//            }
//            definedEvents.add(innerEvents);
//        }

        List<List<PvPEvent>> testDefinedEvents = new ArrayList<>();
        List<PvPEvent> innerEventsCurrent = new ArrayList<>();
        List<PvPEvent> innerEvents1h = new ArrayList<>();
        List<PvPEvent> innerEvents2h = new ArrayList<>();
        List<PvPEvent> innerEvents3h = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            innerEventsCurrent.add(new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
            innerEvents1h.add(new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
            innerEvents2h.add(new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
            innerEvents3h.add(new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
        }

        for (PvPEvent e : events) {
            String currentDay = days[0];
            for (int i = 0; i < e.getTime().size(); i++) {
                if (currentDay.equals(e.getTime().get(i).getDay())) {
                    if (serverHour == e.getTime().get(i).getBeginTime()) {
                        if (innerEventsCurrent.size() == 10) {
                            innerEventsCurrent.clear();
                        }
                        innerEventsCurrent.add(e);
                    }
                    if ((serverHour + 1) == e.getTime().get(i).getBeginTime()) {
                        if (innerEvents1h.size() == 10) {
                            innerEvents1h.clear();
                        }
                        innerEvents1h.add(e);
                    }
                    if ((serverHour + 2) == e.getTime().get(i).getBeginTime()) {
                        if (innerEvents2h.size() == 10) {
                            innerEvents2h.clear();
                        }
                        innerEvents2h.add(e);
                    }
                    if ((serverHour + 3) == e.getTime().get(i).getBeginTime()) {
                        if (innerEvents3h.size() == 10) {
                            innerEvents3h.clear();
                        }
                        innerEvents3h.add(e);
                    }
                }

                if (serverHour == 21) {
                    currentDay = days[1];
                    if (currentDay.equals(e.getTime().get(i).getDay())) {
                        if (0 == e.getTime().get(i).getBeginTime()) {
                            if (innerEvents3h.size() == 10) {
                                innerEvents3h.clear();
                            }
                            innerEvents3h.add(e);
                        }
                    }
                }
                if (serverHour == 22) {
                    currentDay = days[1];
                    if (currentDay.equals(e.getTime().get(i).getDay())) {
                        if (0 == e.getTime().get(i).getBeginTime()) {
                            if (innerEvents3h.size() == 10) {
                                innerEvents3h.clear();
                            }
                            innerEvents3h.add(e);
                        }
                        if (1 == e.getTime().get(i).getBeginTime()) {
                            if (innerEvents2h.size() == 10) {
                                innerEvents2h.clear();
                            }
                            innerEvents2h.add(e);
                        }
                    }
                }
                if (serverHour == 23) {
                    currentDay = days[1];
                    if (currentDay.equals(e.getTime().get(i).getDay())) {
                        if (0 == e.getTime().get(i).getBeginTime()) {
                            if (innerEvents3h.size() == 10) {
                                innerEvents3h.clear();
                            }
                            innerEvents3h.add(e);
                        }
                        if (1 == e.getTime().get(i).getBeginTime()) {
                            if (innerEvents2h.size() == 10) {
                                innerEvents2h.clear();
                            }
                            innerEvents2h.add(e);
                        }
                        if (2 == e.getTime().get(i).getBeginTime()) {
                            if (innerEvents1h.size() == 10) {
                                innerEvents1h.clear();
                            }
                            innerEvents1h.add(e);
                        }
                    }
                }
            }
        }

        testDefinedEvents.add(0, innerEventsCurrent);
        testDefinedEvents.add(1, innerEvents1h);
        testDefinedEvents.add(2, innerEvents2h);
        testDefinedEvents.add(3, innerEvents3h);


//        for (PvPEvent event : events) {
//            String buffDay = days[0];
//            for (int i = 0; i < event.getTime().size(); i++) {
//                innerEvents = new ArrayList<>();
//                if (buffDay.equals(event.getTime().get(i).getDay())) {
//                    if (serverHour == event.getTime().get(i).getBeginTime()) {
//                        innerEvents.add(event);
//                        if (definedEvents.get(0).size() == 10) {
//                            definedEvents.remove(0);
//                        }
//                        definedEvents.add(0, innerEvents);
//                    }
//                    if ((serverHour + 1) == event.getTime().get(i).getBeginTime()) {
//                        innerEvents.add(event);
//                        if (definedEvents.get(1).size() == 10) {
//                            definedEvents.remove(1);
//                        }
//                        definedEvents.add(1, innerEvents);
//                    }
//                    if ((serverHour + 2) == event.getTime().get(i).getBeginTime()) {
//                        innerEvents.add(event);
//                        if (definedEvents.get(2).size() == 10) {
//                            definedEvents.remove(2);
//                        }
//                        definedEvents.add(2, innerEvents);
//                    }
//                    if ((serverHour + 3) == event.getTime().get(i).getBeginTime()) {
//                        innerEvents.add(event);
//                        if (definedEvents.get(3).size() == 10) {
//                            definedEvents.remove(3);
//                        }
//                        definedEvents.add(3, innerEvents);
//                    }
//                }
//                if (serverHour == 21) {
//                    buffDay = days[1];
//                    if (buffDay.equals(event.getTime().get(i).getDay())) {
//                        if (0 == event.getTime().get(i).getBeginTime()) {
//                            innerEvents.add(event);
//                            if (definedEvents.get(3).size() == 10) {
//                                definedEvents.remove(3);
//                            }
//                            definedEvents.add(3, innerEvents);
//                        }
//
//                    }
//                }
//                if (serverHour == 22) {
//                    buffDay = days[1];
//                    if (buffDay.equals(event.getTime().get(i).getDay())) {
//                        if (0 == event.getTime().get(i).getBeginTime()) {
//                            innerEvents.add(event);
//                            if (definedEvents.get(2).size() == 10) {
//                                definedEvents.remove(2);
//                            }
//                            definedEvents.add(2, innerEvents);
//                        }
//                        if (1 == event.getTime().get(i).getBeginTime()) {
//                            innerEvents.add(event);
//                            if (definedEvents.get(3).size() == 10) {
//                                definedEvents.remove(3);
//                            }
//                            definedEvents.add(3, innerEvents);
//                        }
//                    }
//                }
//                if (serverHour == 23) {
//                    buffDay = days[1];
//                    if (buffDay.equals(event.getTime().get(i).getDay())) {
//                        if (0 == event.getTime().get(i).getBeginTime()) {
//                            innerEvents.add(event);
//                            if (definedEvents.get(1).size() == 10) {
//                                definedEvents.remove(1);
//                            }
//                            definedEvents.add(1, innerEvents);
//                        }
//                        if (1 == event.getTime().get(i).getBeginTime()) {
//                            innerEvents.add(event);
//                            if (definedEvents.get(2).size() == 10) {
//                                definedEvents.remove(2);
//                            }
//                            definedEvents.add(2, innerEvents);
//                        }
//                        if (2 == event.getTime().get(i).getBeginTime()) {
//                            innerEvents.add(event);
//                            if (definedEvents.get(3).size() == 10) {
//                                definedEvents.remove(3);
//                            }
//                            definedEvents.add(3, innerEvents);
//                        }
//                    }
//                }
//            }
//        }
        return testDefinedEvents;
    }

    public String[] defineDaysLine(String day) {
        int shiftPositions = -1;
        String[] definedDayLine = {Const.DAY_SUNDAY, Const.DAY_MONDAY, Const.DAY_TUESDAY, Const.DAY_WEDNESDAY,
                Const.DAY_THURSDAY, Const.DAY_FRIDAY, Const.DAY_SATURDAY};
        for (int i = 0; i < definedDayLine.length; i++) {
            if (day.equals(definedDayLine[i])) {
                shiftPositions = i;
            }
        }
        String[] tempArray = new String[definedDayLine.length];
        System.arraycopy(definedDayLine, shiftPositions, tempArray, 0, definedDayLine.length - shiftPositions);
        System.arraycopy(definedDayLine, 0, tempArray, definedDayLine.length - shiftPositions, shiftPositions);
        return tempArray;
    }

    public void debugMethod() {
        for (int i = 0; i < Const.DISPLAYED_EVENTS_SIZE; i++) {
            for (int j = 0; j < 10; j++) {
                Log.i(TAG,
                        mNeededEvents
                                .get(i)
                                .get(j)
                                .getEventName());
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void startInnerAsyncTask() {
        if (isOnline()) {
            new CreateDBAndPullTimeTask().execute();
        } else {
            showNoContent(getView(), getString(R.string.no_network_message));
        }
    }


    private class CreateDBAndPullTimeTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingBar(getView());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mRealmSchedule = Realm.getInstance(mActivity, getString(R.string.default_database_name));
            if (mPreferenceHelper.isFirstLaunch()) {
                CreateDatabaseHelper dbHelper = new CreateDatabaseHelper();
                dbHelper.createEventDatabase(mRealmSchedule);
                mPreferenceHelper.launchFirstTime(false);
            }
            mStopwatchHelper.getTimeFromNetwork();
            mDay = mStopwatchHelper.getDay();
            mTimeHours = 4;// mStopwatchHelper.getTimeHours();
            mAllEvents = mRealmDatabaseHelper.getAllEvents(mRealmSchedule);
            mRealmSchedule.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (mDay.equals(Const.DAY_ERROR)) {
                showNoContent(getView(), getString(R.string.server_error_message));
            } else {
                mNeededEvents = defineNextEvents(mDay, mTimeHours, mAllEvents);
                cleanUpEvents(mNeededEvents);
                showContent(getView());
                showNextEvents();
            }
        }
    }
}
