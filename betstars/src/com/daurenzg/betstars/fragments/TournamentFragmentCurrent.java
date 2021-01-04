package com.daurenzg.betstars.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.ShortListActivity;
import com.daurenzg.betstars.adapter.TournamentsListAdapter;
import com.daurenzg.betstars.btn.FloatingActionButton;
import com.daurenzg.betstars.tournaments.CreateTournamentActivity;
import com.daurenzg.betstars.tournaments.ViewTournamentActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class TournamentFragmentCurrent extends Fragment {

	String[] items;
	ListView ls;

	public static List<BTournamentItem> currentTournamentList = new ArrayList<BTournamentItem>();
	public static TournamentsListAdapter currentTournamentListAdapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tournaments_current,
				container, false);

		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(getActivity()).getAndroidUtils();
        
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        getActivity().getActionBar().setCustomView((ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.ab_tournaments, null));
        
        final ListView tournamentslistView = (ListView) rootView.findViewById(R.id.tournamentslistView);
        tournamentslistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent =  new Intent(getActivity(), ViewTournamentActivity.class);
				BTournamentItem item = currentTournamentList.get(arg2);
				
				float state = item.getCurrentPlayers()/item.getMaxPlayers();
				if (state==1){
					intent.putExtra("tournamentItem", item);
					startActivity(intent);
				} else {
					Toast.makeText(getActivity(), R.string.notAllUsersJoinedMessage, Toast.LENGTH_LONG).show();
				}
			}
		});
        
        FloatingActionButton createTournamentBtn = (FloatingActionButton) rootView.findViewById(R.id.createTournamentBtn);
        createTournamentBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CreateTournamentActivity.class);
				startActivity(intent);
			}
		});
        new AsyncTask<Void, Void, List<BTournamentItem>>(){

        	ProgressDialog pd = new ProgressDialog(getActivity()); 
        	protected void onPreExecute() {
				String waitDlgTitle = getString(R.string.waitDlgTitle);
				String waitDlgMessage = getString(R.string.loadTournamentDlgMessage);
				pd.setTitle(waitDlgTitle);
				pd.setMessage(waitDlgMessage);
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
        	};
        	
        	protected List<BTournamentItem> doInBackground(Void... params) {
        		List<BTournamentItem> tournamentItems = new ArrayList<BTournamentItem>();
				try {
					List<Long> tournamentIdList = androidUtils.getCurrentTournamentIdList();
					for (Long long1 : tournamentIdList){
						BTournamentItem tournamentItem = androidUtils.getTournamentInfo(long1.longValue());
						tournamentItems.add(tournamentItem);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return tournamentItems;
			}
        	
        	protected void onPostExecute(List<BTournamentItem> result) {
        		if (currentTournamentListAdapter == null)
        			currentTournamentListAdapter = new TournamentsListAdapter(getActivity(), 0, 0, result, false);
        		else
        			currentTournamentListAdapter.setData(result);
        		currentTournamentList = result;
        		tournamentslistView.setAdapter(currentTournamentListAdapter);
        		currentTournamentListAdapter.notifyDataSetChanged();
        		pd.dismiss();
        	}
        	
        }.execute();
         
        return rootView;
		
	}

}
