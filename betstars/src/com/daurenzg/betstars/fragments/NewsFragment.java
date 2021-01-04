package com.daurenzg.betstars.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.adapter.NewsFragmentNewsTitleAdapter;
import com.daurenzg.betstars.news.NewsDetailActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BNewsItem;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class NewsFragment extends Fragment {
	
	public NewsFragment(){}
	
	private int page = 1;
	IAndroidUtils androidUtils;
	List<BNewsItem> newsList;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        androidUtils = AndroidUtilsFactory.getInstanse(getActivity()).getAndroidUtils();
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        getActivity().getActionBar().setCustomView((ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.ab_news, null));
        final ListView newsTitleLiView = (ListView) rootView.findViewById(R.id.newsTitleListView);
        
        new AsyncTask<Void, List<BNewsItem>, List<BNewsItem>>() {

			@Override
			protected List<BNewsItem> doInBackground(Void... params) {
				newsList = new ArrayList<BNewsItem>();
				try {
					newsList = androidUtils.getNews(1);
				} catch (BUtilsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return newsList;
			}
			
			@Override
			protected void onPostExecute(List<BNewsItem> result) {
				newsTitleLiView.setAdapter(new NewsFragmentNewsTitleAdapter(getActivity(), 0, 0, newsList));
			}
		}.execute();
		
        newsTitleLiView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
				intent.putExtra("newsItem", newsList.get(position));
				startActivity(intent);
			}
		});
        
        return rootView;
    }
}
