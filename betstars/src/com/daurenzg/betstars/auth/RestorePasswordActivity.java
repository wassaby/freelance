package com.daurenzg.betstars.auth;

import com.daurenzg.betstars.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class RestorePasswordActivity extends Activity {

	ImageView back;
	ImageView done;
	EditText email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restore_password);
		
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_restore_pswd, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		
		back = (ImageView) findViewById(R.id.iv_back_restore);
		done = (ImageView) findViewById(R.id.iv_done_restore);
		email = (EditText) findViewById(R.id.email_pswd);
		
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
