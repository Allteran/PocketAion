package ua.ck.allteran.pocketaion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.databases.RealmHelper;
import ua.ck.allteran.pocketaion.entites.PvPEvent;

/**
 * Created by Allteran on 7/17/2015.
 */
public class AlertDialogFaveAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<PvPEvent> mEvents;
    private Realm mRealmFaveEvents;

    public AlertDialogFaveAdapter(Context context, List<PvPEvent> events) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mEvents = events;
        mRealmFaveEvents = Realm.getInstance(context, context.getString(R.string.fave_events_database_name));
    }

    @Override
    public int getCount() {
        return mEvents.size();
    }

    @Override
    public PvPEvent getItem(int position) {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mEvents.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.alert_dialog_fave_event, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mCurrentEventName = (TextView) convertView.findViewById(R.id.current_event_name);
            viewHolder.mIsEventFave = (CheckBox) convertView.findViewById(R.id.is_event_fave);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mCurrentEventName.setText(mEvents.get(position).getEventName());
        final RealmHelper databaseHelper = new RealmHelper();
        viewHolder.mIsEventFave.setChecked(databaseHelper.isEventInDatabase(mRealmFaveEvents, mEvents.get(position)));
        viewHolder.mIsEventFave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = !((CheckBox) v).isChecked();
                if (checked) {
                    databaseHelper.deleteEvent(mRealmFaveEvents, mEvents.get(position));
                } else {
                    databaseHelper.addEventToDatabase(mRealmFaveEvents, mEvents.get(position));
                }
                ((CheckBox)v).setChecked(!checked);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView mCurrentEventName;
        CheckBox mIsEventFave;
    }
}
