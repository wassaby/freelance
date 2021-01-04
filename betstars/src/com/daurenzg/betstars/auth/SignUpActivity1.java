package com.daurenzg.betstars.auth;

import org.json.JSONObject;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.wao.RegisterItem;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity1 extends Activity {

	ImageView back;
	ImageView next;
	EditText pswd;
	EditText email;
	EditText username;
	JSONObject registerObj;
	LoginButton loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		setContentView(R.layout.activity_sign_up_activity1);

		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_sign_up, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		registerObj = new JSONObject();

		back = (ImageView) findViewById(R.id.iv_back_up1);
		next = (ImageView) findViewById(R.id.iv_next_up1);
		email = (EditText) findViewById(R.id.email);
		pswd = (EditText) findViewById(R.id.password);
		username = (EditText) findViewById(R.id.username);
		loginBtn = (LoginButton) findViewById(R.id.loginFb);

		// set listeners
		email.addTextChangedListener(textWatcher);
		pswd.addTextChangedListener(textWatcher);
		username.addTextChangedListener(textWatcher);

		// run once to disable if empty
		checkFieldsForEmptyValues();
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				RegisterItem regItem = new RegisterItem();
				regItem.setEmail(email.getText().toString());
				regItem.setPassword(pswd.getText().toString());
				Bundle mBundle = new Bundle(); 
				mBundle.putSerializable("regItem", regItem); 
				Intent signUp2 = new Intent(SignUpActivity1.this, SignUpActivity2.class);
				signUp2.putExtras(mBundle);
				startActivity(signUp2);
				finish();
			}
		});

	}

	// TextWatcher
	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i2,
				int i3) {
		}

		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i2,
				int i3) {
			checkFieldsForEmptyValues();
		}

		@Override
		public void afterTextChanged(Editable editable) {
		}
	};

	private void checkFieldsForEmptyValues() {
		ImageView b = (ImageView) findViewById(R.id.iv_next_up1);

		String s1 = email.getText().toString();
		String s2 = pswd.getText().toString();
		String s3 = username.getText().toString();

		if (!s1.equals("") && !s2.equals("") && !s3.equals("")) {
			b.setVisibility(View.VISIBLE);
		} else {
			b.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }

}
