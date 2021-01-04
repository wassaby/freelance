package com.daurenzg.betstars;

import java.text.ParseException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.daurenzg.betstars.adapter.ShortListAdapter;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BGamesItem;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class ShortListActivity extends Activity {

	private static final int REQUEST_CODE_SPORT_LIST = 1;
	private List<BGamesItem> eventList = null;
	public static ShortListAdapter adapter = null;
	private IAndroidUtils androidUtils = null;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_SPORT_LIST){
			if (resultCode == RESULT_OK){
				BGamesItem eventItem = (BGamesItem) data.getExtras().get("gameItem");
				eventList.add(eventItem);
				adapter.setData(eventList);
				adapter.notifyDataSetChanged();
				try {
					androidUtils.setEventItem(eventItem);
					adapter.setData(androidUtils.getEventListFromLocalDB());
					adapter.notifyDataSetChanged();
				} catch (BUtilsException e) {
					Toast.makeText(ShortListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				} catch (ParseException e) {
					Toast.makeText(ShortListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BTournamentItem tournamentItem = (BTournamentItem) getIntent().getExtras().get("tournamentItem");
		setContentView(R.layout.activity_short_list);
		ListView shortListListView = (ListView) findViewById(R.id.shortListListView);
		androidUtils = AndroidUtilsFactory.getInstanse(this).getAndroidUtils();
		try {
			eventList = androidUtils.getEventListFromLocalDB();
		} catch (ParseException e) {
			Toast.makeText(ShortListActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		adapter = new ShortListAdapter(this, 0, 0, eventList, tournamentItem);
		shortListListView.setAdapter(adapter);
		View footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.short_list_footer_item, null, false);
		shortListListView.addFooterView(footerView);
		
		footerView.findViewById(R.id.shortListFooterAddImageView).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ShortListActivity.this, SportListActivity.class);
				startActivityForResult(intent, REQUEST_CODE_SPORT_LIST);
			}
		});
		
		footerView.findViewById(R.id.shortListFooterAddTextView).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ShortListActivity.this, SportListActivity.class);
				startActivityForResult(intent, REQUEST_CODE_SPORT_LIST);
			}
		});
		
	}

}
