package ua.ck.allteran.pocketaion.fragments;

import android.content.DialogInterface;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.adapters.AlertDialogFaveAdapter;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.entites.PvPEventsLoaderResult;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;
import ua.ck.allteran.pocketaion.helpers.StopwatchHelper;
import ua.ck.allteran.pocketaion.tasks.ParseTimeFromJSON;
import ua.ck.allteran.pocketaion.tasks.PvPEventsLoader;
import ua.ck.allteran.pocketaion.utilities.Const;
import ua.ck.allteran.pocketaion.utilities.Utils;

/**
 * Created by Alteran on 5/22/2015.
 */
public class PvPEventsFragment extends BasicFragment implements
        LoaderManager.LoaderCallbacks<PvPEventsLoaderResult>, View.OnLongClickListener {

    private static final String TAG = PvPEventsFragment.class.getSimpleName();
    private TextView mTime0hTextView, mTime1hTextView, mTime2hTextView,
            mEventCurrent, mEvent0h, mEvent1h, mEvent2h;

    private AppCompatActivity mActivity;
    private PreferenceHelper mPreferenceHelper;
    private StopwatchHelper mStopwatchHelper;

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
        mPreferenceHelper = PreferenceHelper.getInstance(mActivity);
        mStopwatchHelper = new StopwatchHelper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pvp_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        mActivity.getSupportActionBar().setTitle(R.string.pvp_events_title);

        showLoadingBar(getView());
        mAllEvents = null;

        mTime0hTextView = (TextView) view.findViewById(R.id.time_0h);
        mTime1hTextView = (TextView) view.findViewById(R.id.time_1h);
        mTime2hTextView = (TextView) view.findViewById(R.id.time_2h);

        mEventCurrent = (TextView) view.findViewById(R.id.event_current);
        mEvent0h = (TextView) view.findViewById(R.id.event_0h);
        mEvent1h = (TextView) view.findViewById(R.id.event_1h);
        mEvent2h = (TextView) view.findViewById(R.id.event_2h);

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
        inflater.inflate(R.menu.pvp_event, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.action_show_whole_schedule:
                fragment = WholeScheduleFragment.newInstance(mDay, mTimeHours);
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
            if (events.get(i).size() == Const.MAX_EVENT_IN_HOUR) {
                events.remove(i);
                events.add(i, innerList);
            }
        }
    }

    /**
     * Transforming text from List into strings to display it on screen
     */
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

    public void startPvPLoader() {
        if (!Utils.isNetworkAvailable(mActivity) && !mPreferenceHelper.isWarningShowed()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.some_problems_title)
                    .setMessage(R.string.no_network_message)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
            mPreferenceHelper.showWarning(true);
        }
        getLoaderManager().getLoader(Const.PVP_EVENT_LOADER_ID).forceLoad();
    }

    @Override
    public android.support.v4.content.Loader<PvPEventsLoaderResult> onCreateLoader(int id, Bundle args) {
        android.support.v4.content.Loader<PvPEventsLoaderResult> loader = null;
        if (id == Const.PVP_EVENT_LOADER_ID) {
            loader = new PvPEventsLoader(mActivity, args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<PvPEventsLoaderResult> loader, PvPEventsLoaderResult data) {
        mAllEvents = data.getAllEvents();

        ParseTimeFromJSON timeParser = new ParseTimeFromJSON(data.getTimeFromNetwork());
        mDay = Const.DAY_MONDAY; //timeParser.getParsedDay();
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
    public void onLoaderReset(android.support.v4.content.Loader<PvPEventsLoaderResult> loader) {

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
        AlertDialogFaveAdapter alertDialogAdapter = new AlertDialogFaveAdapter(mActivity, events);
        builder.setAdapter(alertDialogAdapter, null);
        builder.show();
        return true;
    }
}

