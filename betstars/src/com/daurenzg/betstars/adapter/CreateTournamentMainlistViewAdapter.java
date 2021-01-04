package com.daurenzg.betstars.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.tournaments.CreateTournamentActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.ContactItem;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.ProfileItem;

public class CreateTournamentMainlistViewAdapter extends ArrayAdapter<String> {

	private List<String> data = null;
	private Context context = null;
	public static CreateTournamentUsrListAdapter createTournamentUsrListAdapter = null;
	public static List<ProfileItem> profileItems = null;
	public static ListView listView = null;
	public static View addUsrListView = null;
	public static View addUsrView = null;
	private AlertDialog alertDialog = null;
	private AlertDialog alertDialogInviteFriend = null;
	private List<ContactItem> selectedContactList = new ArrayList<ContactItem>();
	 
	
	public CreateTournamentMainlistViewAdapter(Context context, int resource,
			int textViewResourceId, List<String> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
	}
	
	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View result = null;
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		switch (position) {
		case 0:
			result = inflater.inflate(R.layout.create_tournament_players_count_item, null, true);
			
			OnClickListener radioListener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					RadioButton rb = (RadioButton)v;
					switch (rb.getId()) {
					case R.id.createTournamentRb9People: CreateTournamentActivity.playersCount = 9;
					    break;
					case R.id.createTournamentRb6People: CreateTournamentActivity.playersCount = 6;
						break;
					default:
						break;
					}
				}
			};
			
			result.findViewById(R.id.createTournamentRb9People).setOnClickListener(radioListener);
			result.findViewById(R.id.createTournamentRb6People).setOnClickListener(radioListener);

			
			break;
		case 1:
			result = inflater.inflate(R.layout.create_tournament_players_list_item, null, true);
				addUsrListView = inflater.inflate(R.layout.create_tournament_players_list_item_add_usr_list_item, null, true);
			addUsrView = inflater.inflate(R.layout.create_tournament_players_list_item_add_usr_btns_item, null, true);
				listView = (ListView) addUsrListView.findViewById(R.id.addUsrUsrlistView123);
			if (profileItems==null)
				profileItems = new ArrayList<ProfileItem>();
			if (createTournamentUsrListAdapter == null)
				createTournamentUsrListAdapter = new CreateTournamentUsrListAdapter(context, 0, 0, profileItems);
			listView.setAdapter(createTournamentUsrListAdapter);
			createTournamentUsrListAdapter.setData(profileItems);
			createTournamentUsrListAdapter.notifyDataSetChanged();
			androidUtils.setListViewHeightBasedOnChildren(listView);
			ImageView addUsrImageView = (ImageView) addUsrView.findViewById(R.id.tournamentProfileAddUsrImageView);
			TextView addUsrTextView = (TextView) addUsrView.findViewById(R.id.tournamentProfileAddUsrTextView);
			ImageView inviteUsrImageView = (ImageView) addUsrView.findViewById(R.id.tournamentProfileInviteUsrImageView);
			TextView inviteUsrTextView = (TextView) addUsrView.findViewById(R.id.tournamentProfileInviteUsrTextView);
			
			OnClickListener onInviteUsrClickListener = new OnClickListener() {
				public void onClick(View v) {
					 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					 LayoutInflater layoutInflater = LayoutInflater.from(context);
					 View promptView = layoutInflater.inflate(R.layout.activity_invite_friend, null);
					 ListView addUsrContactListView = (ListView) promptView.findViewById(R.id.addUsrContactListView);
					 List<ContactItem> contactList = androidUtils.getContactList();
					 InviteUserListAdapter inviteUserListAdapter = new InviteUserListAdapter(context, 0, 0, contactList, selectedContactList);
					 addUsrContactListView.setAdapter(inviteUserListAdapter);
					 alertDialogBuilder.setView(promptView);
					 alertDialogBuilder.setCancelable(true);
					 alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if (selectedContactList.size()==0){
								Toast.makeText(context, "Select contact", Toast.LENGTH_LONG).show();
							} else {
								for (ContactItem item : selectedContactList){
									androidUtils.sendSMS(item.getPhoneNumber(), "I am using betstars");
								}
							}
							selectedContactList.clear();
						}
					}).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});	
					 alertDialogInviteFriend = alertDialogBuilder.create();					
					 alertDialogInviteFriend.show();
				}
			};
			inviteUsrImageView.setOnClickListener(onInviteUsrClickListener);
			inviteUsrTextView.setOnClickListener(onInviteUsrClickListener);
			OnClickListener onAddUsrClickListener = new OnClickListener() {
				public void onClick(View v) {
					 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
					 LayoutInflater layoutInflater = LayoutInflater.from(context);
					 View promptView = layoutInflater.inflate(R.layout.activity_search_account, null);
					 final ListView searchResultListView = (ListView) promptView.findViewById(R.id.searchResultListView1);
					 final SearchAccountResultListAdapter adapter = new SearchAccountResultListAdapter(context, 0, 0, new ArrayList<ProfileItem>());
					 searchResultListView.setAdapter(adapter);
					 searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							ProfileItem profileItem = adapter.getItem(arg2);
							boolean alreadyAdded = false;
							for (ProfileItem item : profileItems){
								if (item.getId() == profileItem.getId()){
									alreadyAdded = true;
									break;
								}
							}
							if (alreadyAdded){
								Toast.makeText(context, R.string.searchAccountAlreadyAddedMessage, Toast.LENGTH_SHORT).show();
								return;
							}
							profileItems.add(profileItem);
							createTournamentUsrListAdapter.setData(profileItems);
							createTournamentUsrListAdapter.notifyDataSetChanged();
							androidUtils.setListViewHeightBasedOnChildren(listView);
							alertDialog.cancel();
						}
					});
					 
					 alertDialogBuilder.setView(promptView);
					 final EditText input = (EditText) promptView.findViewById(R.id.searchAccountEditText);
					 final ProgressBar bar = (ProgressBar) promptView.findViewById(R.id.searchAccountProgressBar);
					 final ImageView searchAccButton = (ImageView) promptView.findViewById(R.id.searchAccButton);
					 searchAccButton.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							new AsyncTask<Void, Void, List<ProfileItem>>() {
								
								protected void onPreExecute() {
									searchAccButton.setVisibility(View.INVISIBLE);
									bar.setVisibility(View.VISIBLE);
								};
								
								@Override
								protected List<ProfileItem> doInBackground(
										Void... params) {
									try {
										List<ProfileItem> items = androidUtils.searchProfile(input.getText().toString());
										return items;
									} catch (Exception e) {
										e.printStackTrace();
										return null;
									}
								}

								@Override
								protected void onPostExecute(
										List<ProfileItem> result) {
									adapter.setData(result);
									adapter.notifyDataSetChanged();
									searchAccButton.setVisibility(View.VISIBLE);
									bar.setVisibility(View.INVISIBLE);
								}
							}.execute();
						}
					});
					 alertDialogBuilder.setCancelable(true);
					 alertDialog = alertDialogBuilder.create();					
					 alertDialog.show();
				}
			};
			addUsrImageView.setOnClickListener(onAddUsrClickListener);
			addUsrTextView.setOnClickListener(onAddUsrClickListener);
			((LinearLayout) result).addView(addUsrListView);
			((LinearLayout) result).addView(addUsrView);
			break;
		case 2:
			result = inflater.inflate(R.layout.create_tournament_days_count_item, null, true);
			String[] days = new String[]{"3 round", "7 rounds", "10 rounds"};
			ArrayAdapter<String> daysCountSpinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, days);
			Spinner roundCountSpinner = (Spinner) result.findViewById(R.id.createTournamentDaysCountSpinner);
			roundCountSpinner.setAdapter(daysCountSpinnerAdapter);
			roundCountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						CreateTournamentActivity.roundCount = 3;
						break;
					case 1:
						CreateTournamentActivity.roundCount = 7;
						break;
					case 2:
						CreateTournamentActivity.roundCount = 10;
						break;
					default:
						break;
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			break;
		case 3:
			result = inflater.inflate(R.layout.create_tournament_un_item, null, true);
			String[] unCount = new String[]{"100", "500", "1000"};
			ArrayAdapter<String> unCountSpinnerAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, unCount);
			Spinner entranceFeeSpinner = (Spinner) result.findViewById(R.id.createTournamentUnCountSpinner);
			entranceFeeSpinner.setAdapter(unCountSpinnerAdapter);
			entranceFeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					switch (arg2) {
					case 0:
						CreateTournamentActivity.entranceFee = 100;
						break;
					case 1:
						CreateTournamentActivity.entranceFee = 500;
						break;
					case 2:
						CreateTournamentActivity.entranceFee = 1000;
						break;
					default:
						break;
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			break;
		default:
			break;
		}
		return result;
	}
	
}
