package com.vkarppinen.tasker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import com.vkarppinen.tasker.Task;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * Simple class for storing multiple elements and updating and deleting them.
 * Uses tasks in this context.
 * @author Valtteri Karppinen, 1.0, 2.12.2015 
 */

public class Tasks {
	
	private ArrayList<Task> tasklist;
	TaskListDB db;
	Context mContext;
	
	NotificationCompat.Builder builder;
	final AlarmManager am;
	private int numTasksDue = 0;
	
	public Tasks(Context context) {
		this.mContext = context;
		this.tasklist = new ArrayList<Task>();
		this.db = new TaskListDB(context);
		fetchTasks();
		
		this.am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
	}

	/**
	 * Return the model tasklist.
	 * @return tasklist
	 */
	public ArrayList<Task> getTasks() {
		return this.tasklist;
	}
	
	
	
	/* Task specific methods 
	 ***********************************************************************************************************************/
	
	/**
	 * Searches the model for specific task.
	 * @param id the id of the task that is searched.
	 * @return task or null
	 */
	public Task getTask(UUID id) {
		for (Task task : tasklist) {
			if (task.getID() == id) return task;
		}
		return null;
	}
	
	/**
	 * Adds a task to the model.
	 * @param task task that is added.
	 */
	public void add(Task task) {
		
		if (!tasklist.contains(task)) {
			this.tasklist.add(task);
     		setAlarm(task);
		}
	}
	
	/**
	 * Deletes a specific task from the model.
	 * @param id id of the task that is to be deleted.
	 */
	public void delete(UUID id) {
		
		int indexToDelete = 0;
		Task t = null;
		
		for (Task task : tasklist) {
			if (task.getID() == id) {
				indexToDelete = tasklist.indexOf(task);
				t = task;
			}
		}
		
		if (indexToDelete != -1)	{
			tasklist.remove(indexToDelete);
			deleteFromDB(t);
		}
		
		deleteAlarm(t);
	}
	
	
	
	/* Methods for database handling 
	 ***********************************************************************************************************************/
	private void fetchTasks() {
		db.getRows(tasklist);		
	}
	protected void addToDB(Task task) {
		db.insert(task);
	}
	private void deleteFromDB (Task task) {
		db.deleteRow(task);
	}
	
	/* Alarm methods
	 ***********************************************************************************************************************/
	
	/**
	 * Sets an alarm for a task
	 * @param t the task which the alarm is added to.
	 */
	@SuppressLint("NewApi")
	private void setAlarm(Task t) {		 
		Calendar cal = Calendar.getInstance();
		cal.setTime(t.getDateDue());
			
		Intent intentService = new Intent(mContext, NotificationService.class);
		
		Notification n = buildNotification(t);
		intentService.putExtra("notification", n);
		
		// Create a unique identifier for the task's alarm. Used to cancel the alarm.
		int alarmId = (int) System.currentTimeMillis();
		
		
		PendingIntent pi = PendingIntent.getService(mContext, alarmId, intentService, PendingIntent.FLAG_UPDATE_CURRENT);
		t.setAlarmId(alarmId);
		t.setAlarmIntent(pi);
		
		// Check for API version, because setExact method doesn't work in api level < 19.
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		System.out.println("Your API version is: " + currentapiVersion);
		if (currentapiVersion >= 19){
			am.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
		} else{
			am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
		}
		Log.i("p1",	String.valueOf(t.getAlarmIntent().toString()));
	}
	
	/**
	 * Deletes a specific alarm.
	 * @param task the task from which the alarm is deleted.
	 */
	public void deleteAlarm(Task task) {
		if (task.getAlarmIntent() != null) {
			PendingIntent pi = task.getAlarmIntent();
			Log.i("p2",	String.valueOf(task.getAlarmIntent().toString()));
			am.cancel(pi);
			pi.cancel();
		}
		numTasksDue--;
	}
	
	/**
	 * Generates a notification containing task data
	 * @param t task which contains needed notification information
	 * @return the generated notification
	 */
	private Notification buildNotification(Task t) {
		numTasksDue++;
		
		// Update notification if there are notifications.
		if (numTasksDue < 2) {
		this.builder = new NotificationCompat.Builder(mContext)
	              .setSmallIcon(R.drawable.ic_notification)
	              .setContentTitle("A Task is due")
	              .setPriority(2)
	              .setContentText(t.getName());
		} else {
			this.builder.setNumber(numTasksDue)
				   .setContentTitle(numTasksDue + " Tasks due.")
				   .setContentText("");
		}
	    

	    Intent targetIntent = new Intent(mContext, TaskListActivity.class);
	    PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
	    builder.setContentIntent(contentIntent);
	    Notification n = builder.build();
	    n.flags = Notification.FLAG_AUTO_CANCEL;
	    
	    return n;
	}
}
