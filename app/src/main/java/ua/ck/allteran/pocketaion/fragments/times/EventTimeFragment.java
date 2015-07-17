package ua.ck.allteran.pocketaion.fragments.times;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.adapters.AlertDialogFaveAdapter;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.LoaderResult;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;
import ua.ck.allteran.pocketaion.helpers.StopwatchHelper;
import ua.ck.allteran.pocketaion.tasks.ParseTimeFromJSON;
import ua.ck.allteran.pocketaion.tasks.PvPEventsLoader;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Alteran on 5/22/2015.
 */
public class EventTimeFragment extends BasicFragment implements
        LoaderManager.LoaderCallbacks<LoaderResult>, View.OnLongClickListener {
    private static final String TAG = "Line_text_test";

    private TextView mTime0hTextView, mTime1hTextView, mTime2hTextView,
            mEventCurrent, mEvent0h, mEvent1h, mEvent2h;

    private AppCompatActivity mActivity;
    private PreferenceHelper mPreferenceHelper;
    private StopwatchHelper mStopwatchHelper;
    private RealmHelper mRealmDatabaseHelper;

    private Realm mRealmFaveEvents;

    /**
     * mNeededEvents - define displayed events
     * mAllEvents - define all events that will be stored in inner database
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

        showLoadingBar(getView());
        mActivity.setTitle(R.string.subcategory_time_siege);
        mAllEvents = null;

        mTime0hTextView = (TextView) view.findViewById(R.id.time_0h);
        mTime1hTextView = (TextView) view.findViewById(R.id.time_1h);
        mTime2hTextView = (TextView) view.findViewById(R.id.time_2h);

        mEventCurrent = (TextView) view.findViewById(R.id.event_current);
        mEvent0h = (TextView) view.findViewById(R.id.event_0h);
        mEvent1h = (TextView) view.findViewById(R.id.event_1h);
        mEvent2h = (TextView) view.findViewById(R.id.event_2h);

        //TODO: OnLongClickListener for events
        mEventCurrent.setOnLongClickListener(this);
        mEvent0h.setOnLongClickListener(this);
        mEvent1h.setOnLongClickListener(this);
        mEvent2h.setOnLongClickListener(this);

        mStopwatchHelper.updateTime(mActivity, mTime0hTextView, mTime1hTextView, mTime2hTextView);

        Bundle dataForLoader = new Bundle();
        dataForLoader.putString(PvPEventsLoader.URL_EXTRA, Const.TIME_PULL_URL);
        getLoaderManager().initLoader(Const.PVP_EVENT_LOADER_ID, dataForLoader, this);
        startPvPLoader();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.siege_and_event, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.action_show_whole_schedule:
                break;
            case R.id.action_show_favorites:
                fragment = FavoriteEventsFragment.newInstance(mDay, mTimeHours);
                break;
            default:
                return true;
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_activity_main, fragment)
                .addToBackStack(null)
                .commit();
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
            textToShowCurrent += mNeededEvents.get(0).get(i).getEventName() + ", ";
            if (i == mNeededEvents.get(0).size() - 1) {
                textToShowCurrent = textToShowCurrent.substring(0, textToShowCurrent.toCharArray().length - 2) + ".";
            }
        }
        for (int i = 0; i < mNeededEvents.get(1).size(); i++) {
            textToShow0h += mNeededEvents.get(1).get(i).getEventName() + ", ";
            if (i == mNeededEvents.get(1).size() - 1) {
                textToShow0h = textToShow0h.substring(0, textToShow0h.toCharArray().length - 2) + ".";
            }
        }
        for (int i = 0; i < mNeededEvents.get(2).size(); i++) {
            textToShow1h += mNeededEvents.get(2).get(i).getEventName() + ", ";
            if (i == mNeededEvents.get(2).size() - 1) {
                textToShow1h = textToShow1h.substring(0, textToShow1h.toCharArray().length - 2) + ".";
            }
        }
        for (int i = 0; i < mNeededEvents.get(3).size(); i++) {
            textToShow2h += mNeededEvents.get(3).get(i).getEventName() + ", ";
            if (i == mNeededEvents.get(3).size() - 1) {
                textToShow2h = textToShow2h.substring(0, textToShow2h.toCharArray().length - 2) + ".";
            }

        }
        mEventCurrent.setText(textToShowCurrent);
        mEvent0h.setText(textToShow0h);
        mEvent1h.setText(textToShow1h);
        mEvent2h.setText(textToShow2h);
    }

    /**
     * Next method do some really hard BDSM games with data.
     * To display all events that could be up now - I use 2-dimensional lists. Outer list has fixed
     * size - 4. Inner list(s) has unfixed size, cause there can be more than one event at one time
     */
    public List<List<PvPEvent>> defineNextEvents(String day, int serverHour, List<
            PvPEvent> events) {
        String[] days = defineDaysLine(day);

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
                    if (serverHour == e.getTime().get(i).getBeginTime() ||
                            (e.getTime().get(i).getBeginTime() < serverHour && e.getTime().get(i).getEndTime() > serverHour) &&
                                    e.getTime().get(i).getEndTime() < serverHour + 3) {
                        if (innerEventsCurrent.size() == 10) {
                            innerEventsCurrent.clear();
                        }
                        innerEventsCurrent.add(e);
                    }
                    if ((serverHour + 1) == e.getTime().get(i).getBeginTime() ||
                            (e.getTime().get(i).getBeginTime() < (serverHour + 1) && e.getTime().get(i).getEndTime() > (serverHour + 1) &&
                                    e.getTime().get(i).getEndTime() < serverHour + 4)) {
                        if (innerEvents1h.size() == 10) {
                            innerEvents1h.clear();
                        }
                        innerEvents1h.add(e);
                    }
                    if ((serverHour + 2) == e.getTime().get(i).getBeginTime() ||
                            (e.getTime().get(i).getBeginTime() < (serverHour + 2) && e.getTime().get(i).getEndTime() > (serverHour + 2) &&
                                    e.getTime().get(i).getEndTime() < serverHour + 5)) {
                        if (innerEvents2h.size() == 10) {
                            innerEvents2h.clear();
                        }
                        innerEvents2h.add(e);
                    }
                    if ((serverHour + 3) == e.getTime().get(i).getBeginTime() ||
                            (e.getTime().get(i).getBeginTime() < (serverHour + 3) && e.getTime().get(i).getEndTime() > (serverHour + 3) &&
                                    e.getTime().get(i).getEndTime() < serverHour + 6)) {
                        if (innerEvents3h.size() == 10) {
                            innerEvents3h.clear();
                        }
                        innerEvents3h.add(e);
                    }
                }

                if (serverHour == 21) {
                    currentDay = days[1];
                    if (currentDay.equals(e.getTime().get(i).getDay())) {
                        if (0 == e.getTime().get(i).getBeginTime() ||
                                (e.getTime().get(i).getEndTime() == 2)) {
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
                        if (0 == e.getTime().get(i).getBeginTime() ||
                                (e.getTime().get(i).getEndTime() == 2)) {
                            if (innerEvents3h.size() == 10) {
                                innerEvents3h.clear();
                            }
                            innerEvents3h.add(e);
                        }
                        if (1 == e.getTime().get(i).getBeginTime() ||
                                (e.getTime().get(i).getEndTime() == 2)) {
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
                        if (0 == e.getTime().get(i).getBeginTime() ||
                                (e.getTime().get(i).getEndTime() == 2)) {
                            if (innerEvents3h.size() == 10) {
                                innerEvents3h.clear();
                            }
                            innerEvents3h.add(e);
                        }
                        if (1 == e.getTime().get(i).getBeginTime() ||
                                (e.getTime().get(i).getEndTime() == 2)) {
                            if (innerEvents2h.size() == 10) {
                                innerEvents2h.clear();
                            }
                            innerEvents2h.add(e);
                        }
                        if (2 == e.getTime().get(i).getBeginTime() ||
                                (e.getTime().get(i).getEndTime() == 2)) {
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

        return testDefinedEvents;
    }

    /**
     * This method define next day according to current day.
     */
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

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void startPvPLoader() {
        if (isOnline()) {
            getLoaderManager().getLoader(Const.PVP_EVENT_LOADER_ID).forceLoad();
        } else {
            showNoContent(getView(), getString(R.string.no_network_message));
        }
    }

    @Override
    public android.support.v4.content.Loader<LoaderResult> onCreateLoader(int id, Bundle args) {
        android.support.v4.content.Loader<LoaderResult> loader = null;
        if (id == Const.PVP_EVENT_LOADER_ID) {
            loader = new PvPEventsLoader(mActivity, args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<LoaderResult> loader, LoaderResult data) {
        mAllEvents = data.getAllEvents();

        ParseTimeFromJSON timeParser = new ParseTimeFromJSON(data.getTimeFromNetwork());
        mDay = timeParser.getParsedDay();
        mTimeHours = 20;// Integer.valueOf(timeParser.getParsedTimeHours());

        if (mDay.equals(Const.DAY_ERROR)) {
            showNoContent(getView(), getString(R.string.server_error_message));
        } else {
            mNeededEvents = defineNextEvents(mDay, mTimeHours, mAllEvents);
            cleanUpEvents(mNeededEvents);
            showContent(getView());
            showNextEvents();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<LoaderResult> loader) {

    }

    /**
     * Implementation of View.OnLongClickListener interface for event's textViews
     */
    @Override
    public boolean onLongClick(View v) {
        final List<PvPEvent> events;
        switch (v.getId()) {
            case R.id.event_current:
                events = mNeededEvents.get(0);
                break;
            case R.id.event_0h:
                events = mNeededEvents.get(1);
                break;
            case R.id.event_1h:
                events = mNeededEvents.get(2);
                break;
            case R.id.event_2h:
                events = mNeededEvents.get(3);
                break;
            default:
                events = new ArrayList<>();
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.add_event_to_fav_title));
        final AlertDialogFaveAdapter alertDialogAdapter = new AlertDialogFaveAdapter(getActivity(), events);
        builder.setAdapter(alertDialogAdapter, null);
        builder.show();
        return true;
    }
}

