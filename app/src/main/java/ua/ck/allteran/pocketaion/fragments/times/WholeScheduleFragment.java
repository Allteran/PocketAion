package ua.ck.allteran.pocketaion.fragments.times;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.adapters.DatabaseEventsExpAdapter;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/13/2015.
 */
public class WholeScheduleFragment extends BasicFragment {

    private AppCompatActivity mActivity;

    public static WholeScheduleFragment newInstance(String day, int hour) {
        WholeScheduleFragment fragment = new WholeScheduleFragment();
        Bundle args = new Bundle();
        args.putString(Const.ARG_DAY, day);
        args.putInt(Const.ARG_HOUR, hour);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_whole_schedule, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActivity.getSupportActionBar().setTitle(R.string.whole_schedule_title);
        RealmHelper databaseHelper = new RealmHelper();
        List<PvPEvent> eventsFromDatabase = databaseHelper.getAllEvents(mActivity,getString(R.string.default_database_name));
        List<List<PvPEvent>> sortedEvents = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            sortedEvents.add(new ArrayList<PvPEvent>());
        }
        for (int i = 0; i < eventsFromDatabase.size(); i++) {
            for (int j = 0; j < eventsFromDatabase.get(i).getTime().size(); j++) {
                switch (eventsFromDatabase.get(i).getTime().get(j).getDay()) {
                    case Const.DAY_SUNDAY:
                        sortedEvents.get(0).add(eventsFromDatabase.get(i));
                        break;
                    case Const.DAY_MONDAY:
                        sortedEvents.get(1).add(eventsFromDatabase.get(i));
                        break;
                    case Const.DAY_TUESDAY:
                        sortedEvents.get(2).add(eventsFromDatabase.get(i));
                        break;
                    case Const.DAY_WEDNESDAY:
                        sortedEvents.get(3).add(eventsFromDatabase.get(i));
                        break;
                    case Const.DAY_THURSDAY:
                        sortedEvents.get(4).add(eventsFromDatabase.get(i));
                        break;
                    case Const.DAY_FRIDAY:
                        sortedEvents.get(5).add(eventsFromDatabase.get(i));
                        break;
                    case Const.DAY_SATURDAY:
                        sortedEvents.get(6).add(eventsFromDatabase.get(i));
                        break;
                    default:
                        break;
                }
            }
        }
        //Do same as in 'FavoriteEvents'
        for (int i = 0; i < sortedEvents.size(); i++) {
            LinkedHashSet<PvPEvent> bufferSet = new LinkedHashSet<>(sortedEvents.get(i));
            sortedEvents.get(i).clear();
            sortedEvents.get(i).addAll(bufferSet);
        }

        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.all_events_exlist);
        DatabaseEventsExpAdapter adapter = new DatabaseEventsExpAdapter(mActivity, sortedEvents, false);

        expandableListView.setAdapter(adapter);
        String[] daysLine = {Const.DAY_SUNDAY, Const.DAY_MONDAY, Const.DAY_TUESDAY, Const.DAY_WEDNESDAY,
                Const.DAY_THURSDAY, Const.DAY_FRIDAY, Const.DAY_SATURDAY};
        String currentDay = "";
        if (getArguments() != null) {
            currentDay = getArguments().getString(Const.ARG_DAY);
            if (currentDay != null) {
                for (int i = 0; i < daysLine.length; i++) {
                    if (currentDay.equals(daysLine[i])) {
                        expandableListView.expandGroup(i, true);
                    }
                }
            }
        }
    }
}
