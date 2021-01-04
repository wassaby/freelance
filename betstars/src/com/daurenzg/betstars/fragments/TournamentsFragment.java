package com.daurenzg.betstars.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.PagerSlidingTabStrip;
import com.daurenzg.betstars.utils.SlidingTabLayout;

public class TournamentsFragment extends android.support.v4.app.Fragment/*Activity*/ {
	private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    
	public TournamentsFragment(){}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
        
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        getActivity().getActionBar().setCustomView((ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.ab_faq, null));
        
        mViewPager = (ViewPager) rootView.findViewById(R.id.faq_viewpager);
        mViewPager.setAdapter(new TournamentsPagerAdapter(getFragmentManager()));
        //mSlidingTabLayout = (SlidingTabLayout) rootView.findViewById(R.id.faq_sliding_tabs);
        //mSlidingTabLayout.setViewPager(mViewPager);
        
        //mSlidingTabLayout.setFadingEdgeLength(0);
        //mSlidingTabLayout.setDividerColors(getResources().getColor(R.color.white));
        
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.faq_sliding_tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(mViewPager);
        return rootView;
    }
	
	public void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
	}
	
    // Adapter
	class TournamentsPagerAdapter extends FragmentStatePagerAdapter /*implements IconTabProvider*/ {
		TournamentFragmentCurrent current;
		TournamentFragmentSearch search;
    	private final String[] TITLES = getResources().getStringArray(R.array.tournament_tab_titles);
    	//private int tabIcons[] = {R.drawable.ic_faq, R.drawable.ic_rules};
    	
    	/*@Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }*/
    	
        public TournamentsPagerAdapter(FragmentManager fm) {
			super(fm);
			current = new TournamentFragmentCurrent();
			search = new TournamentFragmentSearch();
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
	            	return current;
	            case 1:
	            	return search;
	            default:
	                return null;
			}
		}
		
		/*@Override
		public int getPageIconResId(int position) {
	        return tabIcons[position];
	    }*/
		
    }	
}
