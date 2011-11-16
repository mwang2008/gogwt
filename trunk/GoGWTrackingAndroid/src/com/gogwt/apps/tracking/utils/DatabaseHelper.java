package com.gogwt.apps.tracking.utils;

import com.gogwt.apps.tracking.activities.LocationTrackingActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DatabaseHelper
 * <pre>
 * Broswer db:
 * step 1: 
 * C:\MyProgs\android-sdk-windows-32\platform-tools>adb -s emulator-5554 shell then
 * use linux command ls cd /data/data cd com.androidexample.example or 
 * 
 * or
 * cd /data/data/com.gogwt.apps.tracking/databases
 * 
 * step 2: accesss database of gogps.sqlite
 * # sqlite3 gogwt.sqlite 
 * 
 * step 3. show tables for the database 
 * sqlite> .tables 
 * 
 * step 4. select statement
 * sqlite> select * from gogwt_location
 * 
 * step 5. show table layout
 * PRAGMA table_info(gogwt_location);
 * </pre>
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	protected static final String TAG = DatabaseHelper.class.getSimpleName();
	
	private static final String DATABASE_NAME = "gogwt.sqlite";
	public static final int DATABASE_VERSION = 2;
	public static final String LOCATION_TBL_NAME = "gogwt_location";
	
	public static final String CREATE_LOCATION_TBL = "" +
			"create table " + LOCATION_TBL_NAME + " (" +
			      "id integer primary key autoincrement, " +
			      "groupId CHAR(30), " +
			      "latitude int NOT NULL, " +
			      "longitude int NOT NULL," +
			      "altitude NUMERIC NOT NULL, " +
			      "provider CHAR(25), " +
			      "accuracy NUMERIC NOT NULL, " +
			      "bearing NUMERIC NOT NULL, " +
			      "distance NUMERIC NOT NULL, " +
			      "speed NUMERIC NOT NULL, " +
			      "time int NOT NULL," +
			      "startTime int NOT NULL," +
			      "totalDistance NUMERIC NOT NULL" + ");";
 	
	public static final String DELETE_LOCATION_TBL = "drop table " + LOCATION_TBL_NAME +";";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.i(TAG, "==== Start Create Database of gogwt.sqlite");
	}

	/**
	 * Method is called during creation of the database
	 */
	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.i(TAG, "==== Start Create table " + LOCATION_TBL_NAME);
		try {
		  database.execSQL(CREATE_LOCATION_TBL);		  
		}
		catch (Throwable e) {
			Log.e(TAG, "==== Start Create table Error " + LOCATION_TBL_NAME, e);
			e.printStackTrace();
		}
	}

	/**
	 * Method is called during an upgrade of the database, e.g. if you increase the database version
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,	int newVersion) {
		Log.w(DatabaseHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");
		database.execSQL(DELETE_LOCATION_TBL);
		onCreate(database);
	}
}