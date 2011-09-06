package com.gogwt.apps.tracking.dataaccess.dao;

import static com.gogwt.apps.tracking.utils.DatabaseHelper.LOCATION_TBL_NAME;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gogwt.apps.tracking.data.GLocation;
import com.gogwt.apps.tracking.data.GPXPoint;
import com.gogwt.apps.tracking.utils.DatabaseHelper;

public class LocationDAO {

	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper databaseHelper;
	private String KEY_ROWID = "id";
		
    private static LocationDAO instance;
	
	public LocationDAO(Context context) {
		this.context = context; 
	}
	
	public LocationDAO open() throws SQLException {
		databaseHelper = new DatabaseHelper(context);
		database = databaseHelper.getWritableDatabase();

		return this;
	}

	public void close() {
		databaseHelper.close();
	}

	/**
	 * 
	 * Create a new todo If the todo is successfully created return the new
	 * 
	 * rowId for that note, otherwise return a -1 to indicate failure.
	 */
 	
	public long insertLocation(GLocation point) {
		ContentValues values = new ContentValues();
 		
		values.put("groupId", point.groupId);
		values.put("latitude", point.latitude);
		values.put("longitude", point.longitude);
		values.put("altitude", point.altitude);
		values.put("accuracy", point.accuracy);
		values.put("bearing", point.bearing);
		values.put("distance", point.distance);
		values.put("speed", point.speed);
		values.put("time", point.time);
		values.put("startTime", point.startTime);
		values.put("totalDistance", point.totalDistance);
		
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		//values.put("createdtime", dateFormat.format(point.timestamp));
 		return database.insert(LOCATION_TBL_NAME, null, values);
	}
 	 

	/**
	 * 
	 * Update 
	 */

	public boolean updateLocation(GPXPoint point) {
		 //todo: later
		return false;
	}

	/**
	 * 
	 * Deletes todo
	 */

	public boolean deleteLocation(long rowId) {
		return database.delete(LOCATION_TBL_NAME, KEY_ROWID + "=" + rowId, null) > 0;
	}

	/**
	 * 
	 * Return a Cursor over the list of all todo in the database
	 * 
	 * Both working fine
	 * 	Cursor cursor = database.rawQuery("SELECT latitude, longitude, altitude, provider, createdtime FROM " + LOCATION_TBL_NAME, null); 
		Cursor cursor = database.query(LOCATION_TBL_NAME, new String[] {"id", "latitude", "longitude", "altitude", "provider", "createdtime"  }, null, null, null, null, null);
	
	 * 
	 * @return Cursor over all notes
	 */

	public List<GLocation> fetchAllLocation() {
		//Cursor cursor = database.rawQuery("SELECT id, latitude, longitude, altitude, provider, createdtime FROM " + LOCATION_TBL_NAME, null); 
		Cursor cursor = database.query(LOCATION_TBL_NAME, new String[] {"id", "latitude", "longitude", "altitude", "provider", "createdtime"  }, null, null, null, null, null);
		 
		ArrayList<GLocation> al = new ArrayList<GLocation>();
	 
		cursor.moveToFirst();
		
		GLocation gLocation;
		while (cursor.isAfterLast() == false) {
			gLocation = new GLocation();
			
			int latitudeCol = cursor.getColumnIndex("latitude");
			int longitudeCol = cursor.getColumnIndex("longitude");
			
			Log.i("DBUtils", " ==== latitudeCol="+latitudeCol +", longitudeCol="+longitudeCol);
			
			gLocation.setProvider(cursor.getString(cursor.getColumnIndex("groupId")));
			gLocation.setLatitude(cursor.getInt(latitudeCol));
			gLocation.setLongitude(cursor.getInt(longitudeCol));
			gLocation.setAltitude(cursor.getDouble(cursor.getColumnIndex("altitude")));
			gLocation.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
			gLocation.setAccuracy(cursor.getDouble(cursor.getColumnIndex("accuracy")));
			gLocation.setBearing(cursor.getDouble(cursor.getColumnIndex("bearing"))); 
			gLocation.setDistance(cursor.getDouble(cursor.getColumnIndex("distance"))); 
			gLocation.setSpeed(cursor.getLong(cursor.getColumnIndex("speed")));
			gLocation.setTime(cursor.getLong(cursor.getColumnIndex("time")));
			gLocation.setStartTime(cursor.getLong(cursor.getColumnIndex("startTime")));
			gLocation.setTotalDistance(cursor.getDouble(cursor.getColumnIndex("totalDistance")));
	 		 
			al.add(gLocation);
			
			cursor.moveToNext();
		}
		cursor.close();	 
		
		return al;

	}

	/**
	 * 
	 * Return a Cursor positioned at the defined todo
	 */

	public Cursor fetch(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, LOCATION_TBL_NAME, new String[] {
		KEY_ROWID, "latitude", "longitude", "altitude", "provider", "createdtime" },
		KEY_ROWID + "=" + rowId, null, null, null, null, null);

		if (mCursor != null) {
			mCursor.moveToFirst();
		}

		return mCursor;

	}

	public static LocationDAO getInstance(Context context) {
		if (instance == null) {
			instance = new LocationDAO(context);
			instance.open();
		}
		
		return instance;
	}
}
