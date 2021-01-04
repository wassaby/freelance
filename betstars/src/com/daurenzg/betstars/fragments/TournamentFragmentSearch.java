package com.daurenzg.betstars.fragments;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.adapter.TournamentsListAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class TournamentFragmentSearch extends Fragment{
	
	private String playersCount = "6";
	private String roundCount = "3";
	private String entanceFee = "100";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tournaments_search, container, false);
        
        String[] anteValues = new String[]{"100", "500", "1000"};
        ArrayAdapter<String> anteValuesSpinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, anteValues);
		Spinner searchTournamentAnteSpinner = (Spinner) rootView.findViewById(R.id.searchTournamentAnteSpinner);
		searchTournamentAnteSpinner.setAdapter(anteValuesSpinnerAdapter);
		searchTournamentAnteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					entanceFee = "100";
					break;
				case 1:
					entanceFee = "500";
					break;
				case 2:
					entanceFee = "1000";
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});


        String[] termOfTournaments = new String[]{"3 days", "7 days", "10 days"};
		ArrayAdapter<String> termOfTournamentsSpinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, termOfTournaments);
		Spinner searchTournamentTermSpinner = (Spinner) rootView.findViewById(R.id.searchTournamentTermSpinner);
		searchTournamentTermSpinner.setAdapter(termOfTournamentsSpinnerAdapter);
		searchTournamentTermSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					roundCount = "3";
					break;
				case 1:
					roundCount = "7";
					break;
				case 2:
					roundCount = "10";
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}});

        String[] players = new String[]{"6 players", "9 players"};
		ArrayAdapter<String> daysCountSpinnerAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, players);
		Spinner searchTournamentPlayersCountSpinner = (Spinner) rootView.findViewById(R.id.searchTournamentPlayersCountSpinner);
		searchTournamentPlayersCountSpinner.setAdapter(daysCountSpinnerAdapter);
		searchTournamentPlayersCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				switch (arg2) {
				case 0:
					playersCount = "6";
					break;
				case 1:
					playersCount = "9";
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final ListView searchTournamentSearchResultListView = (ListView) rootView.findViewById(R.id.searchTournamentSearchResultListView);
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(getActivity()).getAndroidUtils();
		ImageButton searchTournamentSearchImageButton = (ImageButton) rootView.findViewById(R.id.searchTournamentSearchImageButton);
		searchTournamentSearchImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				new AsyncTask<Void, Void, List<BTournamentItem>>() {
					ProgressDialog pd = new ProgressDialog(getActivity());
					protected void onPreExecute() {
						String waitDlgTitle = getString(R.string.waitDlgTitle);
						String waitDlgMessage = getString(R.string.waitDlgMessage);
						pd.setTitle(waitDlgTitle);
						pd.setMessage(waitDlgMessage);
						pd.setCancelable(false);
						pd.setIndeterminate(true);
						pd.show();
					}
					
					protected List<BTournamentItem> doInBackground(Void... params) {
						try {
							List<BTournamentItem> tournamentList = androidUtils.getTournamentList(playersCount, roundCount, entanceFee);
//							List<BTournamentItem> tournamentList = androidUtils.getTournamentList(null, null, null);
							return tournamentList;
						} catch (Exception e) {
							return null;
						}
					}
					
					protected void onPostExecute(List<BTournamentItem> result) {
						pd.dismiss();
						TournamentsListAdapter adapter = new TournamentsListAdapter(getActivity(), 0, 0, result, true);
						searchTournamentSearchResultListView.setAdapter(adapter);
					}
				}.execute();
			}
		});
        return rootView;
    }
	
}
