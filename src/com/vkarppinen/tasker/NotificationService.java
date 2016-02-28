package com.vkarppinen.tasker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {
	
	private final int NOTIFICATION_ID = 1;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Notification n = intent.getParcelableExtra("notification");
	    NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	    nManager.notify(NOTIFICATION_ID, n);
	    
	    // Stop the service.
	    stopSelf();
		
	    return NOTIFICATION_ID;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
	
