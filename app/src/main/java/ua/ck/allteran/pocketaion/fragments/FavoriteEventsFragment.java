package ua.ck.allteran.pocketaion.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.adapters.DatabaseEventsExpAdapter;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/14/2015.
 */
public class FavoriteEventsFragment extends BasicFragment {

    private static final String TAG = FavoriteEventsFragment.class.getSimpleName();
    private AppCompatActivity mActivity;
    private RealmHelper mDatabaseHelper;
    private DatabaseEventsExpAdapter mFaveEventsAdapter;
    private List<List<PvPEvent>> mSortedEvents;
    private List<PvPEvent> mEventsFromDatabase;

    public static FavoriteEventsFragment newInstance(String day, int hour) {
        FavoriteEventsFragment fragment = new FavoriteEventsFragment();
        Bundle args = new Bundle();
        args.putString(Const.ARG_DAY, day);
        args.putInt(Const.ARG_HOUR, hour);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_events, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        assert mActivity.getSupportActionBar() != null;

//        mActivity.getSupportActionBar().setHomeButtonEnabled(true);
//        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActivity.getSupportActionBar().setTitle(R.string.fave_events_title);
        mDatabaseHelper = new RealmHelper();
        mEventsFromDatabase = mDatabaseHelper.getAllEvents(mActivity, getString(R.string.fave_events_database_name));

        displayData(view);
    }

    private void displayData(View view) {
        if (!mEventsFromDatabase.isEmpty()) {
            showFaveList(view);
        } else {
            showNoContent(getView(), getString(R.string.no_fave_events_message));
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fave_events, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem actionDeleteAllFaveEvents = menu.findItem(R.id.action_delete_all_fave_events);
        if (mEventsFromDatabase.isEmpty()) {
            actionDeleteAllFaveEvents.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_fave_events:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.delete_all_fave_events_title)
                        .setMessage(R.string.delete_all_fave_events_message)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDatabaseHelper.deleteAllEvents(mActivity, getString(R.string.fave_events_database_name));
                                Toast.makeText(mActivity, R.string.all_events_deleted_message, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                mEventsFromDatabase.clear();
                                mSortedEvents.clear();
                                mFaveEventsAdapter.notifyDataSetChanged();
                                mActivity.supportInvalidateOptionsMenu();
                                displayData(getView());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                break;
        }
        return true;
    }

    private void showFaveList(View view) {
        mSortedEvents = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            mSortedEvents.add(new ArrayList<PvPEvent>());
        }
        for (int i = 0; i < mEventsFromDatabase.size(); i++) {
            for (int j = 0; j < mEventsFromDatabase.get(i).getTime().size(); j++) {
                switch (mEventsFromDatabase.get(i).getTime().get(j).getDay()) {
                    case Const.DAY_SUNDAY:
                        mSortedEvents.get(0).add(mEventsFromDatabase.get(i));
                        break;
                    case Const.DAY_MONDAY:
                        mSortedEvents.get(1).add(mEventsFromDatabase.get(i));
                        break;
                    case Const.DAY_TUESDAY:
                        mSortedEvents.get(2).add(mEventsFromDatabase.get(i));
                        break;
                    case Const.DAY_WEDNESDAY:
                        mSortedEvents.get(3).add(mEventsFromDatabase.get(i));
                        break;
                    case Const.DAY_THURSDAY:
                        mSortedEvents.get(4).add(mEventsFromDatabase.get(i));
                        break;
                    case Const.DAY_FRIDAY:
                        mSortedEvents.get(5).add(mEventsFromDatabase.get(i));
                        break;
                    case Const.DAY_SATURDAY:
                        mSortedEvents.get(6).add(mEventsFromDatabase.get(i));
                        break;
                    default:
                        break;
                }
            }
        }
        //To delete duplicates I used HasSet which doesn't allow duplicates
        //Using LinkedHashSet I'm saving order that was at the beginning
        for (int i = 0; i < mSortedEvents.size(); i++) {
            LinkedHashSet<PvPEvent> bufferSet = new LinkedHashSet<>(mSortedEvents.get(i));
            mSortedEvents.get(i).clear();
            mSortedEvents.get(i).addAll(bufferSet);
        }
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.fave_events_exlist);
        mFaveEventsAdapter = new DatabaseEventsExpAdapter(mActivity, mSortedEvents, true);

        expandableListView.setAdapter(mFaveEventsAdapter);

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
