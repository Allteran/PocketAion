package ua.ck.allteran.pocketaion.activities;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.fragments.MainFragment;
import ua.ck.allteran.pocketaion.utilities.NavigationDrawerCategory;
import ua.ck.allteran.pocketaion.utilities.NavigationDrawerSubcategory;
import static ua.ck.allteran.pocketaion.utilities.Const.Navigation.*;


public class MainActivity extends BasicActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView mCategoryList;

    private ArrayList<NavigationDrawerCategory> mCategoryName = new ArrayList<>();
    private ArrayList<ArrayList<NavigationDrawerSubcategory>> mSubcategoryName = new ArrayList<>();
    private ArrayList<Integer> mSubcategoryCount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDrawerElementsName();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mCategoryList = (ExpandableListView) findViewById(R.id.left_drawer);

        mCategoryList.setAdapter(new ExpandableAdapter(this, mCategoryName, mSubcategoryName, mSubcategoryCount));
//        final int previousGroup;
        Log.i(TAG, String.valueOf(mCategoryName.size()));
        Log.i(TAG, String.valueOf(mSubcategoryName.size()));
        mCategoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if (expandableListView.isGroupExpanded(i)) {
                    expandableListView.collapseGroup(i);
                } else {
                    expandableListView.expandGroup(i);
                }

                expandableListView.smoothScrollToPosition(i);
                return true;
            }
        });


        mCategoryList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                switch (i) {
                    case CAT_TIMES:
                        switch (i1) {
                            case SUBCAT_TIMES_SIEGE:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            case SUBCAT_TIMES_RIFTS:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            case SUBCAT_TIMES_TREES:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            case SUBCAT_TIMES_SN:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                return true;
                        }
                        ;
                        break;
                    case CAT_MAPS:
                        switch (i1) {
                            case SUBCAT_MAPS_RIFTS:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            case SUBCAT_MAPS_TREES:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            case SUBCAT_MAPS_SN:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            case SUBCAT_MAPS_BMSHUGO:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                return true;
                        }
                        ;
                        break;
                    case CAT_STIGMAS:
                        switch (i1) {
                            case SUBCAT_STIGMAS_CALCULATE:
                                Toast.makeText(MainActivity.this, "Group No" + i + " subgroup no +" + i1, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                return true;
                        }
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_activity_main, new MainFragment())
                    .commit();
        }
    }

    private void setDrawerElementsName() {
        mCategoryName.clear();

        NavigationDrawerCategory categoryDetails = new NavigationDrawerCategory();
        categoryDetails.setCategoryCode(10);
        categoryDetails.setCategoryName(getString(R.string.category_times));
        mCategoryName.add(categoryDetails);

        categoryDetails = new NavigationDrawerCategory();
        categoryDetails.setCategoryCode(20);
        categoryDetails.setCategoryName(getString(R.string.category_maps));
        mCategoryName.add(categoryDetails);

        categoryDetails = new NavigationDrawerCategory();
        categoryDetails.setCategoryCode(30);
        categoryDetails.setCategoryName(getString(R.string.category_stigmas));
        mCategoryName.add(categoryDetails);

        //Populate subcategory's list
        mSubcategoryName.clear();
        ArrayList<NavigationDrawerSubcategory> subcategoryMatches = new ArrayList<>();

        NavigationDrawerSubcategory subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_time_siege));
        subcategoryMatch.setSubcategoryCode("1001");
        subcategoryMatches.add(subcategoryMatch);

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_time_rifts));
        subcategoryMatch.setSubcategoryCode("1002");
        subcategoryMatches.add(subcategoryMatch);

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_time_trees));
        subcategoryMatch.setSubcategoryCode("1003");
        subcategoryMatches.add(subcategoryMatch);

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_time_sn));
        subcategoryMatch.setSubcategoryCode("1004");
        subcategoryMatches.add(subcategoryMatch);
        mSubcategoryName.add(subcategoryMatches);
        mSubcategoryCount.add(subcategoryMatches.size());

        subcategoryMatches = new ArrayList<>();

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_map_rifts));
        subcategoryMatch.setSubcategoryCode("2001");
        subcategoryMatches.add(subcategoryMatch);

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_map_trees));
        subcategoryMatch.setSubcategoryCode("2002");
        subcategoryMatches.add(subcategoryMatch);

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_map_sn));
        subcategoryMatch.setSubcategoryCode("2003");
        subcategoryMatches.add(subcategoryMatch);

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_map_bmshugo));
        subcategoryMatch.setSubcategoryCode("2004");
        subcategoryMatches.add(subcategoryMatch);
        mSubcategoryName.add(subcategoryMatches);
        mSubcategoryCount.add(subcategoryMatches.size());

        subcategoryMatches = new ArrayList<>();

        subcategoryMatch = new NavigationDrawerSubcategory();
        subcategoryMatch.setSubcategoryName(getString(R.string.subcategory_stigmas_calculate));
        subcategoryMatch.setSubcategoryCode("3001");
        subcategoryMatches.add(subcategoryMatch);
        mSubcategoryName.add(subcategoryMatches);
        mSubcategoryCount.add(subcategoryMatches.size());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }

    private class ExpandableAdapter extends BaseExpandableListAdapter {

        private LayoutInflater mLayoutInflater;
        private ArrayList<NavigationDrawerCategory> mCategoryNameAdapter = new ArrayList<>();
        private ArrayList<ArrayList<NavigationDrawerSubcategory>> mSubcategoryNameAdapter = new ArrayList<>();
        private ArrayList<Integer> mSubcategoryCountAdapter = new ArrayList<>();
        private NavigationDrawerSubcategory mSingleChild = new NavigationDrawerSubcategory();
        private int mCountAdapter;

        public ExpandableAdapter(Context context, ArrayList<NavigationDrawerCategory> categoryName,
                                 ArrayList<ArrayList<NavigationDrawerSubcategory>> subcategoryName,
                                 ArrayList<Integer> subcategoryCount) {
            mLayoutInflater = LayoutInflater.from(context);
            mCategoryNameAdapter = mCategoryName;
            mSubcategoryNameAdapter = mSubcategoryName;
            mSubcategoryCountAdapter = mSubcategoryCount;
            mCountAdapter = mCategoryName.size();
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
            return mCategoryNameAdapter.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return (mSubcategoryCount.get(i));
        }

        @Override
        public Object getGroup(int i) {
            return mCategoryName.get(i).getCategoryName();
        }

        @Override
        public NavigationDrawerSubcategory getChild(int i, int i1) {
            ArrayList<NavigationDrawerSubcategory> tempList = new ArrayList<>();
            tempList = mSubcategoryName.get(i);
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
                view = mLayoutInflater.inflate(R.layout.expandable_list_category, viewGroup, false);
            }
            TextView categoryTitle = (TextView) view.findViewById(R.id.category_title);
            categoryTitle.setText(getGroup(i).toString());

            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mLayoutInflater.inflate(R.layout.expandable_list_subcategory, viewGroup, false);
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
}
