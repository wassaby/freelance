package com.daurenzg.betstars.messages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.adapter.ChatListAdapter;
import com.daurenzg.betstars.adapter.TournamentsListAdapter;
import com.daurenzg.betstars.receiver.LoadMessagesReceiver;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BMessageItem;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class MessagesActivity extends Activity {
	
	public MessagesActivity(){}
	
	public static ChatListAdapter chatListAdapter = null;
	public static ListView chatListView = null;
	public static final Map<Long, Boolean> isLoadMessagesJobStarted = new HashMap<Long, Boolean>();
	public static boolean isLoadMsgJobStarted = false;
	public static BTournamentItem tournamentItem = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_messages);
        final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(MessagesActivity.this).getAndroidUtils();
        final BTournamentItem tournamentItem = (BTournamentItem) getIntent().getExtras().get("tournamentItem");
        
        findViewById(R.id.createTournamentMessageBtn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MessagesActivity.this);
				builder.setTitle("Create new message");

				// Set up the input
				final EditText input = new EditText(MessagesActivity.this);
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
				input.setInputType(InputType.TYPE_CLASS_TEXT);
				builder.setView(input);

				// Set up the buttons
				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				    	
				    	
				    	new AsyncTask<Void, Void, List<BMessageItem>>(){

				        	ProgressDialog pd = new ProgressDialog(MessagesActivity.this); 
				        	protected void onPreExecute() {
								String waitDlgTitle = getString(R.string.waitDlgTitle);
								String waitDlgMessage = "Sending message";
								pd.setTitle(waitDlgTitle);
								pd.setMessage(waitDlgMessage);
								pd.setCancelable(false);
								pd.setIndeterminate(true);
								pd.show();
				        	};
				        	
				        	protected List<BMessageItem> doInBackground(Void... params) {
								try {
									IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(MessagesActivity.this).getAndroidUtils();
									androidUtils.createMessage(tournamentItem.getId(), input.getText().toString());
									List<BMessageItem> result = androidUtils.getMessageList(tournamentItem.getId());
									return result;
								} catch (Exception e) {
									e.printStackTrace();
								}
								return null;
							}
				        	
				        	protected void onPostExecute(List<BMessageItem> result) {
				        		pd.dismiss();
				        		if (result!=null&&result.size()!=0){
				        			for (int i=0;i<result.size()-1;i++){
				        				result.get(i).setRead(true);
				        			}
				        			chatListAdapter.setData(result);
				        			chatListAdapter.notifyDataSetChanged();
				        		}
				        	}
				        	
				        }.execute();
				    	
				    	
				    }
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        dialog.cancel();
				    }
				});

				builder.show();
			}
		});
        
        MessagesActivity.tournamentItem = tournamentItem;
        try {
            	chatListView = (ListView) findViewById(R.id.chatlistView);
            
            new AsyncTask<Void, Void, List<BMessageItem>>() {
				ProgressDialog pd = new ProgressDialog(MessagesActivity.this);
				protected void onPreExecute() {
					String waitDlgTitle = MessagesActivity.this.getString(R.string.waitDlgTitle);
					String waitDlgMessage = MessagesActivity.this.getString(R.string.waitDlgMessage);
					pd.setTitle(waitDlgTitle);
					pd.setMessage(waitDlgMessage);
					pd.setCancelable(false);
					pd.setIndeterminate(true);
					pd.show();
				}
				
				protected List<BMessageItem> doInBackground(Void... params) {
					try {
						return androidUtils.getMessagesFromLocalDB(tournamentItem.getId());
					} catch (Exception e) {
						return null;
					}
				}
				
				protected void onPostExecute(List<BMessageItem> result) {
					pd.dismiss();
					if (result == null)
						Toast.makeText(MessagesActivity.this, "Error loading messages", Toast.LENGTH_SHORT).show();
					else {
						if (chatListAdapter == null)
							chatListAdapter = new ChatListAdapter(MessagesActivity.this, 0, 0, result);
						else
							chatListAdapter.setData(result);
						chatListView.setAdapter(chatListAdapter);
						chatListAdapter.notifyDataSetChanged();
					}
				}
			}.execute();
			if (!isLoadMsgJobStarted){
				AlarmManager alarmManager = (AlarmManager) MessagesActivity.this.getSystemService(Context.ALARM_SERVICE);
				Intent loadMessages = new Intent(MessagesActivity.this, LoadMessagesReceiver.class);
				loadMessages.putExtra("tournamentItem", tournamentItem);
				PendingIntent responseQueueIntent = PendingIntent.getBroadcast(MessagesActivity.this, 0, loadMessages, 0);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 1000 * 60, responseQueueIntent);
				isLoadMsgJobStarted = true;
			}
			isLoadMessagesJobStarted.put(new Long(tournamentItem.getId()), true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
