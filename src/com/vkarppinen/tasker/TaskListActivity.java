package com.vkarppinen.tasker;

import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class TaskListActivity extends ActionBarActivity {
	protected Tasks tasks = new Tasks();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_list_view);
		
		initUI();
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  
	  // Save the parcelable task objects to the instance state.
	  savedInstanceState.putParcelableArrayList("Tasks", tasks.getTasks());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  
	  // Get task objects from saved instance state to a supporting arraylist.
	  ArrayList<Task> ts = savedInstanceState.getParcelableArrayList("Tasks");
	  
	  // add each from supporting list to the main task list.
	  for (Task t : ts) {
		this.tasks.add(t);
	  }
	  
	  // Garbage collection will handle the support arraylist.
	  ts = null; 
	  
	  initUI();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if (requestCode == 1) {
	    	if(resultCode == RESULT_OK){
	     		Task task = (Task) data.getParcelableExtra("Task");	  
	     		this.tasks.add(task);
	     		initUI();
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/*
	 * Developer created methods. 
	 */
	
	/**
	 * Called when the user clicks the Create button.
	 * @param view
	 */
	public void newTask(View view) {
	    Intent intent = new Intent(this, NewTaskActivity.class);
	    startActivityForResult(intent, 1);
	}
	
	
	/**
	 * initialize the UI.
	 */
	private void initUI() {
		
		TableLayout table = (TableLayout)findViewById(R.id.tasksTable);
		table.removeAllViews();

		for (Task task : tasks.getTasks()) {
		    TableRow tr = new TableRow(this);
		    tr.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

		    TextView tv = new TextView(this);
		    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		    tv.setText(task.getName());
		    tr.addView(tv);
		    
		    TextView tv2 = new TextView(this);
		    tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
		    tv2.setText(task.getDateDue().toString());
		    tr.addView(tv2);

		    table.addView(tr);
		}
	}
}
