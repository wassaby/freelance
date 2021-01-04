package com.daurenzg.betstars.account;

import com.daurenzg.betstars.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	
	Switch switchPush;
	TextView txtChangePswd;
	TextView txtSignOut;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		final ActionBar actionBar = getActionBar();
		
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.ab_settings, null);
        actionBar.setCustomView(actionBarLayout);

        switchPush = (Switch) findViewById(R.id.switchPush);
		txtChangePswd = (TextView) findViewById(R.id.txtChangePswd);
		txtSignOut = (TextView) findViewById(R.id.txtSignOut);
		
		back = (ImageView) actionBarLayout.findViewById(R.id.iv_back_up_settings);
		back.setOnClickListener(new View.OnClickListener() {
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
