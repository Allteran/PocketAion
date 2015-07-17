package ua.ck.allteran.pocketaion.fragments.times;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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
import java.util.List;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.adapters.FaveEventsExpAdapter;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/14/2015.
 */
public class FavoriteEventsFragment extends BasicFragment {

    private Realm mRealmFaveEvents;
    private AppCompatActivity mActivity;
    private RealmHelper mDatabaseHelper;

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
    public void onDestroy() {
        super.onDestroy();
        if (mRealmFaveEvents != null) {
            mRealmFaveEvents.close();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
        mRealmFaveEvents = Realm.getInstance(mActivity, getString(R.string.fave_events_database_name));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRealmFaveEvents == null ) {
            mRealmFaveEvents = Realm.getInstance(mActivity, getString(R.string.fave_events_database_name));
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mDatabaseHelper = new RealmHelper();
        List<PvPEvent> eventsFromDatabase = mDatabaseHelper.getAllEvents(mRealmFaveEvents);
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
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.fave_events_exlist);
        FaveEventsExpAdapter adapter = new FaveEventsExpAdapter(mActivity, sortedEvents);

        expandableListView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fave_events, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_fave_events:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.delete_all_fave_events_title)
                        .setMessage(R.string.delete_all_fave_events_message)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mRealmFaveEvents.close();
                                mDatabaseHelper.deleteAllEvents(mRealmFaveEvents, mActivity, getString(R.string.fave_events_database_name));
                                Toast.makeText(mActivity, R.string.all_events_deleted_message, Toast.LENGTH_SHORT).show();
                                mRealmFaveEvents = Realm.getInstance(mActivity, getString(R.string.fave_events_database_name));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                return true;
        }
        return true;
    }
}
