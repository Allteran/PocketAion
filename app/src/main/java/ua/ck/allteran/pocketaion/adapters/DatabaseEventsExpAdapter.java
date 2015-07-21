package ua.ck.allteran.pocketaion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/16/2015.
 */
public class DatabaseEventsExpAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mLayoutInflater;
    private List<List<PvPEvent>> mEvents;
    private Context mContext;

    private boolean mForFaveEvents;

    private CategoryViewHolder mCategoryViewHolder;
    private SubcategoryViewHolder mSubcategoryViewHolder;


    public DatabaseEventsExpAdapter(Context context, List<List<PvPEvent>> group, boolean forFaveEvents) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mEvents = group;
        mForFaveEvents = forFaveEvents;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mEvents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mEvents.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mEvents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mEvents.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.database_expandable_list_category, parent, false);

            mCategoryViewHolder = new CategoryViewHolder();
            mCategoryViewHolder.dayName = (TextView) convertView.findViewById(R.id.day_name);
            convertView.setTag(mCategoryViewHolder);
        } else {
            mCategoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }
        String day;
        switch (groupPosition) {
            case 0:
                day = Const.DAY_SUNDAY;
                break;
            case 1:
                day = Const.DAY_MONDAY;
                break;
            case 2:
                day = Const.DAY_TUESDAY;
                break;
            case 3:
                day = Const.DAY_WEDNESDAY;
                break;
            case 4:
                day = Const.DAY_THURSDAY;
                break;
            case 5:
                day = Const.DAY_FRIDAY;
                break;
            case 6:
                day = Const.DAY_SATURDAY;
                break;
            default:
                day = Const.DAY_ERROR;
        }
        mCategoryViewHolder.dayName.setText(day);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.database_expandable_list_subcat, parent, false);

            mSubcategoryViewHolder = new SubcategoryViewHolder();
            mSubcategoryViewHolder.eventName = (TextView) convertView.findViewById(R.id.event_name);
            mSubcategoryViewHolder.setNotificationButton = (ImageButton) convertView.findViewById(R.id.set_notification);
            mSubcategoryViewHolder.deleteFromFaveButton = (ImageButton) convertView.findViewById(R.id.delete_from_db_event);
            mSubcategoryViewHolder.isEventFave = (CheckBox) convertView.findViewById(R.id.is_event_in_fave);

            convertView.setTag(mSubcategoryViewHolder);
        } else {
            mSubcategoryViewHolder = (SubcategoryViewHolder) convertView.getTag();
        }
        //TODO: fix checkbox state
        if (!mForFaveEvents) {
            mSubcategoryViewHolder.deleteFromFaveButton.setVisibility(View.GONE);
            mSubcategoryViewHolder.isEventFave.setVisibility(View.VISIBLE);
        } else {
            mSubcategoryViewHolder.isEventFave.setVisibility(View.GONE);
        }
        mSubcategoryViewHolder.eventName.setText(mEvents.get(groupPosition).get(childPosition).getEventName());
        mSubcategoryViewHolder.setNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Set_notification", Toast.LENGTH_SHORT).show();
            }
        });
        mSubcategoryViewHolder.deleteFromFaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Delete_event_from_fave", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class CategoryViewHolder {
        TextView dayName;
    }

    private static class SubcategoryViewHolder {
        TextView eventName;
        ImageButton deleteFromFaveButton, setNotificationButton;
        CheckBox isEventFave;
    }
}
