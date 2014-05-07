package com.example.addshortcut;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.Log;
import android.view.WindowManager;

public class MyReceiver extends BroadcastReceiver{
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder;
		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
		    builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		    Log.v("gzw","biger bigger sdk");
		} else {
			Log.v("gzw","little sdk");
		    builder = new AlertDialog.Builder(context);
		}
		AlertDialog dialog = builder.setTitle("title").setMessage("detail")
				.setNegativeButton("leftButton", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				}).setPositiveButton("rightButton", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).create();
		dialog.getWindow()
				.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
}
