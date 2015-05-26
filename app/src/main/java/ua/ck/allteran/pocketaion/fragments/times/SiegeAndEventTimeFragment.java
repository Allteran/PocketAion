package ua.ck.allteran.pocketaion.fragments.times;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.fragments.BasicFragment;

/**
 * Created by Alteran on 5/22/2015.
 */
public class SiegeAndEventTimeFragment extends BasicFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_siege_and_event_time,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
