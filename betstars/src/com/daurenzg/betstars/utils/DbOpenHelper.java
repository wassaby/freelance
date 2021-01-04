package com.daurenzg.betstars.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "betstars";
    public static final String BETSTARS_SETTINGS_TABLE_NAME = "BETSTARS_SETTINGS";
    public static final String ID = "ID";
	public static final String LOGIN = "LOGIN";
	public static final String PASSWORD = "PASSWORD";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	public static final String ACCESS_TOKEN_EXPIRES_IN = "ACCESS_TOKEN_EXPIRES_IN";
	public static final String ACCESS_TOKEN_TYPE = "ACCESS_TOKEN_TYPE";
	public static final String APP_LANG = "APP_LANG";
	public static final String COINS = "COINS";
	
	public static final String BETSTARS_EVENTS_TABLE_NAME = "BETSTARS_EVENTS_TABLE";
	public static final String BETSTARS_EVENTS_ID_COL_NAME= "_id";
	public static final String BETSTARS_EVENTS_NAME_COL_NAME= "name";
	public static final String BETSTARS_EVENTS_START_AT_COL_NAME= "START_AT";
	public static final String BETSTARS_EVENTS_CHAMPIONATSHIP_COL_NAME= "CHAMPIONATSHIP_ID";
	public static final String BETSTARS_EVENTS_SPORT_COL_NAME= "SPORT_ID";
	
	public static final String BETSTARS_BETS_TABLE_NAME = "BETSTARS_BETS_TABLE";
	public static final String BETSTARS_BETS_ID_COL_NAME = "_id";
	public static final String BETSTARS_BETS_KEY_COL_NAME = "BETSTARS_BETS_KEY_COL_NAME";
	public static final String BETSTARS_BETS_EVENT_ID_COL_NAME = "event_id";

	public static final String BETSTARS_OPTIONS_TABLE_NAME = "BETSTARS_OPTIONS_TABLE";
	public static final String BETSTARS_OPTIONS_ID_COL_NAME = "_id";
	public static final String BETSTARS_OPTIONS_KEY_COL_NAME = "BETSTARS_OPTIONS_KEY_COL_NAME";
	public static final String BETSTARS_OPTIONS_BET_ID_COL_NAME = "BET_ID";
	public static final String BETSTARS_OPTIONS_TITLE_COL_NAME = "BETSTARS_OPTIONS_TITLE_COL_NAME";
	public static final String BETSTARS_OPTIONS_RATE_COL_NAME = "BETSTARS_OPTIONS_RATE_COL_NAME";


	public static final String BETSTARS_PROFILE_TABLE_NAME = "BETSTARS_PROFILE";
	public static final String BETSTARS_PROFILE_TABLE_COL_ID = "_id";
	public static final String BETSTARS_PROFILE_TABLE_COL_COUNTRY_ID = "country_id";
	public static final String BETSTARS_PROFILE_TABLE_COL_CITY_ID = "city_id";
	public static final String BETSTARS_PROFILE_TABLE_COL_EMAIL = "email";
	public static final String BETSTARS_PROFILE_TABLE_COL_NAME = "name";
	public static final String BETSTARS_PROFILE_TABLE_COL_PHOTO_URL = "photo_url";
	public static final String BETSTARS_PROFILE_TABLE_COL_IS_READY = "is_ready";
	public static final String BETSTARS_PROFILE_TABLE_COL_IS_BLOCKED = "is_blocked";
	public static final String BETSTARS_PROFILE_TABLE_COL_COINS = "coins";
	

	public static final String BETSTARS_MESSAGES_TABLE_NAME = "BETSTARS_MESSAGES";
	public static final String BETSTARS_MESSAGES_TABLE_COL_ID = "_id";
	public static final String BETSTARS_MESSAGES_TABLE_COL_PROFILE_ID = "profile_id";
	public static final String BETSTARS_MESSAGES_TABLE_COL_TOURNAMENT_ID = "tournament_id";
	public static final String BETSTARS_MESSAGES_TABLE_COL_MSG_TEXT = "msg_text";
	public static final String BETSTARS_MESSAGES_TABLE_COL_MSG_TIMESTMAP = "msg_timestamp";
	public static final String BETSTARS_MESSAGES_TABLE_COL_IS_READ = "is_read";
	
	public static final String BETSTARS_TOURNAMENT_ID_LIST_TABLE_NAME = "TOURNAMENT_ID_LIST";
	public static final String BETSTARS_TOURNAMENT_ID_LIST_TABLE_ID_COL_NAME = "_id";
	public static final String BETSTARS_TOURNAMENT_ID_LIST_TABLE_TOURNAMENT_ID_COL_NAME = "tournament_id";

	private static final String CREATE_EVENT_LIST_TABLE =
    		new StringBuffer("create table ").append(BETSTARS_EVENTS_TABLE_NAME).append(" ( ")
    		.append(BETSTARS_EVENTS_ID_COL_NAME).append(" integer primary key autoincrement, ")
    		.append(BETSTARS_EVENTS_NAME_COL_NAME).append(" TEXT, ")
    		.append(BETSTARS_EVENTS_START_AT_COL_NAME).append(" TEXT, ")
    		.append(BETSTARS_EVENTS_CHAMPIONATSHIP_COL_NAME).append(" integer, ")
    		.append(BETSTARS_EVENTS_SPORT_COL_NAME).append(" integer) ")
    		.toString();
	
	private static final String CREATE_TOURNAMENT_ID_LIST_TABLE =
    		new StringBuffer("create table ").append(BETSTARS_TOURNAMENT_ID_LIST_TABLE_NAME).append(" ( ")
    		.append(BETSTARS_TOURNAMENT_ID_LIST_TABLE_ID_COL_NAME).append(" integer primary key autoincrement, ")
    		.append(BETSTARS_TOURNAMENT_ID_LIST_TABLE_TOURNAMENT_ID_COL_NAME).append(" integer) ")
    		.toString();
	
	private static final String CREATE_BET_LIST_TABLE =
    		new StringBuffer("create table ").append(BETSTARS_BETS_TABLE_NAME).append(" ( ")
    		.append(BETSTARS_BETS_ID_COL_NAME).append(" integer primary key autoincrement, ")
    		.append(BETSTARS_BETS_EVENT_ID_COL_NAME).append(" integer, ")
    		.append(BETSTARS_BETS_KEY_COL_NAME).append(" TEXT) ")
    		.toString();
	
	private static final String CREATE_OPTIONS_LIST_TABLE =
    		new StringBuffer("create table ").append(BETSTARS_OPTIONS_TABLE_NAME).append(" ( ")
    		.append(BETSTARS_OPTIONS_ID_COL_NAME).append(" integer primary key autoincrement, ")
    		.append(BETSTARS_OPTIONS_KEY_COL_NAME).append(" TEXT, ")
    		.append(BETSTARS_OPTIONS_BET_ID_COL_NAME).append(" integer, ")
    		.append(BETSTARS_OPTIONS_TITLE_COL_NAME).append(" TEXT, ")
    		.append(BETSTARS_OPTIONS_RATE_COL_NAME).append(" TEXT)")
    		.toString();
	
	private static final String CREATE_VIO_SETTINGS_TABLE = 
    		new StringBuffer("create table ").append(BETSTARS_SETTINGS_TABLE_NAME).append(" ( ")
    		.append(ID).append(" integer primary key autoincrement, ")
    		.append(LOGIN).append(" TEXT, ")
    		.append(PASSWORD).append(" TEXT, ")
    		.append(ACCESS_TOKEN).append(" TEXT, ")
    		.append(ACCESS_TOKEN_EXPIRES_IN).append(" integer, ")
    		.append(ACCESS_TOKEN_TYPE).append(" TEXT, ")
    		.append(COINS).append(" integer, ")
    		.append(APP_LANG).append(" TEXT) ")
    		.toString();

    private static final String CREATE_BETSTARS_PROFILE_TABLE = 
    		new StringBuffer("create table ").append(BETSTARS_PROFILE_TABLE_NAME).append(" ( ")
    		.append(BETSTARS_PROFILE_TABLE_COL_ID).append(" integer primary key autoincrement, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_COUNTRY_ID).append(" integer, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_CITY_ID).append(" integer, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_EMAIL).append(" integer, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_NAME).append(" TEXT, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_PHOTO_URL).append(" TEXT, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_IS_READY).append(" TEXT, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_IS_BLOCKED).append(" TEXT, ")
    		.append(BETSTARS_PROFILE_TABLE_COL_COINS).append(" integer) ")
    		.toString();

    private static final String CREATE_BETSTARS_MESSAGES_TABLE = 
    		new StringBuffer("create table ").append(BETSTARS_MESSAGES_TABLE_NAME).append(" ( ")
    		.append(BETSTARS_MESSAGES_TABLE_COL_ID).append(" integer primary key autoincrement, ")
    		.append(BETSTARS_MESSAGES_TABLE_COL_PROFILE_ID).append(" integer, ")
    		.append(BETSTARS_MESSAGES_TABLE_COL_TOURNAMENT_ID).append(" integer, ")
    		.append(BETSTARS_MESSAGES_TABLE_COL_MSG_TEXT).append(" TEXT, ")
    		.append(BETSTARS_MESSAGES_TABLE_COL_MSG_TIMESTMAP).append(" TEXT, ")
    		.append(BETSTARS_MESSAGES_TABLE_COL_IS_READ).append(" TEXT) ")
    		.toString();

    private SQLiteDatabase db;

    public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
    	sqLiteDatabase.execSQL(CREATE_VIO_SETTINGS_TABLE);
    	sqLiteDatabase.execSQL(CREATE_BETSTARS_PROFILE_TABLE);
    	sqLiteDatabase.execSQL(CREATE_BETSTARS_MESSAGES_TABLE);
    	sqLiteDatabase.execSQL(CREATE_EVENT_LIST_TABLE);
    	sqLiteDatabase.execSQL(CREATE_BET_LIST_TABLE);
    	sqLiteDatabase.execSQL(CREATE_OPTIONS_LIST_TABLE);
    	sqLiteDatabase.execSQL(CREATE_TOURNAMENT_ID_LIST_TABLE);
    }

    @Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
    
}