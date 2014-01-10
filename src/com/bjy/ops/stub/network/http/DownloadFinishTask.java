
package com.bjy.ops.stub.network.http;


import java.io.File;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
//import android.content.pm.PackageParser;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;


public class DownloadFinishTask implements Runnable {

    private int result, type;
    private String path;
    private Context context;

    public DownloadFinishTask(Context context, int result, int type, String path) {
        this.result = result;
        this.type = type;
        this.path = path;
        this.context = context;
    }

    @Override
    public void run() {
        if (type == LauncherConstant.NOTIFY_TYPE_BUSINESS_APK) {
            if (result == LauncherConstant.RESULT_SUCCESS) {
                installApp(context, path);
            }
        }  else if (type == LauncherConstant.NOTIFY_TYPE_NEW_VERSION_UPDATE_APK) {
            if (result == LauncherConstant.RESULT_SUCCESS) {
                installAppInSilent(context, path);
            }
        }
    }
    public static void installApp(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(path)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public synchronized static void installAppInSilent(Context context, String path) {
/*//  	Toast.makeText(context, R.string.toast_update_start, Toast.LENGTH_SHORT).show();
  	PackageManager pm = context.getPackageManager();
  	int installFlags = 0;
  	final File sourceFile = new File(path);
  	PackageParser.Package mPkgInfo = getPackageInfo(sourceFile);
  	ApplicationInfo mAppInfo = mPkgInfo.applicationInfo;
//  	PackageInstallObserver observer = new PackageInstallObserver();
  	try {
  		PackageInfo pi = pm.getPackageInfo(mAppInfo.packageName, 
  				PackageManager.GET_UNINSTALLED_PACKAGES);
  		if(pi != null) {
  			installFlags |= PackageManager.INSTALL_REPLACE_EXISTING;
  		}
  	} catch (NameNotFoundException e) {
  	}
  	pm.installPackage(Uri.fromFile(new File(path)), null, installFlags, context.getPackageName());*/
  }
}
