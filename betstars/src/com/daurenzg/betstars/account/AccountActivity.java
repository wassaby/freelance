package com.daurenzg.betstars.account;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.fragments.BalanceFragment;
import com.daurenzg.betstars.fragments.FaqFragment;
import com.daurenzg.betstars.fragments.NewsFragment;
import com.daurenzg.betstars.fragments.ProfileFragment;
import com.daurenzg.betstars.fragments.SearchTournamentsFragment;
import com.daurenzg.betstars.fragments.StatsFragment;
import com.daurenzg.betstars.fragments.TournamentsFragment;
import com.daurenzg.betstars.sliding.menu.model.NavDrawerItem;
import com.daurenzg.betstars.sliding.menu.model.NavDrawerListAdapter;
import com.daurenzg.betstars.tournaments.TabListener;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.AuthItem;
import com.daurenzg.betstars.wao.ProfileItem;

public class AccountActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar actionBar = null;
	private int navigationMode;
	private int currentMenuItemIndex = 0;
	private IAndroidUtils androidUtils;
	ProfileItem data;
	AuthItem auth;
	TextView txtTransfer;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		auth = (AuthItem) getIntent().getSerializableExtra("authItem");
		data = auth.getProfItem();
		actionBar = getActionBar();
		navigationMode = actionBar.getNavigationMode();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayUseLogoEnabled(false);
		androidUtils = AndroidUtilsFactory.getInstanse(this).getAndroidUtils();

		
		
		/*
		 * final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
		 * .inflate(R.layout.ab_profile, null);
		 * 
		 * final ActionBar actionBar = getActionBar();
		 * actionBar.setDisplayShowTitleEnabled(false);
		 * actionBar.setDisplayShowCustomEnabled(true);
		 * actionBar.setCustomView(actionBarLayout);
		 */

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		// HEADER
		View header = getLayoutInflater().inflate(R.layout.header, null);
		mDrawerList.addHeaderView(header);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		/*
		 * navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
		 * .getResourceId(0, -1)));
		 */
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		/**
		 * navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
		 * .getResourceId(1, -1)));
		 **/
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1), true, "22"));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_menu, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		/*switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}*/
		return super.onOptionsItemSelected(item);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		if (drawerOpen) { // Если открыли меню
			actionBar.setDisplayShowCustomEnabled(false); // убираем "TRANSFER" из action bar
		} else {
			actionBar.setDisplayShowCustomEnabled(true);
			
		}
		//menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		actionBar.setNavigationMode(navigationMode);
		Fragment fragment = null;
		currentMenuItemIndex = position;
		Bundle bundle = new Bundle();
		bundle.putSerializable("data", data);
		switch (position) {
		case 0:
			fragment = new ProfileFragment();
			fragment.setArguments(bundle);
			break;
		case 1:
			fragment = new NewsFragment();
			break;
		/**
		 * case 2: fragment = new MessagesFragment(); break;
		 **/
		case 2:
			fragment = new TournamentsFragment();
			break;
		case 3:
			fragment = new StatsFragment();
			break;
		case 4:
			fragment = new BalanceFragment();
			fragment.setArguments(bundle);
			break;
		case 5:
			fragment = new FaqFragment();
			break;
		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
