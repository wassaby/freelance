package com.daurenzg.betstars.auth;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.account.AccountActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.AuthItem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends Activity {

	ImageView back;
	ImageView done;
	EditText pswd;
	EditText email;
	TextView txtRestore;
	IAndroidUtils androidUtils;
	AuthItem auth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_sign_in, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);

		androidUtils = AndroidUtilsFactory.getInstanse(SignInActivity.this)
				.getAndroidUtils();
		auth = new AuthItem();
		back = (ImageView) findViewById(R.id.iv_back_in);
		done = (ImageView) findViewById(R.id.iv_done_in);
		email = (EditText) findViewById(R.id.email);
		pswd = (EditText) findViewById(R.id.password);
		txtRestore = (TextView) findViewById(R.id.txtRestorePswd);

		email.addTextChangedListener(textWatcher);
		pswd.addTextChangedListener(textWatcher);
		
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		txtRestore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(SignInActivity.this,
						RestorePasswordActivity.class);
				startActivity(i);
			}
		});

		done.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTask<Void, AuthItem, AuthItem>() {
					@Override
					protected AuthItem doInBackground(Void... params) {
						try {
							auth = androidUtils.auth(
									email.getText().toString(), pswd.getText()
											.toString());
						} catch (BUtilsException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return auth;
					}

					@Override
					protected void onPostExecute(AuthItem result) {
						if (auth.getResponceCode() == 422) {
							Toast.makeText(SignInActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
							email.getText().clear();
							pswd.getText().clear();
						} else {
							Bundle mBundle = new Bundle();
							mBundle.putSerializable("authItem", auth);
							Intent i = new Intent(SignInActivity.this,
									AccountActivity.class);
							i.putExtras(mBundle);
							startActivity(i);
						}
					}
				}.execute();
				/*new AsyncTask<Void, AuthItem, AuthItem>() {
					int code = 0;
					@Override
					protected AuthItem doInBackground(Void... params) {
						try {
							auth = androidUtils.auth(
									email.getText().toString(), pswd.getText()
											.toString());
							auth.setResponceCode(code);
						} catch (BUtilsException e) {
							auth.setResponceCode(e.getCode());
							auth.setResponseMessage(e.getMessage());
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return auth;
					}

					@Override
					protected void onPostExecute(AuthItem result) {
						if (auth.getResponceCode() != 0) {
							String toastMsg = "";
							try {
								JSONObject msg = new JSONObject(auth.getResponseMessage());
								Iterator<String> keys= msg.keys();
								while (keys.hasNext()) 
								{
							        String keyValue = (String)keys.next();
							        String valueString = msg.getString(keyValue);
							        toastMsg = toastMsg +("\n") + valueString;
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Toast.makeText(SignInActivity.this, toastMsg, Toast.LENGTH_LONG).show();
							email.getText().clear();
							pswd.getText().clear();
						} else {
							Bundle mBundle = new Bundle();
							mBundle.putSerializable("authItem", auth);
							Intent i = new Intent(SignInActivity.this,
									AccountActivity.class);
							i.putExtras(mBundle);
							startActivity(i);
						}
					}
				}.execute();*/
			}
		});
	}

	private void checkFieldsForEmptyValues() {
		ImageView b = (ImageView) findViewById(R.id.iv_done_in);
		String s1 = email.getText().toString();
		String s2 = pswd.getText().toString();
		if (!s1.equals("") && !s2.equals("")) {
			b.setVisibility(View.VISIBLE);
		} else {
			b.setVisibility(View.GONE);
		}
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
	
	@Override
	public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }

}
