package ua.ck.allteran.pocketaion.fragments.times;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/13/2015.
 */
public class WholeScheduleFragment extends BasicFragment {

    public WholeScheduleFragment newInstance(String day, int hour) {
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

    }
}
