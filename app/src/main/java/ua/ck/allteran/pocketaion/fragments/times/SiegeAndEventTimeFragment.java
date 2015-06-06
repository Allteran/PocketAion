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

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.databases.CreateDatabaseHelper;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;

/**
 * Created by Alteran on 5/22/2015.
 */
public class SiegeAndEventTimeFragment extends BasicFragment {
    private TextView mTimeCurrent, mTime1h, mTime2h, mTime3h,
            mEventCurrent, mEvent1h, mEvent2h, mEvent3h;
    private AppCompatActivity mActivity;
    private Realm mRealmSchedule, mRealmFaveEvents;
    private PreferenceHelper mPreferenceHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mRealmSchedule = Realm.getInstance(mActivity, getString(R.string.default_database_name));
        mRealmFaveEvents = Realm.getInstance(mActivity, getString(R.string.fave_events_database_name));
        mPreferenceHelper = PreferenceHelper.getInstance(mActivity);
        if(mPreferenceHelper.isFirstLaunch()) {
            new CreateDBTask().execute();
            mPreferenceHelper.launchFirstTime(false);
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

        mTimeCurrent = (TextView) view.findViewById(R.id.time_current);
        mTime1h = (TextView) view.findViewById(R.id.time_1h);
        mTime2h = (TextView) view.findViewById(R.id.time_2h);
        mTime3h = (TextView) view.findViewById(R.id.time_3h);

        mEventCurrent = (TextView) view.findViewById(R.id.event_current);
        mEvent1h = (TextView) view.findViewById(R.id.event_1h);
        mEvent2h = (TextView) view.findViewById(R.id.event_2h);
        mEvent3h = (TextView) view.findViewById(R.id.event_3h);
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
    private class CreateDBTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingBar(getView());
        }

        @Override
        protected Void doInBackground(Void... voids) {
            CreateDatabaseHelper dbHelper = new CreateDatabaseHelper();
            dbHelper.createEventDatabase(mRealmSchedule);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showSchedule(getView());
        }
    }
}
