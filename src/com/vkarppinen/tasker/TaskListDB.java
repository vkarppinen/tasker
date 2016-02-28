package com.vkarppinen.tasker;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class TaskListDB extends SQLiteOpenHelper {
	
	// Database general information.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks.db";

    /* Table columns */
    public static  abstract class TasksTable implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date_due";
    }
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + TasksTable.TABLE_NAME + " (" +
        		TasksTable.COLUMN_ID + TEXT_TYPE + " PRIMARY KEY," +
        		TasksTable.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
        		TasksTable.COLUMN_DATE + TEXT_TYPE +
        " )";
    
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TasksTable.TABLE_NAME;

    public TaskListDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

	public long insert(Task task) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(TasksTable.COLUMN_ID, task.getID().toString());
		values.put(TasksTable.COLUMN_NAME, task.getName());
		values.put(TasksTable.COLUMN_DATE, task.getDateDue().toString());
		
		// Insert the new row. Returns the primary key value of the new row.
		long newRowId;
		newRowId = database.insert(
		         TasksTable.TABLE_NAME,
		         null,
		         values);
		return newRowId;
	}


	public void getRows(ArrayList<Task> tasks) {
		
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor c = database.rawQuery("SELECT * FROM " + TasksTable.TABLE_NAME, null);
		
		while (c.moveToNext()) {
			String id = c.getString(c.getColumnIndex(TasksTable.COLUMN_ID));
			String name = c.getString(c.getColumnIndex(TasksTable.COLUMN_NAME));
			String date = c.getString(c.getColumnIndex(TasksTable.COLUMN_DATE));
			
			tasks.add(new Task(id, name, date, true));
		}
		
		c.close();
		
	}


	public void deleteRow(Task t) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TasksTable.TABLE_NAME, TasksTable.COLUMN_ID + " LIKE " + "\"" + t.getID().toString() + "\"", null);
	}

}
