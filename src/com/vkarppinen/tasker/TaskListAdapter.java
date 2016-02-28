package com.vkarppinen.tasker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TaskListAdapter extends BaseAdapter {

    Context context;
    Tasks tasks;
    ArrayList<Task> data;
    private static LayoutInflater inflater = null;

    public TaskListAdapter(Context context, Tasks tasks) {
    	this.tasks = tasks;
        this.context = context;
        this.data = tasks.getTasks();
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = inflater.inflate(R.layout.custom_row, parent, false);
        TextView textDateDue = (TextView) v.findViewById(R.id.taskDueDate);
        TextView textTaskName = (TextView) v.findViewById(R.id.taskName);
        ImageView taskPriority = (ImageView) v.findViewById(R.id.taskPriority);
        
        View deleteTask = v.findViewById(R.id.btnCompleteTask);
       	deleteTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	tasks.delete( ((Task) getItem(position)).getID() );
            	ListView taskList = (ListView) v.getParent().getParent();
            	((BaseAdapter) taskList.getAdapter()).notifyDataSetChanged();
            }
        });
        
       	// Format the text shown in date field.
       	Date taskDate = data.get(position).getDateDue();
        Calendar taskCal = Calendar.getInstance();
       	taskCal.setTime(taskDate);
        int year = taskCal.get(Calendar.YEAR);
        String month = taskCal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = taskCal.get(Calendar.DAY_OF_MONTH);
        int hour = taskCal.get(Calendar.HOUR_OF_DAY);
        String min = String.format(Locale.getDefault(), "%02d", taskCal.get(Calendar.MINUTE));
        String dateText = day + ". " + month + " " + year + " klo " + hour + ":" + min;
        
        textDateDue.setText(dateText);
        textTaskName.setText(data.get(position).getName());
        
        // Change taskpriority image according to task date.
        Calendar nowCal = Calendar.getInstance();
        if (taskCal.get(Calendar.DAY_OF_YEAR) - nowCal.get(Calendar.DAY_OF_YEAR) < 1) {
        	taskPriority.setImageResource(R.drawable.priority_high);
        } else if (taskCal.get(Calendar.DAY_OF_YEAR) - nowCal.get(Calendar.DAY_OF_YEAR) < 3){
        	taskPriority.setImageResource(R.drawable.priority_medium);
        } else {
        	taskPriority.setImageResource(R.drawable.priority_low);
        }

        
        return v;
    }	
}