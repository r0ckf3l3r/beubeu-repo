package com.android.counterapp.dialog;

import android.app.AlertDialog;
import android.content.Context;

public class ClearCounterDialog extends AlertDialog {

	private String message = "Are you sure you want to clear the counter?";;

	public ClearCounterDialog(Context context) {
		super(context);

		setMessage(message);
	}

	public ClearCounterDialog(Context context, int theme) {
		super(context, theme);
	}

	public ClearCounterDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

}
