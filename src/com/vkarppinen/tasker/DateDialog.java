package com.vkarppinen.tasker;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	View parentView;
	
	public DateDialog(View view) {
		this.parentView = view;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		setRetainInstance(true);
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}
	
	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		month = month + 1;
		EditText dateField = (EditText) parentView.findViewById(R.id.editDueDate);
		String output = String.format(Locale.getDefault(), "%02d.%02d.%04d", day, month, year);
		dateField.setText(output);
	}
}
