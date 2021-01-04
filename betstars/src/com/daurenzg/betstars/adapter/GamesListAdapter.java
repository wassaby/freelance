package com.daurenzg.betstars.adapter;

import java.util.Formatter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.BGamesItem;

public class GamesListAdapter extends ArrayAdapter<BGamesItem> {

	private List<BGamesItem> data = null;
	private Context context = null;

	public GamesListAdapter(Context context, int resource,
			int textViewResourceId, List<BGamesItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
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
		View result = inflater.inflate(R.layout.games_list_item, null, true);
		BGamesItem item = data.get(position);
		((TextView)result.findViewById(R.id.gamesListNumberTextView)).setText(new StringBuffer(String.valueOf(item.getId())).append(". ").toString());
		((TextView)result.findViewById(R.id.gamesListNameTextView)).setText(item.getName());
		return result;
	}
	
}
