package com.daurenzg.betstars.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.daurenzg.betstars.wao.AuthItem;
import com.daurenzg.betstars.wao.CityItem;
import com.daurenzg.betstars.wao.CountryItem;
import com.daurenzg.betstars.wao.ProfileItem;
import com.daurenzg.betstars.wao.RatingItem;

public class AndroidUtilLevel7Impl extends DbOpenHelper implements
		IAndroidUtils {

	private static AndroidUtilLevel7Impl instanse = null;

	private Context context = null;

	private AndroidUtilLevel7Impl(Context context) {
		super(context);
		this.context = context;
	}

	public static AndroidUtilLevel7Impl getInstanse(Context context) {
		if (instanse == null) {
			instanse = new AndroidUtilLevel7Impl(context);
		}
		return instanse;
	}

	public static String getJSONfromURL(String urlParam) {
		String parsedString = "";
		try {
			URL url = new URL(urlParam);
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			InputStream is = httpConn.getInputStream();
			parsedString = convertInputStreamToString(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsedString;
	}

	private static void setDBAccessToken(SQLiteDatabase db, String accessToken,
			int expiresIn, String tokenType) {

		db.delete(BETSTARS_SETTINGS_TABLE_NAME, null, null);// Чтобы не
															// накапливались
															// данные после
															// каждой
															// авторизации

		ContentValues values = new ContentValues();
		values.put("ACCESS_TOKEN", accessToken);
		values.put("ACCESS_TOKEN_EXPIRES_IN", expiresIn);
		values.put("ACCESS_TOKEN_TYPE", tokenType);
		db.insert(BETSTARS_SETTINGS_TABLE_NAME, null, values);
	}

	private static String getDBAccessToken(SQLiteDatabase db) {

		Cursor cursor = db.query(BETSTARS_SETTINGS_TABLE_NAME,
				new String[] { ACCESS_TOKEN }, null, null, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		String token = cursor.getString(0);
		return token;
	}

	private static String doPost(String urlParam, Map params)
			throws BUtilsException {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlParam);
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			httpConn.setRequestProperty("Accept", "application/json");
			httpConn.setRequestMethod("POST");
			httpConn.connect();

			JSONObject holder = new JSONObject(params);
			OutputStreamWriter writer = new OutputStreamWriter(
					httpConn.getOutputStream());
			String output = holder.toString();
			writer.write(output);
			writer.flush();
			writer.close();

			int HttpResult = httpConn.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
			} else {
				// InputStream in = httpConn.getErrorStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpConn.getErrorStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				JSONObject err = new JSONObject(sb.toString());
				JSONObject errors = err.getJSONObject("errors");
				String msg = err.getString("message");
				int statusCode = err.getInt("status_code");
				throw new BUtilsException(statusCode, errors.toString());
			}
		} catch (BUtilsException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static String doGet(String urlParam, Map params)
			throws BUtilsException {
		StringBuilder sb = new StringBuilder();
		try {
			URL url = new URL(urlParam);
			URLConnection conn = url.openConnection();
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setDoInput(true);
			httpConn.setDoOutput(false);
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			httpConn.setRequestProperty("Accept", "application/json");
			httpConn.setRequestMethod("GET");
			httpConn.connect();

			// JSONObject holder = new JSONObject(params);
			// OutputStreamWriter writer = new OutputStreamWriter(
			// httpConn.getOutputStream());
			// String output = holder.toString();
			// writer.write(output);
			// writer.flush();
			// writer.close();

			int HttpResult = httpConn.getResponseCode();
			if (HttpResult == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
			} else {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						httpConn.getErrorStream(), "utf-8"));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();

				JSONObject err = new JSONObject(sb.toString());
				JSONObject errors = err.getJSONObject("errors");
				String msg = err.getString("message");
				int statusCode = err.getInt("status_code");
				throw new BUtilsException(statusCode, errors.toString());
			}
		} catch (BUtilsException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String convertInputStreamToString(InputStream ists)
			throws IOException {
		if (ists != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader r1 = new BufferedReader(new InputStreamReader(
						ists, "UTF-8"));
				while ((line = r1.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				ists.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	@Override
	public ArrayList<CountryItem> getCountries() throws BUtilsException,
			JSONException {
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL).append(
				"/data/countries").toString();
		ArrayList<CountryItem> countryList = new ArrayList<CountryItem>();
		JSONObject data = new JSONObject(getJSONfromURL(url));
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			CountryItem country = new CountryItem();
			JSONObject jsonCountry = dataArr.getJSONObject(i);
			country.setId(jsonCountry.getLong("id"));
			country.setTitle(jsonCountry.getString("title"));
			countryList.add(country);
		}
		return countryList;
	}

	@Override
	public ArrayList<CityItem> getCities(long countryId)
			throws BUtilsException, JSONException {
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/data/cities/").append(countryId).toString();
		ArrayList<CityItem> cityList = new ArrayList<CityItem>();
		JSONObject data = new JSONObject(getJSONfromURL(url));
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			CityItem city = new CityItem();
			JSONObject jsonCity = dataArr.getJSONObject(i);
			city.setId(jsonCity.getLong("id"));
			city.setTitle(jsonCity.getString("title"));
			city.setCountryId(jsonCity.getLong("country_id"));
			cityList.add(city);
		}
		return cityList;
	}

	@Override
	public AuthItem registerNewUSer(String email, String pswd)
			throws BUtilsException, JSONException {
		// TODO Auto-generated method stub
		AuthItem regItem = new AuthItem();
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL).append(
				"/auth/register").toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", pswd);
		String response = doPost(url, params);
		JSONObject data = new JSONObject(response);
		regItem.setAccessToken(data.getString("access_token"));
		regItem.setTokenType(data.getString("token_type"));
		regItem.setExpiresIn(data.getInt("expires_in"));

		SQLiteDatabase db = getWritableDatabase();
		setDBAccessToken(db, data.getString("access_token"),
				data.getInt("expires_in"), data.getString("token_type"));

		regItem = auth(email, pswd);
		return regItem;
	}

	@Override
	public AuthItem auth(String email, String pswd) throws BUtilsException,
			JSONException {
		// TODO Auto-generated method stub
		AuthItem auth = new AuthItem();
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL).append(
				"/auth/login-email").toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", pswd);

		String response = doPost(url, params);
		JSONObject data = new JSONObject(response);
		auth.setAccessToken(data.getString("access_token"));
		auth.setTokenType(data.getString("token_type"));
		auth.setExpiresIn(data.getLong("expires_in"));
		SQLiteDatabase db = getWritableDatabase();
		setDBAccessToken(db, data.getString("access_token"),
				data.getInt("expires_in"), data.getString("token_type"));
		auth.setProfItem(getProfile());
		return auth;
	}

	@Override
	public ProfileItem getProfile() throws BUtilsException, JSONException {
		Cursor curs = getDb().query("BETSTARS_SETTINGS",
				new String[] { "ACCESS_TOKEN" }, null, null, null, null, null);
		String accessToken = "";
		if (curs.moveToFirst()) {
			int idColIndex = curs.getColumnIndex("ACCESS_TOKEN");
			accessToken = curs.getString(idColIndex);
		}

		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/users/me?access_token=").append(accessToken)
				.toString();
		ProfileItem profileItem = new ProfileItem();
		JSONObject data = new JSONObject(getJSONfromURL(url));
		JSONObject dataArr = data.getJSONObject("data");
		profileItem.setId(dataArr.getInt("id"));
		profileItem.setCountryId(dataArr.getInt("country_id"));
		profileItem.setCityId(dataArr.getInt("city_id"));
		profileItem.setEmail(dataArr.getString("email"));
		profileItem.setName(dataArr.getString("name"));

		profileItem.setPhotoUrl(dataArr.getString("photo"));
		profileItem.setCoins(dataArr.getLong("coins"));

		profileItem.setReady(dataArr.getBoolean("is_ready"));
		profileItem.setBlocked(dataArr.getBoolean("is_blocked"));
		return profileItem;
	}

	@Override
	public void setProfileItemLocalDB(long id, String name, long countryId, long cityId, String email, String photoUrl, boolean isReady, boolean isBlocked, long coins)
			throws BUtilsException {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_ID, id);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_NAME, name);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_COUNTRY_ID, countryId);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_CITY_ID, cityId);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_EMAIL, email);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_PHOTO_URL, photoUrl);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_IS_READY, isReady);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_IS_BLOCKED, isBlocked);
		contentValues.put(BETSTARS_PROFILE_TABLE_COL_COINS, coins);
		
		int rowsUpdated = getDb().update(
				BETSTARS_PROFILE_TABLE_NAME,
				contentValues,
				new StringBuffer(BETSTARS_PROFILE_TABLE_COL_ID).append("=")
						.append(id).toString(), null);
		if (rowsUpdated == 0) {
			getDb().insert(BETSTARS_PROFILE_TABLE_NAME, null, contentValues);
		}
	}

	@Override
	public ProfileItem getProfileItemFromLocalDB(long id)
			throws BUtilsException {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { 
					BETSTARS_PROFILE_TABLE_COL_NAME,
					BETSTARS_PROFILE_TABLE_COL_COUNTRY_ID,
					BETSTARS_PROFILE_TABLE_COL_CITY_ID,
					BETSTARS_PROFILE_TABLE_COL_ID,
					BETSTARS_PROFILE_TABLE_COL_EMAIL,
					BETSTARS_PROFILE_TABLE_COL_PHOTO_URL,
					BETSTARS_PROFILE_TABLE_COL_IS_READY,
					BETSTARS_PROFILE_TABLE_COL_IS_BLOCKED,
					BETSTARS_PROFILE_TABLE_COL_COINS};
			cursor = getDb().query(
					BETSTARS_PROFILE_TABLE_NAME,
					columns,
					new StringBuffer(BETSTARS_PROFILE_TABLE_COL_ID)
							.append(" = ").append(id).toString(), null, null,
					null, null);
			if (cursor.moveToFirst())
				return new ProfileItem(cursor.getString(0), cursor.getLong(1), cursor.getLong(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), Boolean.valueOf(cursor.getString(6)), Boolean.valueOf(cursor.getString(7)), cursor.getLong(8));
			throw new BUtilsException(-1, "Profile not found");
		} finally {
			cursor.close();
		}
	}

	@Override
	public List<ProfileItem> getProfileListFromLocalDB()
			throws BUtilsException {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_PROFILE_TABLE_COL_NAME,
					BETSTARS_PROFILE_TABLE_COL_COUNTRY_ID,
					BETSTARS_PROFILE_TABLE_COL_CITY_ID,
					BETSTARS_PROFILE_TABLE_COL_ID,
					BETSTARS_PROFILE_TABLE_COL_EMAIL,
					BETSTARS_PROFILE_TABLE_COL_PHOTO_URL,
					BETSTARS_PROFILE_TABLE_COL_IS_READY,
					BETSTARS_PROFILE_TABLE_COL_IS_BLOCKED,
					BETSTARS_PROFILE_TABLE_COL_COINS };
			cursor = getDb().query(BETSTARS_PROFILE_TABLE_NAME, columns, null,
					null, null, null, null);
			List<ProfileItem> result = new ArrayList<ProfileItem>();
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					result.add(new ProfileItem(cursor.getString(0), cursor.getLong(1), cursor.getLong(2), cursor.getInt(3), cursor.getString(4), cursor.getString(5), Boolean.valueOf(cursor.getString(6)), Boolean.valueOf(cursor.getString(7)), cursor.getLong(8)));
					cursor.moveToNext();
				}
			}
			return result;
		} finally {
			cursor.close();
		}
	}

	@Override
	public void putMessageToLocalDB(long id, long profileId, String messageText,
			long tournamentId, String timestamp) throws BUtilsException {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_ID, id);
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_PROFILE_ID, profileId);
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_TOURNAMENT_ID, tournamentId);
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_MSG_TIMESTMAP, timestamp);
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_MSG_TEXT, messageText);
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_IS_READ, 0);
		getDb().insert(BETSTARS_MESSAGES_TABLE_NAME, null, contentValues);
	}

	@Override
	public BMessageItem getMessageFromLocalDB(long id) throws BUtilsException, ParseException {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_MESSAGES_TABLE_COL_ID,
					BETSTARS_MESSAGES_TABLE_COL_PROFILE_ID,
					BETSTARS_MESSAGES_TABLE_COL_TOURNAMENT_ID,
					BETSTARS_MESSAGES_TABLE_COL_MSG_TIMESTMAP,
					BETSTARS_MESSAGES_TABLE_COL_MSG_TEXT,
					BETSTARS_MESSAGES_TABLE_COL_IS_READ};
			cursor = getDb().query(
					BETSTARS_MESSAGES_TABLE_NAME,
					columns,
					new StringBuffer(BETSTARS_MESSAGES_TABLE_COL_ID)
							.append(" = ").append(id).toString(), null, null,
					null, null);
			if (!cursor.moveToFirst()){
				BMessageItem item = new BMessageItem(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(4), new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(cursor.getString(3)));
				item.setRead(cursor.getInt(4)==1);
				return item;
			}
			throw new BUtilsException(-1, "Message not found");
		} finally {
			cursor.close();
		}
	}

	@Override
	public List<BMessageItem> getMessagesFromLocalDB(long tournamentId) throws BUtilsException, ParseException {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_MESSAGES_TABLE_COL_ID,
					BETSTARS_MESSAGES_TABLE_COL_PROFILE_ID,
					BETSTARS_MESSAGES_TABLE_COL_TOURNAMENT_ID,
					BETSTARS_MESSAGES_TABLE_COL_MSG_TIMESTMAP,
					BETSTARS_MESSAGES_TABLE_COL_MSG_TEXT,
					BETSTARS_MESSAGES_TABLE_COL_IS_READ};
			cursor = getDb().query(BETSTARS_MESSAGES_TABLE_NAME, columns, new StringBuffer(BETSTARS_MESSAGES_TABLE_COL_TOURNAMENT_ID)
			.append(" = ").append(tournamentId).toString(),
					null, null, null, null);
			List<BMessageItem> result = new ArrayList<BMessageItem>();
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					BMessageItem item = new BMessageItem(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(4), new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse(cursor.getString(3)));
					item.setRead(cursor.getInt(5)==1);
					item.setProfileItem(getProfileItemFromLocalDB(item.getUserId()));
					result.add(item);
					cursor.moveToNext();
				}
			}
			return result;
		} finally {
			cursor.close();
		}
	}

	@Override
	public void deleteAllMessagesFromLocalDB() throws BUtilsException {
		getDb().delete(BETSTARS_MESSAGES_TABLE_NAME, null, null);
	}

	@Override
	public void deleteAllProfilesFromLocalDB() throws BUtilsException {
		getDb().delete(BETSTARS_PROFILE_TABLE_NAME, null, null);
	}

	@Override
	public String createTournament(long maxPlayers, long numberOfRounds,
			long entranceFee) throws BUtilsException, JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments/my").append("?access_token=")
				.append(token).toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("max_players", String.valueOf(maxPlayers));
		params.put("number_of_rounds", String.valueOf(numberOfRounds));
		params.put("entrance_fee", String.valueOf(entranceFee));
		String response = doPost(url, params);
		JSONObject data = new JSONObject(response);
		JSONObject dataArr = data.getJSONObject("data");
		return dataArr.getString("id");
	}

	// public ArrayList<CountryItem> getCountries() throws BUtilsException,
	// JSONException {
	// String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL).append(
	// "/data/countries").toString();
	// ArrayList<CountryItem> countryList = new ArrayList<CountryItem>();
	// JSONObject data = new JSONObject(getJSONfromURL(url));
	// JSONArray dataArr = data.getJSONArray("data");
	// for (int i = 0; i < dataArr.length(); i++) {
	// CountryItem country = new CountryItem();
	// JSONObject jsonCountry = dataArr.getJSONObject(i);
	// country.setId(jsonCountry.getLong("id"));
	// country.setTitle(jsonCountry.getString("title"));
	// countryList.add(country);
	// }
	// return countryList;
	// }

	@Override
	public List<BTournamentItem> getTournamentList(String maxPlayers,
			String ropundCount, String entanceFee) throws BUtilsException {
		String token = getDBAccessToken(getWritableDatabase());
		List<BTournamentItem> result = new ArrayList<BTournamentItem>();
		StringBuffer url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments").append("?access_token=").append(token);
		if (maxPlayers != null && maxPlayers.length() != 0)
			url.append("&max_players=").append(maxPlayers);
		if (ropundCount != null && ropundCount.length() != 0)
			url.append("&number_of_rounds=").append(ropundCount);
		if (entanceFee != null && entanceFee.length() != 0)
			url.append("&entrance_fee=").append(entanceFee);
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JSONObject data = new JSONObject(getJSONfromURL(url.toString()));
			JSONArray dataArr = data.getJSONArray("data");
			for (int i = 0; i < dataArr.length(); i++) {
				BTournamentItem tournament = new BTournamentItem();
				JSONObject jsonTournament = dataArr.getJSONObject(i);
				tournament.setId(jsonTournament.getLong("id"));
				tournament.setType(jsonTournament.getString("type"));
				tournament.setMaxPlayers(jsonTournament.getInt("max_players"));
				tournament.setStartAt(sdf.parse(jsonTournament
						.getString("start_at")));
				tournament.setCurrentPlayers(jsonTournament
						.getInt("current_players"));
				tournament.setNumberOfRounds(jsonTournament
						.getInt("number_of_rounds"));
				tournament
						.setEntranceFee(jsonTournament.getInt("entrance_fee"));
				tournament
						.setRewardFirst(jsonTournament.getInt("reward_first"));
				tournament.setRewardSecond(jsonTournament
						.getInt("reward_second"));
				tournament.setRewardSecond(jsonTournament
						.getInt("reward_third"));
				
				JSONObject jsonObject = jsonTournament.getJSONObject("users");
				JSONArray userArr = jsonObject.getJSONArray("data");
				for (int j=0; j<userArr.length(); j++){
					JSONObject userInfoJsonObject = userArr.getJSONObject(j);
					BAccountInfo accountInfo = new BAccountInfo(userInfoJsonObject.getLong("id"), userInfoJsonObject.getLong("country_id"), userInfoJsonObject.getLong("city_id"), userInfoJsonObject.getString("email"), userInfoJsonObject.getString("name"), userInfoJsonObject.getString("photo"), userInfoJsonObject.getLong("coins"), Boolean.valueOf(userInfoJsonObject.getString("is_ready")) , Boolean.valueOf(userInfoJsonObject.getString("is_blocked")));
					tournament.getAccountInfoList().add(accountInfo);
				}
				
				result.add(tournament);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<ProfileItem> searchProfile(String lopginNameOrId)
			throws BUtilsException, JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		List<ProfileItem> result = new ArrayList<ProfileItem>();
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/users/search").append("?access_token=").append(token)
				.append("&q=").append(lopginNameOrId).toString();
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JSONObject data = new JSONObject(getJSONfromURL(url));
			JSONArray dataArr = data.getJSONArray("data");
			for (int i = 0; i < dataArr.length(); i++) {
				JSONObject jsonTournament = dataArr.getJSONObject(i);
				ProfileItem profileItem = new ProfileItem();
				profileItem.setId(jsonTournament.getInt("id"));
				profileItem.setCountryId(jsonTournament.getInt("country_id"));
				profileItem.setCityId(jsonTournament.getInt("city_id"));
				profileItem.setEmail(jsonTournament.getString("email"));
				profileItem.setName(jsonTournament.getString("name"));
				profileItem.setPhotoUrl(jsonTournament.getString("photo"));
				profileItem.setCoins(jsonTournament.getLong("coins"));
				profileItem.setReady(jsonTournament.getBoolean("is_ready"));
				profileItem.setBlocked(jsonTournament.getBoolean("is_blocked"));
				result.add(profileItem);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0,
				new Intent(SENT), 0);

		PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0,
				new Intent(DELIVERED), 0);

		// ---when the SMS has been sent---
		// registerReceiver(new BroadcastReceiver(){
		// @Override
		// public void onReceive(Context arg0, Intent arg1) {
		// switch (getResultCode())
		// {
		// case Activity.RESULT_OK:
		// Toast.makeText(getBaseContext(), "SMS sent",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
		// Toast.makeText(getBaseContext(), "Generic failure",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case SmsManager.RESULT_ERROR_NO_SERVICE:
		// Toast.makeText(getBaseContext(), "No service",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case SmsManager.RESULT_ERROR_NULL_PDU:
		// Toast.makeText(getBaseContext(), "Null PDU",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case SmsManager.RESULT_ERROR_RADIO_OFF:
		// Toast.makeText(getBaseContext(), "Radio off",
		// Toast.LENGTH_SHORT).show();
		// break;
		// }
		// }
		// }, new IntentFilter(SENT));

		// ---when the SMS has been delivered---
		// registerReceiver(new BroadcastReceiver(){
		// @Override
		// public void onReceive(Context arg0, Intent arg1) {
		// switch (getResultCode())
		// {
		// case Activity.RESULT_OK:
		// Toast.makeText(getBaseContext(), "SMS delivered",
		// Toast.LENGTH_SHORT).show();
		// break;
		// case Activity.RESULT_CANCELED:
		// Toast.makeText(getBaseContext(), "SMS not delivered",
		// Toast.LENGTH_SHORT).show();
		// break;
		// }
		// }
		// }, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(),
				MeasureSpec.AT_MOST);
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

	@Override
	public long transferCoins(String idNickname, long coins)
			throws BUtilsException, JSONException {
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/users/transfer?access_token=")
				.append(getDBAccessToken(getWritableDatabase())).toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("user_id", idNickname);
		params.put("coins", String.valueOf(coins));
		String response = doPost(url, params);
		ProfileItem pi = getProfile();
		return pi.getCoins();
	}

	@Override
	public List<ContactItem> getContactList() {
		Cursor cursor = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				new String[] { Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER },
				null, null, null);
		List<ContactItem> result = new ArrayList<ContactItem>();
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				result.add(new ContactItem(cursor.getString(0), cursor
						.getString(1), cursor.getString(2)));
			}
		}
		return result;
	}

	@Override
	public void inviteUser(String tournamentId, String receiverId)
			throws BUtilsException, JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/invites").append("?access_token=").append(token)
				.toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("tournament_id", tournamentId);
		params.put("receiver_id", receiverId);
		String response = doPost(url, params);
		System.out.println("response = " + response);
		// JSONObject data = new JSONObject(response);
		// JSONObject dataArr = data.getJSONObject("data");
	}

	@Override
	public List<RatingItem> getRating(int page) throws BUtilsException,
			JSONException {
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/rating?page=").append(page).append("&access_token=")
				.append(getDBAccessToken(getWritableDatabase())).toString();
		ArrayList<RatingItem> ratingList = new ArrayList<RatingItem>();
		// JSONObject data = new JSONObject(getJSONfromURL(url));
		JSONArray dataArr = new JSONArray(getJSONfromURL(url));
		for (int i = 0; i < dataArr.length(); i++) {
			RatingItem ratingItem = new RatingItem();
			JSONObject jsonRatingItem = dataArr.getJSONObject(i);
			ratingItem.setId(jsonRatingItem.getLong("id"));
			ratingItem.setCountryId(jsonRatingItem.getLong("country_id"));
			ratingItem.setCityId(jsonRatingItem.getLong("city_id"));
			ratingItem.setUserID(jsonRatingItem.getLong("user_id"));
			ratingItem.setTotalWins(jsonRatingItem.getLong("total_wins"));
			ratingItem.setTotalGames(jsonRatingItem.getLong("total_games"));
			ratingItem.setTotalScore(jsonRatingItem.getLong("total_score"));
			List<ProfileItem> profItemList = new ArrayList<ProfileItem>();
			profItemList = searchProfile(String.valueOf(ratingItem.getUserID()));
			ratingItem.setProfileItemList(profItemList);
			ratingList.add(ratingItem);
		}
		return ratingList;
	}

	private long getMaxValueId(String tableName, String columnName) {
		String query = new StringBuffer("SELECT MAX(").append(columnName)
				.append(") AS max_id FROM ").append(tableName).toString();
		Cursor cursor = getDb().rawQuery(query, null);

		int id = 0;
		if (cursor.moveToFirst()) {
			do {
				id = cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		return id;
	}

	@Override
	public List<BGamesItem> getEventListFromLocalDB() throws ParseException {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_EVENTS_ID_COL_NAME,
					BETSTARS_EVENTS_NAME_COL_NAME,
					BETSTARS_EVENTS_START_AT_COL_NAME,
					BETSTARS_EVENTS_CHAMPIONATSHIP_COL_NAME,
					BETSTARS_EVENTS_SPORT_COL_NAME,
					BETSTARS_EVENTS_START_AT_COL_NAME };
			cursor = getDb().query(BETSTARS_EVENTS_TABLE_NAME, columns, null,
					null, null, null, null);
			List<BGamesItem> result = new ArrayList<BGamesItem>();
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					result.add(new BGamesItem(cursor.getLong(0), cursor
							.getString(1), getBetBetList(cursor.getLong(0)),
							cursor.getLong(3), cursor.getLong(4),
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.parse(cursor.getString(5))));
					cursor.moveToNext();
				}
			}
			return result;
		} finally {
			cursor.close();
		}
	}

	private List<BOptionItem> getBetOptionList(long betId) {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_OPTIONS_KEY_COL_NAME,
					BETSTARS_OPTIONS_TITLE_COL_NAME,
					BETSTARS_OPTIONS_RATE_COL_NAME };
			cursor = getDb().query(
					BETSTARS_OPTIONS_TABLE_NAME,
					columns,
					new StringBuffer(BETSTARS_OPTIONS_BET_ID_COL_NAME)
							.append(" = ").append(betId).toString(), null,
					null, null, null);
			List<BOptionItem> result = new ArrayList<BOptionItem>();
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					result.add(new BOptionItem(cursor.getString(0), cursor
							.getString(1), Double.valueOf(cursor.getString(2))));
					cursor.moveToNext();
				}
			}
			return result;
		} finally {
			cursor.close();
		}
	}

	private List<BBetItem> getBetBetList(long gameId) {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_BETS_ID_COL_NAME,
					BETSTARS_BETS_KEY_COL_NAME };
			cursor = getDb().query(
					BETSTARS_BETS_TABLE_NAME,
					columns,
					new StringBuffer(BETSTARS_BETS_EVENT_ID_COL_NAME)
							.append(" = ").append(gameId).toString(), null,
					null, null, null);
			List<BBetItem> result = new ArrayList<BBetItem>();
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					result.add(new BBetItem(cursor.getString(1),
							getBetOptionList(cursor.getLong(0))));
					cursor.moveToNext();
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
		}
	}

	private long setBetItem(long id, String key, long gameId) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_BETS_KEY_COL_NAME, key);
		contentValues.put(BETSTARS_BETS_EVENT_ID_COL_NAME, gameId);

		if (id == ID_NOT_DEFINED) {
			getDb().insert(BETSTARS_BETS_TABLE_NAME, null, contentValues);
			return getMaxValueId(BETSTARS_BETS_TABLE_NAME,
					BETSTARS_BETS_ID_COL_NAME);
		} else {
			getDb().update(
					BETSTARS_BETS_TABLE_NAME,
					contentValues,
					new StringBuffer(BETSTARS_BETS_ID_COL_NAME).append("=")
							.append(id).toString(), null);
			return id;
		}
	}

	private long setBetOptionItem(long id, String key, String title,
			String rate, long betId) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_OPTIONS_KEY_COL_NAME, key);
		contentValues.put(BETSTARS_OPTIONS_TITLE_COL_NAME, title);
		contentValues.put(BETSTARS_OPTIONS_RATE_COL_NAME, rate);
		contentValues.put(BETSTARS_OPTIONS_BET_ID_COL_NAME, betId);

		int rowsUpdated = getDb().update(
				BETSTARS_OPTIONS_TABLE_NAME,
				contentValues,
				new StringBuffer(BETSTARS_BETS_ID_COL_NAME).append("=")
						.append(id).toString(), null);
		if (rowsUpdated == 0) {
			getDb().insert(BETSTARS_OPTIONS_TABLE_NAME, null, contentValues);
			return getMaxValueId(BETSTARS_OPTIONS_TABLE_NAME,
					BETSTARS_BETS_ID_COL_NAME);
		} else {
			return id;
		}

	}

	@Override
	public void setEventItem(BGamesItem gamesItem) throws BUtilsException {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_EVENTS_ID_COL_NAME, gamesItem.getId());
		contentValues.put(BETSTARS_EVENTS_NAME_COL_NAME, gamesItem.getName());
		try {
			contentValues.put(BETSTARS_EVENTS_START_AT_COL_NAME,
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(gamesItem.getStartAt()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		contentValues.put(BETSTARS_EVENTS_CHAMPIONATSHIP_COL_NAME,
				gamesItem.getChampionatshipId());
		contentValues.put(BETSTARS_EVENTS_SPORT_COL_NAME,
				gamesItem.getSportId());

		int rowsUpdated = getDb().update(
				BETSTARS_EVENTS_TABLE_NAME,
				contentValues,
				new StringBuffer(BETSTARS_EVENTS_ID_COL_NAME).append("=")
						.append(gamesItem.getId()).toString(), null);
		long id = gamesItem.getId();
		if (rowsUpdated == 0) {
			getDb().insert(BETSTARS_EVENTS_TABLE_NAME, null, contentValues);
			try {
				id = getMaxValueId(BETSTARS_EVENTS_TABLE_NAME,
						BETSTARS_EVENTS_ID_COL_NAME);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (BBetItem item : gamesItem.getBetList()) {
			long betId = setBetItem(ID_NOT_DEFINED, item.getKey(), id);
			for (BOptionItem optionItem : item.getOptionsList()) {
				setBetOptionItem(ID_NOT_DEFINED, optionItem.getKey(),
						optionItem.getTitle(),
						String.valueOf(optionItem.getRate()), betId);
			}
		}

	}

	@Override
	public void deleteEventItem(long id) throws BUtilsException {
		getDb().delete(
				BETSTARS_EVENTS_TABLE_NAME,
				new StringBuffer().append(BETSTARS_EVENTS_ID_COL_NAME)
						.append("=").append(id).toString(), null);
	}

	@Override
	public String joinTournament(String tournamentId) throws BUtilsException,
			JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments/").append(tournamentId)
				.append("/join?access_token=").append(token).toString();
		Map<String, String> params = new HashMap<String, String>();
		String response = doPost(url, params);
		if (response.length() > 3) {
			JSONObject data = new JSONObject(response);
			return data.getString("message");
		} else {
			return "";
		}
	}

	@Override
	public List<BSportItem> getSportItemList() throws BUtilsException,
			JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/data/sports?access_token=").append(token).toString();
		ArrayList<BSportItem> sportList = new ArrayList<BSportItem>();
		String response = getJSONfromURL(url);
		if (response != null && response.length() <= 3)
			return sportList;
		JSONObject data = new JSONObject(response);
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			JSONObject jsonCountry = dataArr.getJSONObject(i);
			BSportItem sportItem = new BSportItem(jsonCountry.getLong("id"),
					jsonCountry.getString("name"));
			sportList.add(sportItem);
		}
		return sportList;
	}

	@Override
	public List<BChampionatshipItem> getChampionatshipList()
			throws BUtilsException, JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/data/championships?access_token=").append(token)
				.toString();
		ArrayList<BChampionatshipItem> sportList = new ArrayList<BChampionatshipItem>();
		JSONObject data = new JSONObject(getJSONfromURL(url));
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			JSONObject jsonCountry = dataArr.getJSONObject(i);
			BChampionatshipItem sportItem = new BChampionatshipItem(
					jsonCountry.getLong("id"), jsonCountry.getString("name"));
			sportList.add(sportItem);
		}
		return sportList;
	}

	@Override
	public List<BGamesItem> getGamesList(long sportId, long championshipId)
			throws BUtilsException, JSONException, ParseException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/data/games?access_token=").append(token)
				.append("&sport_id=").append(sportId)
				.append("&championship_id=").append(championshipId).toString();
		ArrayList<BGamesItem> sportList = new ArrayList<BGamesItem>();
		String responce = getJSONfromURL(url);
		JSONObject data = new JSONObject(responce);
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			JSONObject jsonCountry = dataArr.getJSONObject(i);

			JSONArray betsArr = jsonCountry.getJSONArray("bets");
			List<BBetItem> betList = new ArrayList<BBetItem>();
			for (int j = 0; j < betsArr.length(); j++) {
				JSONObject jsonBet = betsArr.getJSONObject(j);
				BBetItem betItem = new BBetItem();
				betItem.setKey(jsonBet.getString("key"));
				JSONArray optionsArr = jsonBet.getJSONArray("options");
				List<BOptionItem> optionsList = new ArrayList<BOptionItem>();
				for (int x = 0; x < betsArr.length(); x++) {
					JSONObject optionsBet = optionsArr.getJSONObject(x);
					BOptionItem optionImpl = new BOptionItem(
							optionsBet.getString("key"),
							optionsBet.getString("title"),
							optionsBet.getDouble("rate"));
					optionsList.add(optionImpl);
				}
				betList.add(new BBetItem(jsonBet.getString("key"), optionsList));
			}

			BGamesItem sportItem = new BGamesItem(jsonCountry.getLong("id"),
					jsonCountry.getString("name"), betList,
					jsonCountry.getLong("championship_id"),
					jsonCountry.getLong("sport_id"), new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(jsonCountry
							.getString("start_at")));
			sportList.add(sportItem);
		}
		return sportList;
	}

	@Override
	public String makeBet(long tournamentId, long gameId, String key)
			throws BUtilsException, JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments/").append(tournamentId)
				.append("/bet?access_token=").append(token).toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("game_id", String.valueOf(gameId));
		params.put("key", key);
		String response = doPost(url, params);
		JSONObject data = new JSONObject(response);
		return data.getString("message");
	}

	@Override
	public void createMessage(long tournamentId, String messageText)
			throws BUtilsException, JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments/").append(tournamentId)
				.append("/chat?access_token=").append(token).toString();
		Map<String, String> params = new HashMap<String, String>();
		params.put("message", messageText);
		String response = doPost(url, params);
	}

	@Override
	public List<BMessageItem> getMessageList(long tournamentId) throws BUtilsException,
			JSONException, ParseException {
		String token = getDBAccessToken(getWritableDatabase());
		List<BMessageItem> result = new ArrayList<BMessageItem>();
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments/").append(tournamentId)
				.append("/chat?access_token=").append(token).toString();
		String response = getJSONfromURL(url);
		if (response!=null&&response.length()<=4)
			return result;
		JSONObject data = new JSONObject(response);
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			JSONObject jsonMessage = dataArr.getJSONObject(i);
//			BMessageItem messageItem = new BMessageItem(jsonMessage.getLong("id"), jsonMessage.getLong("user_id"), jsonMessage.getLong("tournament_id"), jsonMessage.getString("message"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonMessage.getString("created_at")));
			BMessageItem messageItem = new BMessageItem(jsonMessage.getLong("id"), 4, jsonMessage.getLong("tournament_id"), jsonMessage.getString("message"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonMessage.getString("created_at")));			
			ProfileItem profileItem = searchProfile(String.valueOf(messageItem.getUserId())).get(0);
			messageItem.setProfileItem(profileItem);
			result.add(messageItem);
		}
		return result;
	}
	

	@Override
	public List<BNewsItem> getNews(int page) throws BUtilsException,
			JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		String url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/news?access_token=").append(token)
				.append("&page=").append(page).toString();
		ArrayList<BNewsItem> news = new ArrayList<BNewsItem>();
		JSONObject data = new JSONObject(getJSONfromURL(url));
		JSONArray dataArr = data.getJSONArray("data");
		for (int i = 0; i < dataArr.length(); i++) {
			JSONObject jsonNews= dataArr.getJSONObject(i);
			BNewsItem newsItem = new BNewsItem();
			newsItem.setPhotoUrl(jsonNews.getString("image"));
			newsItem.setNewsTitle(jsonNews.getString("title"));
			newsItem.setText(jsonNews.getString("text"));
			news.add(newsItem);
		}
		return news;
	}

	@Override
	public void markMessageAsread(long msgId) throws BUtilsException {
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_MESSAGES_TABLE_COL_IS_READ, 1);
		getDb().update(
				BETSTARS_MESSAGES_TABLE_NAME,
				contentValues,
				new StringBuffer(BETSTARS_PROFILE_TABLE_COL_ID).append("=")
						.append(msgId).toString(), null);
	}

	@Override
	public void addTournamentIdToCurrentList(long id) throws BUtilsException {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(BETSTARS_TOURNAMENT_ID_LIST_TABLE_TOURNAMENT_ID_COL_NAME, id);
		getDb().insert(BETSTARS_TOURNAMENT_ID_LIST_TABLE_NAME, null, contentValues);
		
	}

	@Override
	public List<Long> getCurrentTournamentIdList() throws BUtilsException {
		Cursor cursor = null;
		try {
			String[] columns = new String[] { BETSTARS_TOURNAMENT_ID_LIST_TABLE_ID_COL_NAME,
					BETSTARS_TOURNAMENT_ID_LIST_TABLE_TOURNAMENT_ID_COL_NAME };
			cursor = getDb().query(
					BETSTARS_TOURNAMENT_ID_LIST_TABLE_NAME,
					columns,
					null, null,
					null, null, null);
			List<Long> result = new ArrayList<Long>();
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {
					result.add(cursor.getLong(1));
					cursor.moveToNext();
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			cursor.close();
		}
	}

	@Override
	public BTournamentItem getTournamentInfo(long id) throws BUtilsException,
			JSONException {
		String token = getDBAccessToken(getWritableDatabase());
		BTournamentItem result = null;
		StringBuffer url = new StringBuffer(BETSARS_SERVER_URL_NON_SSL)
				.append("/tournaments/").append(id).append("?access_token=").append(token);
		Map<String, String> params = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			JSONObject data = new JSONObject(getJSONfromURL(url.toString()));
			BTournamentItem tournament = new BTournamentItem();
			JSONObject jsonTournament = data.getJSONObject("data");
			tournament.setId(jsonTournament.getLong("id"));
			tournament.setType(jsonTournament.getString("type"));
			tournament.setMaxPlayers(jsonTournament.getInt("max_players"));
			tournament.setStartAt(sdf.parse(jsonTournament
					.getString("start_at")));
			tournament.setCurrentPlayers(jsonTournament
					.getInt("current_players"));
			tournament.setNumberOfRounds(jsonTournament
					.getInt("number_of_rounds"));
			tournament
					.setEntranceFee(jsonTournament.getInt("entrance_fee"));
			tournament
					.setRewardFirst(jsonTournament.getInt("reward_first"));
			tournament.setRewardSecond(jsonTournament
					.getInt("reward_second"));
			tournament.setRewardSecond(jsonTournament
					.getInt("reward_third"));
			
			JSONObject jsonObject = jsonTournament.getJSONObject("users");
			JSONArray userArr = jsonObject.getJSONArray("data");
			for (int j=0; j<userArr.length(); j++){
				JSONObject userInfoJsonObject = userArr.getJSONObject(j);
				BAccountInfo accountInfo = new BAccountInfo(userInfoJsonObject.getLong("id"), userInfoJsonObject.getLong("country_id"), userInfoJsonObject.getLong("city_id"), userInfoJsonObject.getString("email"), userInfoJsonObject.getString("name"), userInfoJsonObject.getString("photo"), userInfoJsonObject.getLong("coins"), Boolean.valueOf(userInfoJsonObject.getString("is_ready")) , Boolean.valueOf(userInfoJsonObject.getString("is_blocked")));
				tournament.getAccountInfoList().add(accountInfo);
			}
			
			result = tournament;
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
}
