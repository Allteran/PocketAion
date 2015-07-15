package ua.ck.allteran.pocketaion.fragments.times;

import android.os.Bundle;

import ua.ck.allteran.pocketaion.fragments.BasicFragment;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/14/2015.
 */
public class FavoriteEventsFragment extends BasicFragment {

    public FavoriteEventsFragment newInstance(String day, int hour) {
        FavoriteEventsFragment fragment = new FavoriteEventsFragment();
        Bundle args = new Bundle();
        args.putString(Const.ARG_DAY, day);
        args.putInt(Const.ARG_HOUR, hour);
        fragment.setArguments(args);
        return fragment;
    }
}
