package com.daurenzg.betstars.receiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.daurenzg.betstars.adapter.ChatListAdapter;
import com.daurenzg.betstars.messages.MessagesActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BMessageItem;
import com.daurenzg.betstars.utils.BTournamentItem;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;

@SuppressLint("NewApi")
public class LoadMessagesReceiver extends BroadcastReceiver {

	static{
		System.setProperty("http.keepAlive", "false");
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context)
				.getAndroidUtils();
		final Context contextFinal = context;
		final BTournamentItem tournamentItem = (BTournamentItem) intent.getExtras().get("tournamentItem");
		new AsyncTask<Void, Void, Map<Long, List<BMessageItem>>>() {
			protected Map<Long, List<BMessageItem>> doInBackground(Void... params) {
				try {
					Map<Long, List<BMessageItem>> result = new HashMap<Long, List<BMessageItem>>();
					for (Long long1 : MessagesActivity.isLoadMessagesJobStarted.keySet()){
						List<BMessageItem> messagesList = androidUtils.getMessageList(tournamentItem.getId());
						List<BMessageItem> messageItemListFromLocalDb = androidUtils.getMessagesFromLocalDB(tournamentItem.getId());
						List<BMessageItem> tmpMsgList = new ArrayList<BMessageItem>();
						
						for (BMessageItem item : messagesList){
							boolean isInLocalDB = false;
							for (BMessageItem item2 : messageItemListFromLocalDb){
								if (item.getId() == item2.getId()){
									isInLocalDB = true;
									break;
								}
							}
							if (!isInLocalDB) tmpMsgList.add(item);
						}
						
						result.put(new Long(tournamentItem.getId()), tmpMsgList);
					}
					return result;
				} catch (Exception e) {
					return null;
				}
			}
			
			protected void onPostExecute(Map<Long, List<BMessageItem>> result) {
				
				for (Long long1 : result.keySet()){
					List<BMessageItem> messagesList = result.get(long1);
					for (BMessageItem item : messagesList){
						try {
							androidUtils.setProfileItemLocalDB(item.getProfileItem().getId(), item.getProfileItem().getName(), item.getProfileItem().getCityId(), item.getProfileItem().getCityId(), item.getProfileItem().getEmail(), item.getProfileItem().getPhotoUrl(), item.getProfileItem().isReady(), item.getProfileItem().isBlocked(), item.getProfileItem().getCoins());
							androidUtils.putMessageToLocalDB(item.getId(), item.getProfileItem().getId(), item.getMessage(), tournamentItem.getId(), new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(item.getCreatedAt()));
						} catch (BUtilsException e) {
							Toast.makeText(contextFinal, "Could not load new meessahrs", Toast.LENGTH_LONG).show();
						}
					}
				}
				
				if (MessagesActivity.tournamentItem!=null){
					try {
						List<BMessageItem> messageList = androidUtils.getMessagesFromLocalDB(MessagesActivity.tournamentItem.getId());
						if (MessagesActivity.chatListAdapter == null)
							MessagesActivity.chatListAdapter = new ChatListAdapter(contextFinal, 0, 0, messageList);
						else
							MessagesActivity.chatListAdapter.setData(messageList);
						MessagesActivity.chatListView.setAdapter(MessagesActivity.chatListAdapter);
						MessagesActivity.chatListAdapter.notifyDataSetChanged();
					} catch (BUtilsException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
		}.execute();
			
	}
}