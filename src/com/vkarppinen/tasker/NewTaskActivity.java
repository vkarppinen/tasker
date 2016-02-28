package com.vkarppinen.tasker;

import android.support.v7.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class NewTaskActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_task);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_task, menu);
		return true;
	}

	/*
	 * Handle menu-item clicks here.
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)  {
		int id = item.getItemId();
		if (id == R.id.create_task) {
			createTask();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent();
		setResult(TaskListActivity.RESULT_CANCELED, intent);
		finish();
	}
	
	
	public void setDueDate(View view) {
		DialogFragment d = new DateDialog(view);
		d.show(getFragmentManager(), null);
	}
	
	public void setAlarmTime(View view) {
		DialogFragment d = new TimeDialog(view);
		d.show(getFragmentManager(), null);
	}
	
	/** 
	 * Called when the user clicks the Create button.
	 */
	public void createTask() {
	    Intent intent = new Intent(this, TaskListActivity.class);
	    EditText nameField = (EditText) findViewById(R.id.editTaskName);
	    EditText dateField = (EditText) findViewById(R.id.editDueDate);
	    EditText timeField = (EditText) findViewById(R.id.editDueTime);;
	    
	    String name = nameField.getText().toString();
	    String date = dateField.getText().toString();
	    String time = timeField.getText().toString();

	    // Gather data for date validity check.
	    Date dateDue = new Date();
	    Date dateNow = new Date();
	    String dateInString = date + " " + time;
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
		try {
			Date parsedDate = formatter.parse(dateInString);
			dateDue = parsedDate;
		} catch (ParseException e) {
			System.err.println("Error parsing task data");
		}
			
	    // Handle exception if required fields are empty and if assigned dateTime has passed.
	    if (name.length() > 0 && date.length() > 0 && time.length() > 0) {
		    
	    	if (dateDue.compareTo(dateNow) < 0 ) {
				Toast toast = Toast.makeText(getApplicationContext(), R.string.date_error, Toast.LENGTH_SHORT);
		    	toast.show();
			} else {
				Task task = new Task(name, date, time);
				intent.putExtra("Task", task);
			    setResult(RESULT_OK, intent);
			    finish();
			}	
	    } else {
	    	Toast toast = Toast.makeText(getApplicationContext(), R.string.toast_empty_task_fields, Toast.LENGTH_SHORT);
	    	toast.show();
	    }
	    
	}
}