package com.vkarppinen.tasker;

import java.util.ArrayList;
import com.vkarppinen.tasker.Task;


/**
 * Simple class for storing multiple elements and updating and deleting them.
 * Uses tasks in this context.
 * @author Valtteri Karppinen, 1.0, 2.12.2015 
 */

public class Tasks {
	
	private ArrayList<Task> tasks;
	
	public Tasks() {
		this.tasks = new ArrayList<Task>();
	}
	
	public ArrayList<Task> getTasks() {
		return this.tasks;
	}
	
	public void add(Task task) {
		if (!tasks.contains(task)) {
			this.tasks.add(task);
		}
	}
	
	public void delete(Task task) {
		this.tasks.remove(task);
	}
	
	public void update(Task task) {
		for (Task TaskToBeUpdated : tasks) {
			if (task.getID() == TaskToBeUpdated.getID()) {
				TaskToBeUpdated.setName(task.getName());
				TaskToBeUpdated.setDateDue(task.getDateDue());
			}
		}
	}
}
