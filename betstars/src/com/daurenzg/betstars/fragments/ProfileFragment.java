package com.daurenzg.betstars.fragments;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;

import com.daurenzg.betstars.R;
import com.daurenzg.betstars.account.SettingsActivity;
import com.daurenzg.betstars.utils.AndroidUtilsFactory;
import com.daurenzg.betstars.utils.BUtilsException;
import com.daurenzg.betstars.utils.IAndroidUtils;
import com.daurenzg.betstars.wao.CityItem;
import com.daurenzg.betstars.wao.CountryItem;
import com.daurenzg.betstars.wao.ProfileItem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment {
	
	public ProfileFragment(){}
	
	ProfileItem data;
	IAndroidUtils androidUtils;
	TextView userName;
	TextView email;
	TextView bDay;
	TextView location;
	String locationBuff;
	ImageView settings;
	
	ArrayList<CountryItem> countries = new ArrayList<CountryItem>();
	ArrayList<CityItem> cities = new ArrayList<CityItem>();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().getActionBar().setDisplayShowCustomEnabled(true);
        final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater(savedInstanceState).inflate(R.layout.ab_profile, null);
        getActivity().getActionBar().setCustomView(actionBarLayout);
        
        settings = (ImageView) actionBarLayout.findViewById(R.id.iv_settings);
        settings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent settingsI = new Intent(getActivity(), SettingsActivity.class);
			startActivity(settingsI);
			}
		});
        
        userName = (TextView) rootView.findViewById(R.id.user_name_profile);
        email = (TextView) rootView.findViewById(R.id.email_profile);
        bDay = (TextView) rootView.findViewById(R.id.bday_profile);
        location = (TextView) rootView.findViewById(R.id.country_profile);
        androidUtils = AndroidUtilsFactory.getInstanse(getActivity())
				.getAndroidUtils();
        if(getArguments() != null)
        	data = (ProfileItem) getArguments().getSerializable("data");
        
        userName.setText(data.getName());
		email.setText(data.getEmail());
        
		//bDay.setText(data.get)
		new GetCountries().execute();
        /*new AsyncTask<Void, ProfileItem, ProfileItem>() {
			@Override
			protected ProfileItem doInBackground(Void... params) {
				data = new ProfileItem();
				try {
					data = androidUtils.getProfile();
				} catch (BUtilsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return data;
			}

			@Override
			protected void onPostExecute(ProfileItem result) {
				userName.setText(data.getName());
				email.setText(data.getEmail());
				//bDay.setText(data.get)
				new GetCountries().execute();
			}
		}.execute();*/
        return rootView;
    }
	
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
			for (Iterator iter = countries.iterator(); iter.hasNext();){
				CountryItem country = (CountryItem) iter.next();
				if (country.getId() == data.getCountryId()){
					locationBuff = country.getTitle();
					location.setText(locationBuff);
					new GetCities().execute(data.getCountryId());
				}
			}
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
			for (Iterator iter = cities.iterator(); iter.hasNext();){
				CityItem city = (CityItem) iter.next();
				if (city.getId() == data.getCityId()){
					locationBuff = locationBuff + ", " + city.getTitle();
					location.setText(locationBuff);
				}
			}
		}
	}
}
