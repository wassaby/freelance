package com.daurenzg.betstars.adapter;

import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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

public class NewsFragmentNewsTitleAdapter extends ArrayAdapter<BNewsItem> {

	private List<BNewsItem> data = null;
	private Context context = null;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private LayoutInflater inflater;
	
	public NewsFragmentNewsTitleAdapter(Context context, int resource,
			int textViewResourceId, List<BNewsItem> objects) {
		super(context, resource, textViewResourceId, objects);
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.data = objects;
		this.options = new DisplayImageOptions.Builder()
			/*.showImageOnLoading(R.drawable.ic_stub)
			.showImageForEmptyUri(R.drawable.ic_empty)
			.showImageOnFail(R.drawable.ic_error)*/.cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
		/* .displayer(new RoundedBitmapDisplayer(20)) */.build();
	}

	@Override
	public int getCount() {
		return data != null ? data.size() : 0;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;
		
		if (convertView == null) {
			/*LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
			view = inflater.inflate(R.layout.news_title_list_item, null, true);
			viewHolder = new ViewHolder();
			viewHolder.pic = (ImageView) view.findViewById(R.id.newsTitleListItemMainImageView);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.newsTitleListItemNewsTitleextView);
			viewHolder.tvTitle.setHorizontalFadingEdgeEnabled(true);
			viewHolder.tvTitle.setHorizontallyScrolling(true);
			//newsTitleListItemNewsTitleextView.setMovementMethod(new ScrollingMovementMethod());
			viewHolder.tvRubrick = (TextView) view.findViewById(R.id.newsTitleListItemRubricNameTextView);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		viewHolder.tvTitle.setText(data.get(position).getNewsTitle());
		viewHolder.tvRubrick.setText(data.get(position).getRubricName());
		
//		relativeLayout.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
		ImageLoader.getInstance().displayImage(
				new StringBuffer().append(IAndroidUtils.BETSARS_SERVER_URL_ROOT)
						.append(data.get(position).getPhotoUrl())
						.toString(), viewHolder.pic, options,
				animateFirstListener);
		
		//newsMainimageView.setImageResource(data.get(position).getMainPicture());
		return view;
	}
	
	private static class ViewHolder {
		TextView tvTitle;
		TextView tvRubrick;
		ImageView pic;

	}
}
