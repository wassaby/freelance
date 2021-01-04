package com.daurenzg.betstars.adapter;

import java.util.Formatter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.ProfileItem;

public class CreateTournamentUsrListAdapter extends ArrayAdapter<ProfileItem> {

	private List<ProfileItem> data = null;
	private Context context = null;

	public CreateTournamentUsrListAdapter(Context context, int resource,
			int textViewResourceId, List<ProfileItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
	}

	@Override
	public int getCount() {
		return data!=null?data.size():0;
	}

	public void setData(List<ProfileItem> data){
		this.data = data;
	}
	public List<ProfileItem> getData(){
		return data;
	}
	@Override
	public ProfileItem getItem(int position) {
		return data.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Formatter format = new Formatter();
		View result = inflater.inflate(R.layout.create_tournament_players_list_item_list_item, null, true);
		ProfileItem item = data.get(position);
		((TextView)result.findViewById(R.id.tournamentProfileNameTextView)).setText(item.getName());
		((TextView)result.findViewById(R.id.tournamentProfileIdTextView)).setText(new StringBuffer("¹").append(format.format("%06d%n", item.getId()).toString()));
		ImageView delUsrImageView = (ImageView) result.findViewById(R.id.delUsrImageView);
		delUsrImageView.setVisibility(View.VISIBLE);
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		final int location = position;
		delUsrImageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CreateTournamentMainlistViewAdapter.profileItems.remove(location);
				CreateTournamentMainlistViewAdapter.createTournamentUsrListAdapter.setData(CreateTournamentMainlistViewAdapter.profileItems);
				CreateTournamentMainlistViewAdapter.createTournamentUsrListAdapter.notifyDataSetChanged();
				androidUtils.setListViewHeightBasedOnChildren(CreateTournamentMainlistViewAdapter.listView);
			}
		});
		return result;
	}
	
}
