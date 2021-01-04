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

import com.daurenzg.betstars.adapter.SportListAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BGamesItem;
import com.daurenzg.betstars.utils.BSportItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class SportListActivity extends Activity {

	private List<BSportItem> sportList = null;
	private static final int CHAMPIONATSHIP_LIST_ACTIVITY_REQUEST_CODE = 2;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==CHAMPIONATSHIP_LIST_ACTIVITY_REQUEST_CODE&&resultCode == RESULT_OK){
			BGamesItem gamesItem = (BGamesItem) data.getExtras().get("gameItem");
			Intent intent = new Intent();
			intent.putExtra("gameItem", gamesItem);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport_list);
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(SportListActivity.this).getAndroidUtils();
		final ListView listView = (ListView) findViewById(R.id.sportListView);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BSportItem sportItem = (BSportItem) sportList.get(arg2);
				Intent intent = new Intent(SportListActivity.this, ChampionanshipActivity.class);
				intent.putExtra("sportItem", sportItem);
				startActivityForResult(intent, CHAMPIONATSHIP_LIST_ACTIVITY_REQUEST_CODE);
			}
		});
		new AsyncTask<Void, Void, List<BSportItem>>(){

        	ProgressDialog pd = new ProgressDialog(SportListActivity.this); 
        	protected void onPreExecute() {
				String waitDlgTitle = getString(R.string.waitDlgTitle);
				String waitDlgMessage = getString(R.string.loadSportDlgMessage);
				pd.setTitle(waitDlgTitle);
				pd.setMessage(waitDlgMessage);
				pd.setCancelable(false);
				pd.setIndeterminate(true);
				pd.show();
        	};
        	
        	protected List<BSportItem> doInBackground(Void... params) {
        		List<BSportItem> sportList = new ArrayList<BSportItem>();
				try {
					sportList = androidUtils.getSportItemList();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return sportList;
			}
        	
        	protected void onPostExecute(List<BSportItem> result) {
        		sportList = result;
        		SportListAdapter adapter = new SportListAdapter(SportListActivity.this, 0, 0, sportList);
        		listView.setAdapter(adapter);
        		pd.dismiss();
        	}
        	
        }.execute();
		
		
	}

}
