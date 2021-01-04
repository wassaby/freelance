package com.daurenzg.betstars;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daurenzg.betstars.adapter.GamesListAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BChampionatshipItem;
import com.daurenzg.betstars.utils.BGamesItem;
import com.daurenzg.betstars.utils.BSportItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class GamesListActivity extends Activity {

	private List<BGamesItem> gamesList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games_list);
		final BChampionatshipItem bChampionatshipItem = (BChampionatshipItem) getIntent().getExtras().get("championatShipItem");
		final BSportItem sportItem = (BSportItem) getIntent().getExtras().get("sportItem");
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(GamesListActivity.this).getAndroidUtils();
		
		final ListView gamesListMainListView = (ListView) findViewById(R.id.gamesListMainListView);

		gamesListMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BGamesItem gameItem = (BGamesItem) gamesList.get(arg2);
				Intent intent = new Intent();
				intent.putExtra("gameItem", gameItem);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		new AsyncTask<Void, Void, List<BGamesItem>>(){

        	ProgressDialog pd = new ProgressDialog(GamesListActivity.this); 
        	
        	protected void onPreExecute() {
				String waitDlgTitle = getString(R.string.waitDlgTitle);
				String waitDlgMessage = getString(R.string.loadGamesDlgMessage);
				pd.setTitle(waitDlgTitle);
				pd.setMessage(waitDlgMessage);
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
        	};
        	
        	protected List<BGamesItem> doInBackground(Void... params) {
        		List<BGamesItem> sportList = new ArrayList<BGamesItem>();
				try {
					sportList = androidUtils.getGamesList(sportItem.getId(), bChampionatshipItem.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				return sportList;
			}
        	
        	protected void onPostExecute(List<BGamesItem> result) {
        		gamesList = result;
        		GamesListAdapter adapter = new GamesListAdapter(GamesListActivity.this, 0, 0, gamesList);
        		gamesListMainListView.setAdapter(adapter);
        		pd.dismiss();
        	}
        	
        }.execute();
		
	}

}
