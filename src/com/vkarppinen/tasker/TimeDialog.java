package com.vkarppinen.tasker;

import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class TimeDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	View parentView;
	
	public TimeDialog(View view) {
		this.parentView = view;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		setRetainInstance(true);

		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE); 
		
		return new TimePickerDialog(getActivity(), this, hour, min, DateFormat.is24HourFormat(getActivity()));
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hour, int min) {
		EditText timeField = (EditText) parentView.findViewById(R.id.editDueTime);
		String output = String.format(Locale.getDefault(), "%02d:%02d", hour, min);
		timeField.setText(output);
	}

}
