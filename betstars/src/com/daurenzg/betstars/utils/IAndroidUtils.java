package com.daurenzg.betstars.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.widget.ListView;

import com.daurenzg.betstars.wao.AuthItem;
import com.daurenzg.betstars.wao.CityItem;
import com.daurenzg.betstars.wao.CountryItem;
import com.daurenzg.betstars.wao.ProfileItem;
import com.daurenzg.betstars.wao.RatingItem;

public interface IAndroidUtils {

	public final String OUTPU_DATE_FORMAT_PATTERN = "yyyy.MM.dd HH:mm";
	
	public static final int ID_NOT_DEFINED = -111;
	
	public final String SERVER = "betstars.o9.kz/api/v1";
	public final String SERVER_HTTP_ROOT = "betstars.o9.kz";
//	public final String SERVER = "10.241.8.27/api/v1";
	public final String BETSARS_SERVER_URL_NON_SSL = new StringBuffer("http://").append(SERVER).toString();
	public final String BETSARS_SERVER_URL_ROOT = new StringBuffer("http://").append(SERVER_HTTP_ROOT).toString();
	public static final int MSG_TYPE_IN = 0;
	public static final int MSG_TYPE_OUT = 1;
	
	public ArrayList<CountryItem> getCountries() throws BUtilsException, JSONException;
	public ArrayList<CityItem> getCities(long countryId) throws BUtilsException, JSONException;
	public AuthItem registerNewUSer(String email, String pswd) throws BUtilsException, JSONException;
	public AuthItem auth(String email, String pswd) throws BUtilsException, JSONException;
	public ProfileItem getProfile() throws BUtilsException, JSONException;
	public long transferCoins(String idNickname, long coins) throws BUtilsException, JSONException;
	public List<RatingItem> getRating(int page) throws BUtilsException, JSONException;
	public List<BNewsItem> getNews(int page) throws BUtilsException, JSONException;
	//public 
	
	// Roma
	public void setProfileItemLocalDB(long id, String name, long countryId, long cityId, String email, String photoUrl, boolean isReady, boolean isBlocked, long coins) throws BUtilsException;
	public ProfileItem getProfileItemFromLocalDB(long id) throws BUtilsException;
	public List<ProfileItem> getProfileListFromLocalDB() throws BUtilsException;
	public void putMessageToLocalDB(long id, long profileId, String messageText, long tournamentId, String timestamp) throws BUtilsException;
	public void createMessage(long tournamentId, String messageText)  throws BUtilsException, JSONException;
	public List<BMessageItem> getMessageList(long tournamentId)  throws BUtilsException, JSONException, ParseException;
	public BMessageItem getMessageFromLocalDB(long id) throws BUtilsException, ParseException;
	public void markMessageAsread(long msgId) throws BUtilsException;
	public List<BMessageItem> getMessagesFromLocalDB(long tournamentId) throws BUtilsException, ParseException;
	public void deleteAllMessagesFromLocalDB() throws BUtilsException;
	public void deleteAllProfilesFromLocalDB() throws BUtilsException;
	public String createTournament(long maxPlayers,long numberOfRounds, long entranceFee) throws BUtilsException, JSONException;
	public List<BTournamentItem> getTournamentList(String maxPlayers, String ropundCount, String entanceFee) throws BUtilsException;
	public List<ProfileItem> searchProfile(String lopginNameOrId) throws BUtilsException, JSONException;
	public void setListViewHeightBasedOnChildren(ListView listView);
	public List<ContactItem> getContactList();
	public void sendSMS(String phoneNumber, String message);
	public void inviteUser(String tournamentId, String receiverId) throws BUtilsException, JSONException;
	public List<BGamesItem> getEventListFromLocalDB() throws ParseException;
	public void setEventItem(BGamesItem gamesItem) throws BUtilsException;
	public void deleteEventItem(long id) throws BUtilsException;
	public String joinTournament(String tournamentId) throws BUtilsException, JSONException;
	public List<BSportItem> getSportItemList() throws BUtilsException, JSONException;
	public List<BChampionatshipItem> getChampionatshipList() throws BUtilsException, JSONException;
	public List<BGamesItem> getGamesList(long sportId, long championshipId) throws BUtilsException, JSONException, ParseException;
	public String makeBet(long tournamentId, long gameId, String key) throws BUtilsException, JSONException;
	public void addTournamentIdToCurrentList(long id) throws BUtilsException;
	public List<Long> getCurrentTournamentIdList() throws BUtilsException;
	public BTournamentItem getTournamentInfo(long id) throws BUtilsException, JSONException;
}