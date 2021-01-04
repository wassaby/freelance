package com.daurenzg.betstars.fragments;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatsFragment extends Fragment {
	
	public StatsFragment(){}
	
    private ViewPager mViewPager;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        getActivity().getActionBar().setCustomView((ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.ab_stats, null));
        
        mViewPager = (ViewPager) rootView.findViewById(R.id.stats_viewpager);
        mViewPager.setAdapter(new StatsPagerAdapter(getFragmentManager()));
        
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.stats_sliding_tabs);
        tabsStrip.setViewPager(mViewPager);
        return rootView;
    }
	
	// Adapter
    class StatsPagerAdapter extends FragmentStatePagerAdapter /*implements IconTabProvider*/ {
    	
    	StatsFragmentStats stats;
    	StatsFragmentRating ratings;
    	private final String[] TITLES = getResources().getStringArray(R.array.stats_tab_titles);
    	
        public StatsPagerAdapter(FragmentManager fm) {
			super(fm);
			stats = new StatsFragmentStats();
			ratings = new StatsFragmentRating();
			// TODO Auto-generated constructor stub
		}

		@Override
        public int getCount() {
            //return 2;
			return TITLES.length;
        }

		@Override
        public CharSequence getPageTitle(int position) {
			return TITLES[position];
        }

		public Fragment getItem(int i) {
			switch (i) {
	            case 0:
	            	return stats;
	            case 1:
	            	return ratings;
	            default:
	                return null;
			}
		}
		
    }	
}
