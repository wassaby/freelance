package com.daurenzg.betstars.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.ContactItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class InviteUserListAdapter extends ArrayAdapter<ContactItem> {

	private List<ContactItem> data = null;
	private List<ContactItem> selectedContactList = null;
	private Context context = null;
	public static CreateTournamentUsrListAdapter createTournamentUsrListAdapter = null;
	
	 
	
	public InviteUserListAdapter(Context context, int resource,
			int textViewResourceId, List<ContactItem> objects, List<ContactItem> selectedContactList) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
		this.selectedContactList = selectedContactList;
	}
	
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View result = null;
		result = inflater.inflate(R.layout.activity_invite_friend_item, null, true);
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		TextView inviteUserNameTextView = (TextView) result.findViewById(R.id.inviteUserNameTextView);
		TextView inviteUserPhoneNumberTextView = (TextView) result.findViewById(R.id.inviteUserPhoneNumberTextView);
		final ContactItem contactItem = data.get(position);
		CheckBox checkBox = (CheckBox) result.findViewById(R.id.inviteUserCheckBox);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1){
					selectedContactList.add(contactItem);
				} else {
					int idx = 0;
					for (int i=0; i<selectedContactList.size();i++){
						if (selectedContactList.get(i).getId().equals(contactItem.getId())){
							idx = i;
							break;
						}
					}
					selectedContactList.remove(idx);
				}
			}
			
		});
		inviteUserNameTextView.setText(contactItem.getName());
		inviteUserPhoneNumberTextView.setText(contactItem.getPhoneNumber());
		return result;
	}
	
}
