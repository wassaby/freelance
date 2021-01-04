package com.daurenzg.betstars.auth;

import java.util.Timer;
import java.util.TimerTask;

import com.daurenzg.betstars.R;
import com.viewpagerindicator.CirclePageIndicator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FirstActivity extends Activity {

	Button btnSignIn;
	Button btnSignUp;
	int position = 0;
	Timer swipeTimer;
	int currentPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_first);

		final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
		final ImagePagerAdapter adapter = new ImagePagerAdapter();
		viewPager.setAdapter(adapter);

		final CirclePageIndicator circleIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		circleIndicator.setViewPager(viewPager);

		final Handler handler = new Handler();
		final Runnable Update = new Runnable() {
			@Override
			public void run() {
				if (currentPage == 9) {
					currentPage = 0;
				}
				viewPager.setCurrentItem(currentPage++, true);
			}
		};

		swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(Update);
			}
		}, 500, 3000);

		btnSignIn = (Button) findViewById(R.id.signIn);
		btnSignUp = (Button) findViewById(R.id.signUp);

		btnSignIn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FirstActivity.this, SignInActivity.class);
				startActivity(i);
			}
		});
		
		btnSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(FirstActivity.this, SignUpActivity1.class);
				startActivity(i);
			}
		});
	}

	private class ImagePagerAdapter extends PagerAdapter {
		private final int[] mImages = new int[] { R.drawable.rules1_, R.drawable.rules2_, R.drawable.rules3_, R.drawable.rules4_, R.drawable.rules5_, R.drawable.rules6_, R.drawable.rules6_, R.drawable.rules7_, R.drawable.rules8_ };
		//private final String[] mLabels = new String[]{"text 1", "text 2", "text 3", "text 4"};
		private final String[] mLabels = getResources().getStringArray(R.array._faq_rules_tournaments_body);
		private final String[] mHeaders = getResources().getStringArray(R.array.faq_rules);
		Context context;
		LayoutInflater inflater;

		@Override
		public void destroyItem(final ViewGroup container, final int position,
				final Object object) {
			((ViewPager) container).removeView((RelativeLayout) object);
		}

		@Override
		public int getCount() {
			return this.mImages.length;
		}

		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {
			// Declare Variables
			TextView txtLabel, txtImageLabelHeader;
			ImageView imgRule;
	 
			final Context context = FirstActivity.this;
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(R.layout.rules_pager_item, container,
					false);
	 
			txtLabel = (TextView) itemView.findViewById(R.id.txtImageLabel);
			txtLabel.setText(mLabels[position]);
			txtImageLabelHeader = (TextView) itemView.findViewById(R.id.txtImageLabelHeader);
			if (position < 6){
				txtImageLabelHeader.setText(mHeaders[0]);
			} else 
				txtImageLabelHeader.setText(mHeaders[1]);
			imgRule = (ImageView) itemView.findViewById(R.id.ivRulesImage);
			if (position == 7){
				imgRule.setVisibility(View.GONE);
			} else 
				imgRule.setImageResource(mImages[position]);
			((ViewPager) container).addView(itemView);
	 
			return itemView;
		}

		@Override
		public boolean isViewFromObject(final View view, final Object object) {
			return view == ((RelativeLayout) object);
		}
	}

}
