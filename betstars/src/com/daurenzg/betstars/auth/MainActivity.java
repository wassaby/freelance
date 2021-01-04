package com.daurenzg.betstars.auth;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.daurenzg.betstars.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.onesignal.OneSignal;
import com.onesignal.OneSignal.NotificationOpenedHandler;

public class MainActivity extends Activity {

	private class ExampleNotificationOpenedHandler implements NotificationOpenedHandler {
		  /**
		   * Callback to implement in your app to handle when a notification is opened from the Android status bar or
		   * a new one comes in while the app is running.
		   * This method is located in this activity as an example, you may have any class you wish implement NotificationOpenedHandler and define this method.
		   *
		   * @param message        The message string the user seen/should see in the Android status bar.
		   * @param additionalData The additionalData key value pair section you entered in on onesignal.com.
		   * @param isActive       Was the app in the foreground when the notification was received.
		   */
		  @Override
		  public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
		    String messageTitle = "OneSignal Example", messageBody = message;

		    try {
		      if (additionalData != null) {
		        if (additionalData.has("title"))
		          messageTitle = additionalData.getString("title");
		        if (additionalData.has("actionSelected"))
		          messageBody += "\nPressed ButtonID: " + additionalData.getString("actionSelected");

		        messageBody = message + "\n\nFull additionalData:\n" + additionalData.toString();
		      }
		    } catch (JSONException e) {
		    }
		    
		    new AlertDialog.Builder(MainActivity.this)
		                   .setTitle(messageTitle)
		                   .setMessage(messageBody)
		                   .setCancelable(true)
		                   .setPositiveButton("OK", null)
		                   .create().show();
		  }
		}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		OneSignal.init(this, "1084217806867", "58aa5714-70d0-11e5-a990-5740ea98207f", new ExampleNotificationOpenedHandler());
		OneSignal.init(this, "573741894573", "58aa5714-70d0-11e5-a990-5740ea98207f", new ExampleNotificationOpenedHandler());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//		getActionBar().hide();
		initImageLoader(getApplicationContext());
		setContentView(R.layout.activity_main);
		final Intent i = new Intent(this, FirstActivity.class);
		Thread logoTimer = new Thread() {
			@Override
			public void run() {
				try {
					int logoTimer = 0;
					while (logoTimer < 3000) {
						sleep(100);
						logoTimer = logoTimer + 100;
					};
					startActivity(i);
				} catch (InterruptedException e) {
					// TODO: автоматически сгенерированный блок catch.
					e.printStackTrace();
				} finally {
					finish();
				}
			}
		};
		logoTimer.start();
	}
	
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.NONE)
				.build();

		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		//config.diskCache(diskCache)
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		//config.defaultDisplayImageOptions(defaultOptions);
		// config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

}
