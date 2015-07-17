package ua.ck.allteran.pocketaion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.utilities.NavigationDrawerCategory;
import ua.ck.allteran.pocketaion.utilities.NavigationDrawerSubcategory;

public class ExpandableNavigationDraverAdapter extends BaseExpandableListAdapter {

    private LayoutInflater mLayoutInflater;
    private ArrayList<NavigationDrawerCategory> mCategoryNameAdapter = new ArrayList<>();
    private ArrayList<ArrayList<NavigationDrawerSubcategory>> mSubcategoryNameAdapter = new ArrayList<>();
    private ArrayList<Integer> mSubcategoryCountAdapter = new ArrayList<>();
    private NavigationDrawerSubcategory mSingleChild = new NavigationDrawerSubcategory();
    private int mCountAdapter;

    public ExpandableNavigationDraverAdapter(Context context, ArrayList<NavigationDrawerCategory> categoryName,
                                             ArrayList<ArrayList<NavigationDrawerSubcategory>> subcategoryName,
                                             ArrayList<Integer> subcategoryCount) {
        mLayoutInflater = LayoutInflater.from(context);
        mCategoryNameAdapter = categoryName;
        mSubcategoryNameAdapter = subcategoryName;
        mSubcategoryCountAdapter = subcategoryCount;
        mCountAdapter = categoryName.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mCountAdapter;
    }

    @Override
    public int getChildrenCount(int i) {
        return (mSubcategoryCountAdapter.get(i));
    }

    @Override
    public Object getGroup(int i) {
        return mCategoryNameAdapter.get(i).getCategoryName();
    }

    @Override
    public NavigationDrawerSubcategory getChild(int i, int i1) {
        ArrayList<NavigationDrawerSubcategory> tempList;
        tempList = mSubcategoryNameAdapter.get(i);
        return tempList.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.nd_expandable_list_category, viewGroup, false);
        }
        TextView categoryTitle = (TextView) view.findViewById(R.id.category_title);
        categoryTitle.setText(getGroup(i).toString());

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.nd_expandable_list_subcategory, viewGroup, false);
        }

        mSingleChild = getChild(i, i1);

        TextView subcategoryTitle = (TextView) view.findViewById(R.id.subcategory_title);
        subcategoryTitle.setText(mSingleChild.getSubcategoryName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }
}