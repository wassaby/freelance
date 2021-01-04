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

import com.daurenzg.betstars.adapter.ChampionatListAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BChampionatshipItem;
import com.daurenzg.betstars.utils.BGamesItem;
import com.daurenzg.betstars.utils.BSportItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class ChampionanshipActivity extends Activity {

	private List<BChampionatshipItem> championatshipItems = null;
	
	private static final int GAME_LIST_ACTIVITY_REQUEST_CODE = 4;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == GAME_LIST_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
			BGamesItem gameItem = (BGamesItem) data.getExtras().get("gameItem");
			Intent intent = new Intent();
			intent.putExtra("gameItem", gameItem);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_championanship);
		final ListView championatShipListView = (ListView) findViewById(R.id.championatShipListView);
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(ChampionanshipActivity.this).getAndroidUtils();
		final BSportItem sportItem = (BSportItem) getIntent().getExtras().get("sportItem");
		championatShipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BChampionatshipItem bChampionatshipItem = (BChampionatshipItem) championatshipItems.get(arg2);
				Intent intent = new Intent(ChampionanshipActivity.this, GamesListActivity.class);
				intent.putExtra("championatShipItem", bChampionatshipItem);
				intent.putExtra("sportItem", sportItem);
				startActivityForResult(intent, GAME_LIST_ACTIVITY_REQUEST_CODE);
			}
		});
		new AsyncTask<Void, Void, List<BChampionatshipItem>>(){

        	ProgressDialog pd = new ProgressDialog(ChampionanshipActivity.this); 
        	protected void onPreExecute() {
				String waitDlgTitle = getString(R.string.waitDlgTitle);
				String waitDlgMessage = getString(R.string.loadChampionatShipListDlgMessage);
				pd.setTitle(waitDlgTitle);
				pd.setMessage(waitDlgMessage);
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
        	};
        	
        	protected List<BChampionatshipItem> doInBackground(Void... params) {
        		List<BChampionatshipItem> sportList = new ArrayList<BChampionatshipItem>();
				try {
					sportList = androidUtils.getChampionatshipList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return sportList;
			}
        	
        	protected void onPostExecute(List<BChampionatshipItem> result) {
        		championatshipItems = result;
        		ChampionatListAdapter adapter = new ChampionatListAdapter(ChampionanshipActivity.this, 0, 0, championatshipItems);
        		championatShipListView.setAdapter(adapter);
        		pd.dismiss();
        	}
        	
        }.execute();

		
	}

}
