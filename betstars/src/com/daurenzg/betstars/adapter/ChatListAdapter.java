package com.daurenzg.betstars.adapter;

import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.task.DownloadImagesTask;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BMessageItem;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;

public class ChatListAdapter extends ArrayAdapter<BMessageItem> {

	List<BMessageItem> data = null;
	private Context context = null;
	
	public List<BMessageItem> getData() {
		return data;
	}

	public void setData(List<BMessageItem> data) {
		this.data = data;
	}

	public ChatListAdapter(Context context, int resource,
			int textViewResourceId, List<BMessageItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
//		IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		data = objects;
//		Collections.sort(data, new Comparator<BMessageItem>() {
//
//			public int compare(BMessageItem lhs, BMessageItem rhs) {
//				if (lhs.getUserId()>rhs.getUserId())
//					return -1;
//				else
//					return 1;
//			}
//		});
//		
	}

	public BMessageItem getItem(int position) {
		return data.get(position);
	}

	public int getCount() {
		return data==null?0:data.size();
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(context).getAndroidUtils();
		View result = null;
		result = inflater.inflate(R.layout.chat_list_item, null, true);
		BMessageItem messageItem = data.get(position);
		ImageView chatListLogoImageView = (ImageView) result.findViewById(R.id.chatListLogoImageView);
		chatListLogoImageView.setTag(messageItem.getProfileItem().getPhotoUrl());
		new DownloadImagesTask(chatListLogoImageView, (ProgressBar) result.findViewById(R.id.chatListProgressBar)).execute();
		ImageView icNewImageView = (ImageView) result.findViewById(R.id.icNewImageView);
		if (!messageItem.isRead())
			icNewImageView.setVisibility(View.VISIBLE);
		TextView lastMsgTextView = (TextView) result.findViewById(R.id.lastMsgTextView);
		lastMsgTextView.setText(messageItem.getMessage());
		
		TextView msgDateTextView1 = (TextView) result.findViewById(R.id.msgDateTextView1);
		SimpleDateFormat sdf = new SimpleDateFormat(IAndroidUtils.OUTPU_DATE_FORMAT_PATTERN);
		msgDateTextView1.setText(sdf.format(messageItem.getCreatedAt()));
		
		((TextView)result.findViewById(R.id.chatContactNameTextView)).setText(messageItem.getProfileItem().getName());
		((TextView)result.findViewById(R.id.chatContactPhoneNumerTextView)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", messageItem.getProfileItem().getId()).toString()));
		
		try {
			androidUtils.markMessageAsread(messageItem.getId());
		} catch (BUtilsException e){
			Toast.makeText(context, "Could not mark message as read, \""+e.getMessage()+"\"", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(context, "Could not mark message as read, \""+e.getMessage()+"\"", Toast.LENGTH_LONG).show();
		}
		
		return result;
	}
	
}
