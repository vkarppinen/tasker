package com.vkarppinen.tasker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple task class.
 * @author Valtteri Karppinen, 1.0, 2.12.2015
 */

public class Task implements Parcelable {
	private int id;
	private String name;
	
	public Task(String name) {
		
		// TODO this.id = CreateID();
		this.name = name;
	}
	
	public int getID() { 
		return this.id;
	}
	
	public String toString() {
		
		return this.getName();
	}

	protected String getName() {
		return this.name;
	}

	
	/*
	 * Parcelable methods 
	 */
	
    private Task(Parcel in) {
        this.name = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
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
