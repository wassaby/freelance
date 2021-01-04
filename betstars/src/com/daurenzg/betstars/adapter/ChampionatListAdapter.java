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
import com.daurenzg.betstars.utils.BChampionatshipItem;

public class ChampionatListAdapter extends ArrayAdapter<BChampionatshipItem> {

	private List<BChampionatshipItem> data = null;
	private Context context = null;

	public ChampionatListAdapter(Context context, int resource,
			int textViewResourceId, List<BChampionatshipItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
	}

	@Override
	public int getCount() {
		return data!=null?data.size():0;
	}

	public void setData(List<BChampionatshipItem> data){
		this.data = data;
	}
	public List<BChampionatshipItem> getData(){
		return data;
	}
	@Override
	public BChampionatshipItem getItem(int position) {
		return data.get(position);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Formatter format = new Formatter();
		View result = inflater.inflate(R.layout.championat_list_item, null, true);
		BChampionatshipItem item = data.get(position);
		((TextView)result.findViewById(R.id.championatshipItemNumberTextView)).setText(new StringBuffer(String.valueOf(item.getId())).append(". ").toString());
		((TextView)result.findViewById(R.id.championatShipItemNameTextView)).setText(item.getName());
		return result;
	}
	
}
