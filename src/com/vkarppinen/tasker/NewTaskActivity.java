package com.vkarppinen.tasker;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

@SuppressWarnings("deprecation")
public class NewTaskActivity extends ActionBarActivity {

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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
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
	
	
	/*
	 * Developer created methods. 
	 */
	
	/** Called when the user clicks the Create button */
	public void createTask(View view) {
	    Intent intent = new Intent(this, TaskListActivity.class);
	    EditText TaskNameField = (EditText) findViewById(R.id.editTaskName);
	    Task task = new Task(TaskNameField.getText().toString());
	    
	    intent.putExtra("Task", task);
	    setResult(RESULT_OK, intent);        
	    finish();
	}
}
