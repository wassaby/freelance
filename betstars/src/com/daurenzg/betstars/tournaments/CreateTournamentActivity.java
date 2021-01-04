package com.daurenzg.betstars.tournaments;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.adapter.CreateTournamentMainlistViewAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.ProfileItem;

public class CreateTournamentActivity extends Activity {
	
	public static int playersCount = 9;
	public static int entranceFee = 100;
	public static int roundCount = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_tournament_activity);
		
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_create_tournament, null);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		
		ImageView done = (ImageView) findViewById(R.id.iv_create_tournament_done_in);
		
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(CreateTournamentActivity.this)
				.getAndroidUtils();
		
		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTask<Void, Void, Integer>() {
					@Override
					protected Integer doInBackground(Void... params) {
						try {
//							CreateTournamentMainlistViewAdapter.profileItems
							String tournamentId = androidUtils.createTournament(playersCount, roundCount, entranceFee);
							for (ProfileItem item : CreateTournamentMainlistViewAdapter.profileItems){
								androidUtils.inviteUser(tournamentId, String.valueOf(item.getId()));
							}
							return 0;
						} catch (BUtilsException e) {
							e.printStackTrace();
							return 1;
						} catch (JSONException e) {
							e.printStackTrace();
							return 1;
						}
					}

					@Override
					protected void onPostExecute(Integer result) {
						if (result==0)
							Toast.makeText(CreateTournamentActivity.this, "Tournament created successfully", Toast.LENGTH_SHORT).show();
						else
							Toast.makeText(CreateTournamentActivity.this, "Error creating tournament", Toast.LENGTH_SHORT).show();
						CreateTournamentActivity.this.finish();
					}
				}.execute();
			}
		});
		
		ListView createTournamentMainlistView = (ListView) findViewById(R.id.createTournamentMainlistView);
		createTournamentMainlistView.setAdapter(new CreateTournamentMainlistViewAdapter(this, 0, 0, null));
		
	}

}
