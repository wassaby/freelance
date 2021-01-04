package com.daurenzg.betstars.fragments;

import com.daurenzg.betstars.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SearchTournamentsFragment extends Fragment {
	
	public SearchTournamentsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_search_tournaments, container, false);
         
        return rootView;
    }
}
