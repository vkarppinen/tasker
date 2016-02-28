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
        
        View deleteTask = v.findViewById(R.id.btnCompleteTask);
       	deleteTask.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	tasks.delete( ((Task) getItem(position)).getID() );
            	ListView taskList = (ListView) v.getParent().getParent();
            	((BaseAdapter) taskList.getAdapter()).notifyDataSetChanged();
            }
        });
        
       	// Format the text shown in date field.
       	Date date = data.get(position).getDateDue();
        Calendar c = Calendar.getInstance();
       	c.setTime(date);
        int year = c.get(Calendar.YEAR);
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        String min = String.format(Locale.getDefault(), "%02d", c.get(Calendar.MINUTE));
        String dateText = day + ". " + month + " " + year + " klo " + hour + ":" + min;
        
        textDateDue.setText(dateText);
        textTaskName.setText(data.get(position).getName());
        
        return v;
    }	
}