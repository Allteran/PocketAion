package ua.ck.allteran.pocketaion.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.activities.MainActivity;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;

/**
 * Created by Dante on 5/24/2015.
 */
public class MainFragment extends BasicFragment {
    private AppCompatActivity mActivity;
    private PreferenceHelper mPreferenceHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
        mPreferenceHelper = PreferenceHelper.getInstance(mActivity);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = (Button) view.findViewById(R.id.delete_realm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferenceHelper.showWarning(false);
            }
        });
    }
}
