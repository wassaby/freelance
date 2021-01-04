package com.daurenzg.betstars.auth;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.account.AccountActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.AuthItem;
import com.daurenzg.betstars.wao.CityItem;
import com.daurenzg.betstars.wao.CountryItem;
import com.daurenzg.betstars.wao.RegisterItem;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity2 extends Activity {

	TextView txtSignUp;
	private Spinner countriesSpinner;
	private Spinner citySpinner;
	private IAndroidUtils androidUtils;
	ArrayList<CountryItem> countries = new ArrayList<CountryItem>();
	ArrayList<CityItem> cities = new ArrayList<CityItem>();
	RegisterItem regItem;
	AuthItem regItemResponse;
	ImageView ivDone;
	ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
				.inflate(R.layout.ab_sign_up2, null);

		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionBarLayout);
		txtSignUp = (TextView) findViewById(R.id.txtSignIn);
		
		setContentView(R.layout.activity_sign_up2);
		androidUtils = AndroidUtilsFactory.getInstanse(SignUpActivity2.this)
				.getAndroidUtils();
		regItemResponse = new AuthItem();
		countriesSpinner = (Spinner) findViewById(R.id.countries);
		citySpinner = (Spinner) findViewById(R.id.cities);
		new GetCountries().execute();
		
		regItem = (RegisterItem) getIntent().getSerializableExtra("regItem");
		ivDone = (ImageView) findViewById(R.id.iv_done_up2);
		ivBack = (ImageView) findViewById(R.id.iv_back_up2);
		
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		ivDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTask<Void, AuthItem, AuthItem>() {
					int code = 0;
					@Override
					protected AuthItem doInBackground(Void... params) {
						try {
							regItemResponse = androidUtils.registerNewUSer(regItem.getEmail(), regItem.getPassword());
							regItemResponse.setResponceCode(code);
						} catch (BUtilsException e) {
							// TODO Auto-generated catch block
							regItemResponse.setResponceCode(e.getCode());
							regItemResponse.setResponseMessage(e.getMessage());
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return regItemResponse;
					}
					
					@Override
					protected void onPostExecute(AuthItem result) {
						if (regItemResponse.getResponceCode() != 0) {
							String toastMsg = "";
							try {
								JSONObject msg = new JSONObject(regItemResponse.getResponseMessage());
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
							Toast.makeText(SignUpActivity2.this, toastMsg, Toast.LENGTH_LONG).show();
						} else {
							Bundle mBundle = new Bundle();
							mBundle.putSerializable("authItem", regItemResponse);
							Intent i = new Intent(SignUpActivity2.this,
									AccountActivity.class);
							i.putExtras(mBundle);
							startActivity(i);
						}
					}
				}.execute();
			}
		});
	}

	/**
	 * Async task to get all food categories
	 * */
	private class GetCountries extends AsyncTask<Void, String, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(Void... arg0) {
			try {
				countries = androidUtils.getCountries();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			countriesSpinner.setAdapter(new ArrayAdapter<CountryItem>(
					SignUpActivity2.this,
					android.R.layout.simple_spinner_dropdown_item, countries));
			countriesSpinner
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int position, long id) {
							// TODO Auto-generated method stub
							CountryItem c = (CountryItem) countriesSpinner.getSelectedItem();
							GetCities task = new GetCities();
							task.execute(c.getId());
							//new GetCities.execute(c.getId());
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
		}
	}

	private class GetCities extends AsyncTask<Long, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Long... arg0) {
			long countryId = arg0[0];
			try {
				cities = androidUtils.getCities(countryId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			citySpinner.setAdapter(new ArrayAdapter<CityItem>(
					SignUpActivity2.this,
					android.R.layout.simple_spinner_dropdown_item, cities));
			citySpinner
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int position, long id) {
							// TODO Auto-generated method stub
							CityItem cc = (CityItem) citySpinner.getSelectedItem();
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub
						}
					});
		}
	}
	
	@Override
	public void onBackPressed() {
        // Write your code here
        super.onBackPressed();
    }
	
	/*private void checkFieldsForEmptyValues() {
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
	};*/
}
