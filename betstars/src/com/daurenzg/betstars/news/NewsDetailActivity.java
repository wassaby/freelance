package com.daurenzg.betstars.news;

import java.io.File;

import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.adapter.NewsDetailParagraphListAdapter;
import com.daurenzg.betstars.utils.BNewsItem;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

public class NewsDetailActivity extends Activity {

	WebView webNews;
	ImageView imgNews, ivBack;
	TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_detail_activity);
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_news_detail, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		BNewsItem newsItem = (BNewsItem) getIntent().getExtras()
				.get("newsItem");
		webNews = (WebView) findViewById(R.id.webViewNews);
		title = (TextView) findViewById(R.id.newsTitleTitleTextView);
		imgNews = (ImageView) findViewById(R.id.newsTitleImageView);
		ivBack = (ImageView) findViewById(R.id.iv_back_in_news);
		
		title.setText(newsItem.getNewsTitle());
		webNews.loadDataWithBaseURL(null, newsItem.getText(), "text/html",
				"utf-8", null);

		// String path =
		// com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme.FILE.wrap(new
		// StringBuffer().append(IAndroidUtils.BETSARS_SERVER_URL_ROOT).append(newsItem.getPhotoUrl()).toString());
		// MemoryCacheUtils.findCachedBitmapsForImageUri(new
		// StringBuffer().append(IAndroidUtils.BETSARS_SERVER_URL_ROOT).append(newsItem.getPhotoUrl()).toString(),
		// ImageLoader.getInstance().getMemoryCache());
		File file = DiskCacheUtils.findInCache(
				new StringBuffer()
						.append(IAndroidUtils.BETSARS_SERVER_URL_ROOT)
						.append(newsItem.getPhotoUrl()).toString(), ImageLoader
						.getInstance().getDiscCache());

		Uri uri = Uri.fromFile(file);
		imgNews.setImageURI(uri);
		
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }

}
