package com.vkarppinen.tasker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Valtteri Karppinen, 1.0, 2.1.2016
 */

public class Task implements Parcelable {
	private UUID id;
	private String name;
	private Date dateDue;
	private float alarmId;
	private PendingIntent alarmIntent;

	/*
	 * Default constructor.
	 */
	public Task(String name, String date, String time) {
    	this.id = UUID.randomUUID();
		this.name = name;
		
		String dateInString = date + " " + time;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
		try {
			this.dateDue = formatter.parse(dateInString);
		} catch (ParseException e) {
			System.err.println("Error parsing task data");
		}
	}
	
	/*
	 * Create a new task that comes only from DB.
	 */
	public Task(String id, String name, String date, boolean fromdatabase) {
		this.id = UUID.fromString(id);
		this.name = name;
		
		SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.ENGLISH);
		try {
			this.dateDue = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	
	public UUID getID() { 
		return this.id;
	}
	protected String getName() {
		return this.name;
	}
	protected Date getDateDue() {
		return this.dateDue;
	}
	public float getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(float alarmId) {
		this.alarmId = alarmId;
	}
	public PendingIntent getAlarmIntent() {
		return alarmIntent;
	}

	public void setAlarmIntent(PendingIntent alarmIntent) {
		this.alarmIntent = alarmIntent;
	}
	public String toString() {	
		return this.getName();
	}

	
	
	/** Parcelable methods **/
	
	private Task(Parcel in) {
        this.name = in.readString();
        this.id = UUID.fromString(in.readString());
        this.dateDue = new Date(in.readLong());

    }

	public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(id.toString());
        out.writeLong(dateDue.getTime());
    }

    public int describeContents() {
        return 0;
    }

	public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

}