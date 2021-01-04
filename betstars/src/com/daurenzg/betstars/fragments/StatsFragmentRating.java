package com.daurenzg.betstars.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.adapter.RatingListViewAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.RatingItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

public class StatsFragmentRating extends Fragment {
	private int page = 1;
	IAndroidUtils androidUtils;
	private List<RatingItem> data;
	AbsListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_stats_rating,
				container, false);
		androidUtils = AndroidUtilsFactory.getInstanse(getActivity()).getAndroidUtils();
		listView = (ListView) rootView.findViewById(R.id.list_rating);
		
		new AsyncTask<Void, List<RatingItem>, List<RatingItem>>() {

			@Override
			protected List<RatingItem> doInBackground(Void... params) {
				data = new ArrayList<RatingItem>();
				try {
					data = androidUtils.getRating(page);
				} catch (BUtilsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return data;
			}
			
			@Override
			protected void onPostExecute(List<RatingItem> result) {
				((ListView) listView).setAdapter(new RatingListViewAdapter(getActivity(), data));
			}
		}.execute();
		return rootView;
	}

}
