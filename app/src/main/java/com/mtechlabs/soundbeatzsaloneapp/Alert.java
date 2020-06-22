package com.mtechlabs.soundbeatzsaloneapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class Alert extends DialogFragment {
	Context mContext;
	String title;
	Boolean exit;
	Activity activity;

	public void set(String title, Activity activity, Boolean exit) {
		this.title = title;
		this.exit = exit;
		this.activity = activity;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mContext = getActivity();
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
		alertDialogBuilder.setTitle(activity.getString(R.string.ErrorHeader));
		alertDialogBuilder.setMessage(title);
		//null should be your on click listener
		alertDialogBuilder.setPositiveButton(activity.getString(R.string.AcceptError), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (exit) {
					activity.finish();
				}
			}
		});

		return alertDialogBuilder.create();
	}
}