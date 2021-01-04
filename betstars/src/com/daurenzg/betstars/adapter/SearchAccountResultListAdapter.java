package com.daurenzg.betstars.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.wao.ProfileItem;

public class SearchAccountResultListAdapter extends ArrayAdapter<ProfileItem> {

	private List<ProfileItem> data = null;
	private Context context = null;
	
	public SearchAccountResultListAdapter(Context context, int resource,
			int textViewResourceId, List<ProfileItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
	}

	@Override
	public int getCount() {
		return data!=null&&data.size()==0?1:data.size();
	}

	public void setData(List<ProfileItem> data){
		this.data = data;
	}
	@Override
	public ProfileItem getItem(int position) {
		return data.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View result = null;
		if (data.size()==0){
			result = inflater.inflate(R.layout.no_profile_found_item, null, true);
		} else {
			result = inflater.inflate(R.layout.create_tournament_players_list_item_list_item, null, true);
			TextView tournamentProfileNameTextView = (TextView) result.findViewById(R.id.tournamentProfileNameTextView);
			TextView tournamentProfileIdTextView = (TextView) result.findViewById(R.id.tournamentProfileIdTextView);
			ProfileItem profileItem = data.get(position);
			tournamentProfileNameTextView.setText(profileItem.getName());
			tournamentProfileIdTextView.setText(new StringBuffer().append("¹").append(String.valueOf(profileItem.getId())));
		}
		return result;
	}
	
}
