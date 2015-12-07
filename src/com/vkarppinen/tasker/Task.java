package com.vkarppinen.tasker;

import java.util.Date;
import java.util.UUID;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple task class.
 * @author Valtteri Karppinen, 1.0, 2.12.2015
 */

public class Task implements Parcelable {
	private UUID id;
	private String name;
	private Date dateDue;
	
	
	/*
	 * Default constructor.
	 */
	public Task(String name, Date dateDue) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.dateDue = dateDue;
	}
	
	/*
	 * Alternative constructor
	 */
	public Task(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.dateDue = new Date();
	}

	/*
	 * Getters
	 */
	public UUID getID() { 
		return this.id;
	}
	protected String getName() {
		return this.name;
	}
	protected Date getDateDue() {
		return this.dateDue;
	}

	/*
	 * Setters
	 */
	protected void setName(String name) {
		// TODO muuta toimimaan
	}
	protected void setDateDue(Object dateDue) {
		// TODO muuta toimimaan
	}

	
	/*
	 * Custom methods
	 */
	public String toString() {	
		return this.getName();
	}
	
	/*
	 * Parcelable methods 
	 */
	
    
	private Task(Parcel in) {
        this.name = in.readString();
        this.id = UUID.fromString(in.readString());
        this.dateDue = new Date(); //TODO Tämän pitäisi toimia siten, että kun datepickkeristä tulee data sen perusteella pvm.
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(id.toString());
        out.writeString(dateDue.toString());
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