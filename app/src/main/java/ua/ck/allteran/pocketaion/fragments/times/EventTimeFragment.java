package ua.ck.allteran.pocketaion.fragments.times;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    private TextView mTimeCurrentTextView, mTime0hTextView, mTime1hTextView, mTime2hTextView,
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
    private List<PvPEvent> mNeededEvents;
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
        mRealmFaveEvents.close();
        mRealmSchedule.close();
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

        new CreateDBTask().execute();

        mTimeCurrentTextView = (TextView) view.findViewById(R.id.time_current);
        mTime0hTextView = (TextView) view.findViewById(R.id.time_0h);
        mTime1hTextView = (TextView) view.findViewById(R.id.time_1h);
        mTime2hTextView = (TextView) view.findViewById(R.id.time_2h);

        mEventCurrent = (TextView) view.findViewById(R.id.event_current);
        mEvent0h = (TextView) view.findViewById(R.id.event_0h);
        mEvent1h = (TextView) view.findViewById(R.id.event_1h);
        mEvent2h = (TextView) view.findViewById(R.id.event_2h);

        mStopwatchHelper.updateTime(mActivity, mTime0hTextView, mTime1hTextView, mTime2hTextView);
    }

    public void showNextEvents() {
        for (int i = 0; i < 4; i++) {
            mEventCurrent.setText(mNeededEvents
                    .get(0)
                    .getEventName());
            mEvent0h.setText(mNeededEvents.get(1).getEventName());
            mEvent1h.setText(mNeededEvents.get(2).getEventName());
            mEvent2h.setText(mNeededEvents.get(3).getEventName());
        }

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
                break;
            default:
                return true;
        }
        return true;
    }

    public List<PvPEvent> defineNextEvents(String day, int serverHour, List<PvPEvent> events) {
        List<PvPEvent> definedEvents = new ArrayList<>();
        for (int i = 0; i < Const.DISPLAYED_EVENTS_SIZE; i++) {
            definedEvents.add(i, new PvPEvent(Const.NO_EVENT_ID, getString(R.string.no_event_name)));
        }
        for (PvPEvent event : events) {
            for (int i = 0; i < event.getTime().size(); i++) {
                if (day.equals(event.getTime().get(i).getDay())) {
                    if (serverHour == event.getTime().get(i).getBeginTime()) {
                        definedEvents.add(0, event);
                    }
                    if ((serverHour + 1) == event.getTime().get(i).getBeginTime()) {
                        definedEvents.add(1, event);
                    }
                    if ((serverHour + 2) == event.getTime().get(i).getBeginTime()) {
                        definedEvents.add(2, event);
                    }
                    if ((serverHour + 3) == event.getTime().get(i).getBeginTime()) {
                        definedEvents.add(3, event); //IndexOutOfBound. Index is 3 size is 2
                    }
                }
            }
        }
//        if (currentEvent != null) {
//            definedEvents.add(currentEvent);
//        }
//        if (event1h != null) {
//            definedEvents.add(event1h);
//        }
//        if (event2h != null) {
//            definedEvents.add(event2h);
//        }
//        if (event3h != null) {
//            definedEvents.add(event3h);
//        }
        return definedEvents;
    }

    private class CreateDBTask extends AsyncTask<Void, Void, Void> {
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
            mTimeHours = mStopwatchHelper.getTimeHours();
            mAllEvents = mRealmDatabaseHelper.getAllEvents(mRealmSchedule);
            mNeededEvents = defineNextEvents(mDay, mTimeHours, mAllEvents);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showContent(getView());
            showNextEvents();
        }
    }
}
