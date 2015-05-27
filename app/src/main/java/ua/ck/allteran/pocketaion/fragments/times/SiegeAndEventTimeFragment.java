package ua.ck.allteran.pocketaion.fragments.times;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;

/**
 * Created by Alteran on 5/22/2015.
 */
public class SiegeAndEventTimeFragment extends BasicFragment {
    private TextView mTimeCurrent, mTime1h, mTime2h, mTime3h,
            mEventCurrent, mEvent1h, mEvent2h, mEvent3h;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Toast.makeText(getActivity(), "Actions show whole shedule", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_add_to_favorites:
                break;
            default:
                return true;
        }
        return true;
    }
}
