package com.daurenzg.betstars.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.utils.BNewsItem;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class NewsDetailParagraphListAdapter extends ArrayAdapter<String> {

	private List<String> data = null;
	private BNewsItem newsItem = null;
	private Context context = null;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	
	public NewsDetailParagraphListAdapter(Context context, int resource,
			int textViewResourceId, List<String> objects, BNewsItem newsItem) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.data = objects;
		this.newsItem = newsItem;
	}

	@Override
	public int getCount() {
		return data != null ? data.size()+1 : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View result = null;
		if (position == 0){
			result = inflater.inflate(R.layout.news_title_list_item, null, true);
			ImageView newsMainimageView = (ImageView) result.findViewById(R.id.newsTitleListItemMainImageView);
			TextView newsTitleListItemNewsTitleextView = (TextView) result.findViewById(R.id.newsTitleListItemNewsTitleextView);
			TextView newsTitleListItemRubricNameTextView = (TextView) result.findViewById(R.id.newsTitleListItemRubricNameTextView);
			
			newsTitleListItemNewsTitleextView.setText(newsItem.getNewsTitle());
			newsTitleListItemRubricNameTextView.setText(newsItem.getRubricName());
			
			ImageLoader.getInstance().displayImage(
					new StringBuffer().append(IAndroidUtils.BETSARS_SERVER_URL_ROOT)
							.append(newsItem.getPhotoUrl())
							.toString(), newsMainimageView, options,
					animateFirstListener);
		} else {
			result = inflater.inflate(R.layout.news_detail_list_item, null, true);
			TextView newsDetailItemTextView = (TextView) result.findViewById(R.id.newsDetailItemTextView);
			newsDetailItemTextView.setText(data.get(position-1));
		}
		return result;
	}
	
}
