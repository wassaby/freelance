package com.daurenzg.betstars.adapter;

import java.text.ParseException;
import java.util.Formatter;
import java.util.List;

import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.ShortListActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BBetItem;
import com.daurenzg.betstars.utils.BGamesItem;
import com.daurenzg.betstars.utils.BOptionItem;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class ShortListAdapter extends ArrayAdapter<BGamesItem> {

	private List<BGamesItem> data = null;
	private Context context = null;
	private BTournamentItem tournamentItem = null;

	private String optionsKey = null;
	
	private String[] optionKeys;
	
	private String[] optionTitles;
	
	public ShortListAdapter(Context context, int resource,
			int textViewResourceId, List<BGamesItem> objects, BTournamentItem tournamentItem) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
		this.tournamentItem = tournamentItem;
	}

	@Override
	public int getCount() {
		return data!=null?data.size():0;
	}

	public void setData(List<BGamesItem> data){
		this.data = data;
	}
	public List<BGamesItem> getData(){
		return data;
	}
	@Override
	public BGamesItem getItem(int position) {
		return data.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Formatter format = new Formatter();
		View result = inflater.inflate(R.layout.short_list_item, null, true);
		final BGamesItem item = data.get(position);
		((TextView)result.findViewById(R.id.eventNumberTextView)).setText(new StringBuffer(String.valueOf(item.getId())).append(". ").toString());
		((TextView)result.findViewById(R.id.eventNameTextView)).setText(item.getName());
		TextView eventBetTextView = (TextView) result.findViewById(R.id.eventBetTextView);
		TextView delEventImageView = (TextView) result.findViewById(R.id.eventDeleteTextView);
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		final int location = position;
		final String[] betKeys = new String[item.getBetList().size()];
		optionKeys = new String[item.getBetList().get(0).getOptionsList().size()];
		optionTitles = new String[item.getBetList().get(0).getOptionsList().size()];
		final Spinner shortListOptionsSpinner = (Spinner) result.findViewById(R.id.shortListOptionsSpinner1);
		int i=0;
		for (BBetItem betItem : item.getBetList()){
			betKeys[i++] = betItem.getKey();
		}
		int j=0;
		for (BOptionItem optionItem : item.getBetList().get(0).getOptionsList()){
			optionKeys[j] = optionItem.getKey();
			optionTitles[j] = new StringBuffer(optionItem.getTitle()).append(" (").append(optionItem.getRate()).append(")").toString() ;
			j++;
		}
		optionsKey = optionKeys[0];
        ArrayAdapter<String> shortListBetSpinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, betKeys);
        ArrayAdapter<String> shortListOptionsSpinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, optionTitles);
		Spinner shortListBetSpinner = (Spinner) result.findViewById(R.id.shortListBetSpinner1);
		
		shortListBetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				BBetItem bBetItem = item.getBetList().get(arg2);
				optionKeys = new String[bBetItem.getOptionsList().size()];
				optionTitles = new String[bBetItem.getOptionsList().size()];
				int x=0;
				for (BOptionItem optionItem : bBetItem.getOptionsList()){
					optionKeys[x] = optionItem.getKey();
					optionTitles[x] = new StringBuffer(optionItem.getTitle()).append(" (").append(optionItem.getRate()).append(")").toString() ;
					x++;
				}
				ArrayAdapter<String> shortListOptionsSpinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, optionTitles);
				shortListOptionsSpinner.setAdapter(shortListOptionsSpinnerAdapter);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}}
		);
		shortListBetSpinner.setAdapter(shortListBetSpinnerAdapter);
		
		
		shortListOptionsSpinner.setAdapter(shortListOptionsSpinnerAdapter);
		
		shortListOptionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				optionsKey = optionKeys[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}}
		);
		
		eventBetTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					
					new AsyncTask<Void, Void, String>() {
						ProgressDialog pd = new ProgressDialog(context);
						protected void onPreExecute() {
							String waitDlgTitle = context.getString(R.string.waitDlgTitle);
							String waitDlgMessage = context.getString(R.string.waitDlgMessage);
							pd.setTitle(waitDlgTitle);
							pd.setMessage(waitDlgMessage);
							pd.setCancelable(false);
							pd.setIndeterminate(true);
							pd.show();
						}
						
						protected String doInBackground(Void... params) {
							try {
								return androidUtils.makeBet(tournamentItem.getId(), item.getId(), optionsKey);
							} catch (Exception e) {
								return null;
							}
						}
						
						protected void onPostExecute(String result) {
							pd.dismiss();
							try {
								ShortListActivity.adapter.setData(androidUtils.getEventListFromLocalDB());
							} catch (ParseException e) {
								Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
							}
							ShortListActivity.adapter.notifyDataSetChanged();
							if (result!=null&&result.length()!=0)
								Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
						}
					}.execute();
					
				
			}
		});
		
		delEventImageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					androidUtils.deleteEventItem(item.getId());
					ShortListActivity.adapter.setData(androidUtils.getEventListFromLocalDB());
					ShortListActivity.adapter.notifyDataSetChanged();
				} catch (BUtilsException e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
				} catch (ParseException e) {
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		return result;
	}
	
}
