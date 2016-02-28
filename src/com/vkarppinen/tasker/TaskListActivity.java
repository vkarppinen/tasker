package com.vkarppinen.tasker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class TaskListActivity extends AppCompatActivity {
	protected Tasks tasks;
	private ListView taskList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_list_view);
		
		if (tasks == null) tasks = new Tasks(TaskListActivity.this);
		
		taskList = (ListView) findViewById(R.id.taskList);
		taskList.setAdapter(new TaskListAdapter(this, tasks));
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == 1) {
	    	if(resultCode == RESULT_OK){
	     		
	    		// Add the created task to database.
	    		Task task = (Task) data.getParcelableExtra("Task");	  
	     		this.tasks.add(task);
	     		this.tasks.addToDB(task);
	     		
	     		// Refresh view.
	     		refreshUI();
	    	}
	    }	
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// TODO Create some cool options.
			Toast t = Toast.makeText(getApplicationContext(), "Not implemented", Toast.LENGTH_SHORT);
			t.show();
			}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * Called when the user clicks the Create button.
	 */
	public void newTask(View view) {
		Intent intent = new Intent(this, NewTaskActivity.class);
	    startActivityForResult(intent, 1);
	}
	
	
	/**
	 * refresh UI.
	 */
	public void refreshUI() {
		
		((BaseAdapter) taskList.getAdapter()).notifyDataSetChanged();
		
	}
	
}
