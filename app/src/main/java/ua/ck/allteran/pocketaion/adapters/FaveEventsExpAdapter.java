package ua.ck.allteran.pocketaion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.entites.PvPEvent;
import ua.ck.allteran.pocketaion.utilities.Const;

/**
 * Created by Allteran on 7/16/2015.
 */
public class FaveEventsExpAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mLayoutInflater;
    private List<List<PvPEvent>> mEvents;

    private CategoryViewHolder mCategoryViewHolder;
    private SubcategoryViewHolder mSubcategoryViewHolder;

    public FaveEventsExpAdapter(Context context, List<List<PvPEvent>> group) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mEvents = group;
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
            convertView = mLayoutInflater.inflate(R.layout.fave_expandable_list_category, parent, false);

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
        if(convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.fave_expandable_list_subcat,parent,false);

            mSubcategoryViewHolder = new SubcategoryViewHolder();
            mSubcategoryViewHolder.eventName = (TextView) convertView.findViewById(R.id.event_name);
            mSubcategoryViewHolder.deleteFromFaveButton = (ImageButton) convertView.findViewById(R.id.delete_fave_event);
            mSubcategoryViewHolder.setNotificationButton = (ImageButton) convertView.findViewById(R.id.set_notification);

            convertView.setTag(mSubcategoryViewHolder);
        } else {
            mSubcategoryViewHolder = (SubcategoryViewHolder) convertView.getTag();
        }

        mSubcategoryViewHolder.eventName.setText(mEvents.get(groupPosition).get(childPosition).getEventName());
        //TODO: set onClickListeners for ImageButtons;

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
    }
}
