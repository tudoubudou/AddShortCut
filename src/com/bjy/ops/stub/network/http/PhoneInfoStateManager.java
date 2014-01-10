
package com.bjy.ops.stub.network.http;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class PhoneInfoStateManager {

    private final static String TAG = PhoneInfoStateManager.class.getSimpleName();

    public PhoneInfoStateManager() {
    }

//    public static String getChannelID(Context aContext) {
//        return getContentFromIni(aContext, R.raw.tnconfig);
//    }

    public String getPhoneBrand() {
        return Build.BRAND;
    }

    public String getPhoneDeviceModel() {
        return Build.MODEL;
    }

    public String getDevicever() {
        return Build.DISPLAY;
    }

    public String getSysterVersion() {
        return Build.VERSION.RELEASE;
    }

    public String getResolution(Context aContext) {
        DisplayMetrics display = aContext.getResources().getDisplayMetrics();
        return display.widthPixels + "*" + display.heightPixels;
    }
    public String getLanguage(){
        return Locale.getDefault().getLanguage();
    }
    public String getMacAddress(Context aContext) {
        String result = null;
        WifiManager wifiManager = (WifiManager) aContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        result = wifiInfo.getMacAddress();
        return result;
    }

    public String getImei(Context aContext) {
        TelephonyManager tm = (TelephonyManager) aContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

//    public String getOperatorName(Context aContext) {
//        TelephonyManager tm = (TelephonyManager) aContext
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        String simoperator = tm.getSimOperator();
//
//        Log.e(TAG, "simperator :" + simoperator);
//        String[] liantong = {
//                "46001", "46006"
//        };
//        String[] yidong = {
//                "46000", "46002", "46007"
//        };
//        String[] dianxin = {
//                "46003", "46005"
//        };
//        
//        if (simoperator == null || simoperator.trim().equals("")) {
//            return aContext.getResources().getString(R.string.operator_unknown);
//        }else{
//            try{
//            simoperator = simoperator.substring(0, 5);
//            }catch(Exception e){
//                return aContext.getResources().getString(R.string.operator_unknown);
//            }
//
//        }
//        for (String data : liantong) {
//            if (data.equals(simoperator)) {
//                return aContext.getResources().getString(R.string.operator_liantong);
//            }
//        }
//        for (String data : yidong) {
//            if (data.equals(simoperator)) {
//                return aContext.getResources().getString(R.string.operator_yidong);
//            }
//        }
//        for (String data : dianxin) {
//            if (data.equals(simoperator)) {
//                return aContext.getResources().getString(R.string.operator_dianxin);
//            }
//        }
//
//        return aContext.getResources().getString(R.string.operator_other);
//    }

    public String getPhoneNumber(Context aContext) {
        TelephonyManager tm = (TelephonyManager) aContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    public static String getVersionName(Context aContext) {
        try {
            PackageManager packageManager = aContext.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(aContext.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getVersionCode(Context aContext) {
        try {
            PackageManager packageManager = aContext.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(aContext.getPackageName(), 0);
            int versioncode = packInfo.versionCode;
            return String.valueOf(versioncode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * locationdata[0] : Latitude locationdata[1] : Longitude;
     * 
     * @param aContext
     * @return
     */
    public double[] getLocation(Context aContext) {
//        final LocationManager locationManager /*= (LocationManager) aContext
//                .getSystemService(Context.LOCATION_SERVICE);
//        Location location = locationManager*/.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double[] locationdata = new double[] {
                22.498659, 113.912213
        };
//        if (location != null) {
//            locationdata[0] = location.getLatitude();
//            locationdata[1] = location.getLongitude();
//        } else {
//            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location != null) {
//                locationdata[0] = location.getLatitude();
//                locationdata[1] = location.getLongitude();
//            }
//        }
        return locationdata;
    }


//    public static String getCUID(Context aContext) {
//        return StatService.getCuid(aContext);
//    }

    public String getTotalMemory(Context aContext) {
        String resul = "";
        ActivityManager am = (ActivityManager) aContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        long totalmem = 0;
        String memfile = "/proc/meminfo";
        String memcontent;
        String[] arrayofstring;
        try {
            FileReader localFileReader = new FileReader(memfile);
            BufferedReader buffer = new BufferedReader(localFileReader, 8192);
            memcontent = buffer.readLine();
            if (memcontent != null) {
                arrayofstring = memcontent.split("\\s+");
                totalmem = Integer.valueOf(arrayofstring[1]).intValue() / 1024;
                buffer.close();
            }
        } catch (IOException e) {
            LogEx.i(TAG, e.getStackTrace().toString());
        }
        return String.valueOf(totalmem);
    }

    public static boolean isNetworkConnectivity(Context aContext) {
        ConnectivityManager manager = (ConnectivityManager) aContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null == manager){
            return false;
        }
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            int type = activeInfo.getType();
            if (ConnectivityManager.TYPE_WIFI == type) {
                return true;
            } else if (ConnectivityManager.TYPE_MOBILE == type) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiConnection(Context aContext) {
        ConnectivityManager manager = (ConnectivityManager) aContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if(null == manager){
            return false;
        }
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
            int type = activeInfo.getType();
            if (ConnectivityManager.TYPE_WIFI == type) {
                return true;
            }
        }
        return false;
    }

    public boolean IsSdCardMounted() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getIpAddresses(Context aContext) {
        ConnectivityManager cm = (ConnectivityManager)
                aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        // LinkProperties prop = cm.getActiveLinkProperties();
        // return formatIpAddresses(prop);
        return null;
    }

    private static String getContentFromIni(Context aContext, int aResId) {
        InputStream iniFile = null;
        iniFile = aContext.getResources().openRawResource(aResId);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(iniFile, "gb2312"));
        } catch (UnsupportedEncodingException e) {
            LogEx.w(TAG, e.getStackTrace().toString());
        }

        String content = "";
        String tmp = null;
        try {
            while ((tmp = bufferedReader.readLine()) != null) {
                content += tmp;
                LogEx.d(TAG, tmp);
            }
            bufferedReader.close();
            iniFile.close();
        } catch (IOException e) {
            LogEx.w(TAG, e.getStackTrace().toString());
        }
        return content;
    }
}
