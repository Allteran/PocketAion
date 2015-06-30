package ua.ck.allteran.pocketaion.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import ua.ck.allteran.pocketaion.R;

/**
 * Created by Dante on 5/22/2015.
 */
public abstract class BasicFragment extends Fragment {

    public void showLoadingBar(final View view) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View progressBar = view.findViewById(R.id.loading_bar_container);
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                View contentContainer = view.findViewById(R.id.content_container);
                if (contentContainer != null) {
                    contentContainer.setVisibility(View.GONE);
                }
                View noDataMessage = view.findViewById(R.id.no_data_container);
                if (noDataMessage != null) {
                    noDataMessage.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showContent(final View view) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View progressBar = view.findViewById(R.id.loading_bar_container);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                View contentContainer = view.findViewById(R.id.content_container);
                if (contentContainer != null) {
                    contentContainer.setVisibility(View.VISIBLE);
                }
                View noDataMessage = view.findViewById(R.id.no_data_container);
                if (noDataMessage != null) {
                    noDataMessage.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showNoContent(final View view, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View progressBar = view.findViewById(R.id.loading_bar_container);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                View contentContainer = view.findViewById(R.id.content_container);
                if (contentContainer != null) {
                    contentContainer.setVisibility(View.GONE);
                }
                View noDataMessage = view.findViewById(R.id.no_data_container);
                if (noDataMessage != null) {
                    noDataMessage.setVisibility(View.VISIBLE);
                }
                TextView noDataTextView = (TextView) view.findViewById(R.id.no_data_textview);
                if (noDataTextView != null) {
                    noDataTextView.setText(message);
                }
            }
        });
    }
}
