package com.gogwt.apps.tracking.data;

import android.os.Parcel;
import android.os.Parcelable;

public final class GPXPoint implements Parcelable {

	public int latitude;
	public int longitude;
	public double altitude;
	public String provider;
	public double accuracy;
	public double bearing;
	public double distance;
	public double speed;
	public long time;
	public long startTime;
	public double totalDistance;
	
	public static final Parcelable.Creator<GPXPoint> CREATOR = new Parcelable.Creator<GPXPoint>() {

		public GPXPoint createFromParcel(Parcel src) {
			return new GPXPoint(src);
		}

		public GPXPoint[] newArray(int size) {
			return new GPXPoint[size];
		}
	};

	public GPXPoint() {
	}

	private GPXPoint(Parcel src) {
		readFromParcel(src);
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(latitude);
		dest.writeInt(longitude);
		dest.writeDouble(altitude);		
		dest.writeString(provider);
		dest.writeDouble(accuracy);
		dest.writeDouble(bearing);
		dest.writeDouble(distance);
		dest.writeDouble(speed);
		dest.writeLong(time);
		dest.writeLong(startTime);
		dest.writeDouble(totalDistance);
 	}
 	
	public void readFromParcel(Parcel src) {
		latitude = src.readInt();
		longitude = src.readInt();
		altitude = src.readDouble();
		provider = src.readString();
		accuracy = src.readDouble();
		bearing = src.readDouble();
		distance = src.readDouble();
		speed = src.readDouble();
		time = src.readLong();
	    startTime = src.readLong();
	    totalDistance = src.readDouble();
 	}

	@Override
	public int describeContents() {
		// nothing special
		return 0;
	}

}
