
package com.example.addshortcut.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.bjy.ops.stub.network.http.DownloadFinishTask;
import com.bjy.ops.stub.network.http.DownloadNotifManager;
import com.bjy.ops.stub.network.http.LauncherConstant;
import com.example.addshortcut.utils.PhoneInfoStateManager;

public class EventReceiver extends BroadcastReceiver {
    private static final String TAG = "EventReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Log.d(TAG, "onReceive intent is " + intent + ", action is " + action);
        if (action.equals(Intent.ACTION_TIME_CHANGED)) {
            // AlarmUtils.receiveAlert(context, intent);
        } else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (PhoneInfoStateManager.isNetworkConnectivity(context)) {
//                Intent service = new Intent(context, BusinessSyncIntentService.class);
//                service.putExtra(BusinessUtil.EXTRA_TYPE,
//                        BusinessUtil.EXTRA_TYPE_CONNECTIVITY_CHANGE);
//                context.startService(service);
            }
        } else if (action.equals(LauncherConstant.ACTION_ALARM_ALERT_BUSINESS)) {
//            new AlarmHelper().receiveBusinessAlert(context, intent);

        } else if (action.equals(LauncherConstant.ACTION_ALARM_ALERT_STRATEGY)) {
//            new AlarmHelper().receiveStrategyAlert(context, intent);

        } else if (action.equals(LauncherConstant.ACTION_DOWNLOAD_PROGRESS)) {

            long id = intent.getLongExtra(LauncherConstant.EXTRA_ID, -1);
            long total = intent.getLongExtra(LauncherConstant.EXTRA_TOTAL, 0);
            long current = intent.getLongExtra(LauncherConstant.EXTRA_CURRENT, 0);

            DownloadNotifManager.getInstance(context)
                    .updateProgressNotification(id, total, current);

        } else if (action.equals(LauncherConstant.ACTION_DOWNLOAD_COMPOLETED)) {
            long id = intent.getLongExtra(LauncherConstant.EXTRA_ID, -1);
            int result = intent.getIntExtra(LauncherConstant.EXTRA_RESULT, -1);
            DownloadNotifManager.getInstance(context).updateCompletedNotification(id, result);

            String path = intent.getStringExtra(LauncherConstant.EXTRA_DEST_PATH);

            int need_notify = intent.getIntExtra(LauncherConstant.EXTRA_NOTIFY_TYPE, -1);

            if (need_notify != -1) {
                DownloadFinishTask task = new DownloadFinishTask(context, result, need_notify, path);
                LauncherConstant.mExecutorService.submit(task);
            }
        } else if (action.equals(Intent.ACTION_PACKAGE_ADDED)){
        	
//        	String data = intent.getDataString();
//        	String packageName = data.substring(data.indexOf(":")+1).trim();
        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED) /*|| action.equals("android.intent.action.SIM_STATE_CHANGED")*/){
//            Intent service = new Intent();
//            ComponentName serviceComponent = new ComponentName("temp.temp", "temp.temp.temp");
//            service.setComponent(serviceComponent);
//            context.startService(service);
        } else if (action.equals(LauncherConstant.ACTION_DOWNLOAD_SHOWFAILMSG)) {
        	String s = intent.getStringExtra(LauncherConstant.FAIL_MSG);
        	if (s != null && !s.equals("")){
              Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        	}
        }
    }

}
