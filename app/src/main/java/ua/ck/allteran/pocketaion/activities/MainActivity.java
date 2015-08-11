package ua.ck.allteran.pocketaion.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import ua.ck.allteran.pocketaion.R;
import ua.ck.allteran.pocketaion.fragments.MainFragment;
import ua.ck.allteran.pocketaion.fragments.PvPEventsFragment;
import ua.ck.allteran.pocketaion.fragments.ShugoNomandFragment;
import ua.ck.allteran.pocketaion.fragments.StigmasFragment;
import ua.ck.allteran.pocketaion.fragments.TreesFragment;
import ua.ck.allteran.pocketaion.helpers.PreferenceHelper;

import static ua.ck.allteran.pocketaion.utilities.Const.Navigation.*;


public class MainActivity extends BasicActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Drawer mDrawer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.nd_header)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.nd_title_pvp_events).withIcon(R.drawable.ic_pvp_events).
                                withIdentifier(SECTION_PVP_EVENTS),
                        new PrimaryDrawerItem().withName(R.string.nd_title_rifts).withIcon(R.drawable.ic_rifts)
                                .withIdentifier(SECTION_RIFTS),
                        new PrimaryDrawerItem().withName(R.string.nd_title_trees).withIcon(R.drawable.ic_trees)
                                .withIdentifier(SECTION_TREES),
                        new PrimaryDrawerItem().withName(R.string.nd_title_sn).withIcon(R.drawable.ic_default_nd_icon)
                                .withIdentifier(SECTION_SN),
                        new PrimaryDrawerItem().withName(R.string.nd_title_bmshugo).withIcon(R.drawable.ic_default_nd_icon)
                                .withIdentifier(SECTION_BM_SHUGO),
                        new PrimaryDrawerItem().withName(R.string.nd_title_stigmas).withIcon(R.drawable.ic_default_nd_icon)
                                .withIdentifier(SECTION_STIGMAS)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Fragment fragment = null;
                        switch (iDrawerItem.getIdentifier()) {
                            case SECTION_PVP_EVENTS:
                                Log.i(TAG, String.valueOf(iDrawerItem.getIdentifier()));
                                fragment = new PvPEventsFragment();
                                break;
                            case SECTION_RIFTS:
                                Log.i(TAG, String.valueOf(iDrawerItem.getIdentifier()));
                                fragment = new MainFragment();
                                break;
                            case SECTION_TREES:
                                fragment = new TreesFragment();
                                break;
                            case SECTION_SN:
                                fragment = new ShugoNomandFragment();
                                break;
                            case SECTION_BM_SHUGO:
                                fragment = new MainFragment();
                                break;
                            case SECTION_STIGMAS:
                                fragment = new StigmasFragment();
                                break;
                            default:
                                return true;
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container_activity_main, fragment)
                                .commit();
                        return false;
                    }
                })
                .build();
        mDrawer.openDrawer();
        mDrawer.setSelection(SECTION_PVP_EVENTS);

        mDrawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceHelper preferenceHelper = PreferenceHelper.getInstance(this);
        preferenceHelper.showWarning(false);
    }
}
