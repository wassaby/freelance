package com.daurenzg.betstars.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.supportutils.CircleImageView;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.RatingItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class RatingListViewAdapter extends BaseAdapter {

	// private static final String[] IMAGE_URLS = Constants.IMAGES;
	private LayoutInflater inflater;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private DisplayImageOptions options;
	private List<RatingItem> data = new ArrayList<RatingItem>();
	private int page = 1;
	private Context context;

	public RatingListViewAdapter(Context context, List<RatingItem> data) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.garner)
				.showImageForEmptyUri(R.drawable.garner)
				.showImageOnFail(R.drawable.garner).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				/* .displayer(new RoundedBitmapDisplayer(20)) */.build();
		this.data = data;
	}

	public long getItemId(int position) {
		return position;
	}

	public void add(List<RatingItem> data) {
		this.data.addAll(data);
	}

	public int getCount() {
		// return 10;
		return data.size();
		// return IMAGE_URLS.length;
	}

	public int getPosition(RatingItem item) {
		return data.indexOf(item);
	}

	public Object getItem(int position) {
		return data.get(position);
		// return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;
		final IAndroidUtils androidUtils = AndroidUtilsFactory.getInstanse(
				context).getAndroidUtils();

		if (convertView == null) {
			view = inflater.inflate(R.layout.rating_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.txtRatingUserName = (TextView) view
					.findViewById(R.id.txtRatingUserName);
			viewHolder.txtTotalScore = (TextView) view.findViewById(R.id.txtRatingUserTotalScore);
			viewHolder.ratingAvatar = (CircleImageView) view.findViewById(R.id.ratingProfileImage);
			viewHolder.txtUserPlace = (TextView) view
					.findViewById(R.id.txtRatingUserPlace);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		final RatingItem item = (RatingItem) getItem(position);
		viewHolder.txtRatingUserName.setText(String.valueOf(item.getUserID()));
		viewHolder.txtTotalScore.setText(String.valueOf(item.getTotalScore()));
		viewHolder.txtUserPlace.setText(String.valueOf(item.getTotalGames()));
		/*viewHolder.pic.setTag(new StringBuffer().append("http://solveit.kz")
				.append(item.getListReportFile().get(0).getUrl()).toString());*/
		/*ImageLoader.getInstance().displayImage(
				new StringBuffer().append(IAndroidUtils.VIOAPP_SERVER_URL_NON_SSL)
						.append(item.getListReportFile().get(0).getUrl())
						.toString(), viewHolder.pic, options,
				animateFirstListener);
		new DownloadImagesTask(viewHolder.icReportType, item.getReportTypeId()).execute();*/
		/*
		 * new DownloadImagesTask(viewHolder.pic, viewHolder.icReportType, data
		 * .get(position).getReportTypeId(), new StringBuffer()
		 * .append("http://solveit.kz")
		 * .append(item.getListReportFile().get(0).getUrl()).toString(),
		 * options, animateFirstListener).execute();
		 */
		// ImageLoader.getInstance().displayImage(IMAGE_URLS[position],
		// viewHolder.pic, options, animateFirstListener);
		

		if (position == getCount() - 1) {
			new AsyncTask<Void, Void, List<RatingItem>>() {
				@Override
				protected void onPreExecute() {

				};

				@Override
				protected List<RatingItem> doInBackground(Void... params) {
					try {
						IAndroidUtils androidUtils = AndroidUtilsFactory
								.getInstanse(context).getAndroidUtils();
						data.addAll(androidUtils.getRating(page));
					} catch (Exception e) {
						e.printStackTrace();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					return data;
				}

				@Override
				protected void onPostExecute(List<RatingItem> result) {
					if (data.isEmpty()) {
						return;
					}
					notifyDataSetChanged();
					page++;
				}
			}.execute((Void[]) null);
		}
		return view;
	}

	private static class ViewHolder {
		CircleImageView ratingAvatar;
		TextView txtRatingUserName;
		TextView txtTotalScore;
		TextView txtUserPlace;
	}
}
