package com.daurenzg.betstars.tournaments;

import java.util.Formatter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.ShortListActivity;
import com.daurenzg.betstars.messages.MessagesActivity;
import com.daurenzg.betstars.task.DownloadImagesTask;
import com.daurenzg.betstars.utils.BTournamentItem;

public class ViewTournamentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final BTournamentItem tournamentItem = (BTournamentItem) getIntent().getExtras().get("tournamentItem");
		boolean is9Players = tournamentItem.getAccountInfoList().size()>6;
		if (is9Players){
			setContentView(R.layout.view_tournament_activity_9_players);
			
			((TextView)findViewById(R.id.pl9TextView3)).setText(tournamentItem.getAccountInfoList().get(0).getName());
			((TextView)findViewById(R.id.pl9TextView4)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(0).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(0).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(0).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.pl9imageView5);
				imageView.setTag(tournamentItem.getAccountInfoList().get(0).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9rogressBar1)).execute();
			}
			
			((TextView)findViewById(R.id.pl9SearchTournamentPlayersCountTextView)).setText(tournamentItem.getAccountInfoList().get(1).getName());
			((TextView)findViewById(R.id.pl9TextView2)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(1).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(1).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(1).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.pl9imageView3);
				imageView.setTag(tournamentItem.getAccountInfoList().get(1).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9progressBar2)).execute();
			}
			
			((TextView)findViewById(R.id.pl9textView8)).setText(tournamentItem.getAccountInfoList().get(2).getName());
			((TextView)findViewById(R.id.pl9textView9)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(2).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(2).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(2).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.pl9imageView10);
				imageView.setTag(tournamentItem.getAccountInfoList().get(2).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9progressBar3)).execute();
			}
			
			((TextView)findViewById(R.id.pl9textView5)).setText(tournamentItem.getAccountInfoList().get(3).getName());
			((TextView)findViewById(R.id.pl9textView6)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(3).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(3).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(3).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.pl9imageView7);
				imageView.setTag(tournamentItem.getAccountInfoList().get(3).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.plprogressBar4)).execute();
			}
			
			((TextView)findViewById(R.id.pl9textView18)).setText(tournamentItem.getAccountInfoList().get(4).getName());
			((TextView)findViewById(R.id.pl9textView16)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(4).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(4).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(4).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.pl9imageView17);
				imageView.setTag(tournamentItem.getAccountInfoList().get(4).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9progressBar5)).execute();
			}
			
			((TextView)findViewById(R.id.pl9textView13)).setText(tournamentItem.getAccountInfoList().get(5).getName());
			((TextView)findViewById(R.id.pl9textView11)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(5).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(5).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(5).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.pl9imageView12);
				imageView.setTag(tournamentItem.getAccountInfoList().get(5).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9progressBar6)).execute();
			}
			
			((TextView)findViewById(R.id.pl9TextView01)).setText(tournamentItem.getAccountInfoList().get(6).getName());
			((TextView)findViewById(R.id.pl9TextView01TextView01)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(6).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(6).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(6).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView1);
				imageView.setTag(tournamentItem.getAccountInfoList().get(6).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9ProgressBar112)).execute();
			}
			
			((TextView)findViewById(R.id.pl9TextView011)).setText(tournamentItem.getAccountInfoList().get(7).getName());
			((TextView)findViewById(R.id.pl9TextView0123)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(7).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(7).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(7).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView2);
				imageView.setTag(tournamentItem.getAccountInfoList().get(7).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9progressBar11111)).execute();
			}
			
			((TextView)findViewById(R.id.pl9textView888)).setText(tournamentItem.getAccountInfoList().get(7).getName());
			((TextView)findViewById(R.id.pl9textView8887)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(7).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(7).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(7).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView3);
				imageView.setTag(tournamentItem.getAccountInfoList().get(7).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.pl9progressBar11112)).execute();
			}
			
		} else {
			setContentView(R.layout.view_tournament_activity_6_players);
			
			((TextView)findViewById(R.id.textView3)).setText(tournamentItem.getAccountInfoList().get(0).getName());
			((TextView)findViewById(R.id.textView4)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(0).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(0).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(0).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView5);
				imageView.setTag(tournamentItem.getAccountInfoList().get(0).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.progressBar1)).execute();
			}
			
			((TextView)findViewById(R.id.searchTournamentPlayersCountTextView)).setText(tournamentItem.getAccountInfoList().get(1).getName());
			((TextView)findViewById(R.id.textView2)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(1).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(1).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(1).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView3);
				imageView.setTag(tournamentItem.getAccountInfoList().get(1).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.progressBar2)).execute();
			}
			
			((TextView)findViewById(R.id.textView8)).setText(tournamentItem.getAccountInfoList().get(2).getName());
			((TextView)findViewById(R.id.textView9)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(2).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(2).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(2).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView10);
				imageView.setTag(tournamentItem.getAccountInfoList().get(2).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.progressBar3)).execute();
			}
			
			((TextView)findViewById(R.id.textView5)).setText(tournamentItem.getAccountInfoList().get(3).getName());
			((TextView)findViewById(R.id.textView6)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(3).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(3).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(3).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView7);
				imageView.setTag(tournamentItem.getAccountInfoList().get(3).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.progressBar4)).execute();
			}
			
			((TextView)findViewById(R.id.textView18)).setText(tournamentItem.getAccountInfoList().get(4).getName());
			((TextView)findViewById(R.id.textView16)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(4).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(4).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(4).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView17);
				imageView.setTag(tournamentItem.getAccountInfoList().get(4).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.progressBar5)).execute();
			}
			
			((TextView)findViewById(R.id.textView13)).setText(tournamentItem.getAccountInfoList().get(5).getName());
			((TextView)findViewById(R.id.textView11)).setText(new StringBuffer("¹").append(new Formatter().format("%06d%n", tournamentItem.getAccountInfoList().get(5).getId()).toString()));
			if (tournamentItem.getAccountInfoList().get(5).getPhoto()!=null&&tournamentItem.getAccountInfoList().get(5).getPhoto().length()!=0){
				ImageView imageView = (ImageView)findViewById(R.id.imageView12);
				imageView.setTag(tournamentItem.getAccountInfoList().get(5).getPhoto());
				new DownloadImagesTask(imageView, (ProgressBar) findViewById(R.id.progressBar6)).execute();
			}
			
		}
		findViewById(is9Players?R.id.pl9openTournamentChatListBtn:R.id.openTournamentChatListBtn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ViewTournamentActivity.this, MessagesActivity.class);
				intent.putExtra("tournamentItem", tournamentItem);
				startActivity(intent);
			}
		});
		
		findViewById(is9Players?R.id.pl9openTournamentStatisticsBtn:R.id.openTournamentStatisticsBtn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ViewTournamentActivity.this, TournamentResultsActivity.class);
				intent.putExtra("tournamentItem", tournamentItem);
				startActivity(intent);
			}
		});
		
		TextView betTextView = (TextView) findViewById(is9Players?R.id.pl9betTextView:R.id.betTextView);
        betTextView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ViewTournamentActivity.this, ShortListActivity.class);
				intent.putExtra("tournamentItem", tournamentItem);
				startActivity(intent);
			}
		});
		
	}

}
