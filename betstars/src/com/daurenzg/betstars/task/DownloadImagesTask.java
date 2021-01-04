package com.daurenzg.betstars.task;

import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DownloadImagesTask extends AsyncTask<ImageView, Void, Drawable> {

	private static final Map<String, Drawable> imagesCash = new HashMap<String, Drawable>();
	
	private ImageView imageView = null;
	private ProgressBar bar = null;
	
	
	protected void onPreExecute() {
		imageView.setVisibility(View.INVISIBLE);
		bar.setVisibility(View.VISIBLE);
	}
	
	public DownloadImagesTask(ImageView imageView, ProgressBar bar) {
		super();
		this.imageView = imageView;
		this.bar = bar;
	}

	protected Drawable doInBackground(ImageView... imageViews) {
		String url = (String) imageView.getTag();
		Drawable drawable = imagesCash.get(url);
		if (drawable==null){
			drawable = downloadImage(url);
			imagesCash.put(url, drawable);
		}
		return drawable;
	}

	protected void onPostExecute(Drawable result) {
		if (result!=null)
			imageView.setImageDrawable(result);
		imageView.setVisibility(View.VISIBLE);
		bar.setVisibility(View.INVISIBLE);
	}

	private Drawable downloadImage(String url) {
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream(new java.net.URL(url).openStream(), "");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return drawable;
	}
}