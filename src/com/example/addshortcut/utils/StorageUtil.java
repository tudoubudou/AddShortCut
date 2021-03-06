
package com.example.addshortcut.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.res.Resources;
//import android.content.pm.PackageParser;
import android.os.Environment;
import android.os.StatFs;

public class StorageUtil {
    // private final static String TAG = "StorageUtil";
    private final static int    minSpaceMB = 10;

    public static boolean createThemeDir(String file) {
        File f = new File(file);

        if (!f.isDirectory()) {
            f = f.getParentFile();
        }

        if (!f.exists()) {
            return f.mkdirs();
        }
        return true;
    }

    public static void deleteDir(File dir)
    {
        if (!dir.exists()) {
            return;
        }

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();

            if (files != null) {
                for (File f : files) {
                    deleteDir(f);
                }
            }
            dir.delete();
        }
        else {
            dir.delete();
        }
    }

    public static void mvDir(String src, String dst) {
        File file_src = new File(src);
        File file_dst = new File(dst);

        if (file_dst.exists()) {
            deleteDir(file_dst);
        }

        if (file_src.exists()) {
            file_src.renameTo(file_dst);
        }
    }

    public static int FolderCopy(String fromFile, String toFile)
    {
        int ret = 0;
        File[] currentFiles = null;
        File root = new File(fromFile);

        if (!root.exists()) {
            ret = -1;
        }

        if (ret == 0) {
            currentFiles = root.listFiles();

            File targetDir = new File(toFile);
            if (!targetDir.exists()) {
                if (!targetDir.mkdirs()) {
                    ret = -1;
                }
            }
        }

        if (ret == 0 && currentFiles != null) {
            for (int i = 0; i < currentFiles.length; i++) {
                if (currentFiles[i].isDirectory()) {

                    int _ret = FolderCopy(currentFiles[i].getPath() + File.separator,
                            toFile + currentFiles[i].getName()
                                    + File.separator);

                    if (_ret != 0) {
                        break;
                    }

                } else {
                    int _ret = FileCopy(currentFiles[i].getPath(),
                            toFile + currentFiles[i].getName());
                    if (_ret != 0) {
                        break;
                    }
                }
            }
        }
        return ret;
    }

    public static int FileCopy(String fromFile, String toFile)
    {

        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024 * 8];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static boolean isPathExists(String path) {
        if (path != null && !"".equals(path)) {
            File file = new File(path);
            if (file.exists())
                return true;
        }
        return false;
    }

    public static String getDiskRoot() {

        boolean has_sdcard = !Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_REMOVED);
        
        if (has_sdcard){
            if(Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)){
                return Environment.getExternalStorageDirectory().getAbsolutePath();
            }
        }

        return "";
    }

    public static String getDownloadTempDir() {
        String tmp = getSdCardFile() + "/Business/temp";

        File file = new File(tmp);
        if (!file.exists()) {
            file.mkdirs();
        }

        return tmp;
    }

    public static String getDownloadBusinessDir(String aFilename) {
        File file = new File(aFilename);
        if (!file.exists()) {
            file.mkdirs();
        }

        return aFilename;
    }

    public static String getShareTempFileDir() {
        String tmp = getSdCardFile() + "/Business/.shareHome/";

        File file = new File(tmp);
        if (!file.exists()) {
            file.mkdirs();
        }

        return tmp;
    }
    
    public static String getBusinessApkFileDir(String aName, long aStrategyId) {
        return getDownloadBusinessDir(getSdCardFile() + "/Business/app/" + aStrategyId) + "/"
                + aName + ".apk";
    }

    public static String getBusinessApkPath(long aStrategyId) {
        return getDownloadBusinessDir(getSdCardFile() + "/Business/app/" + aStrategyId) + "/";
    }

    public static boolean isBusinessApkFileExit(String aName, long aStrategyId) {
        String filename = getBusinessApkFileDir(aName, aStrategyId);
        File file = new File(filename);
//        if (file.exists()) {
//			try {
//				PackageParser.PackageLite pkg = PackageParser.parsePackageLite(filename, 0);
//				if (pkg != null) {
//					return true;
//				} else {
//					file.delete();
//					return false;
//				}
//			} catch (Exception e) {
//				return false;
//			}	
//		}
        return false;
    }

    public static boolean isExternalSpaceInsufficient(long requireSize) {
        try {
            String path = getDiskRoot();
            if (path.equals("")) {
                return false;
            }
            StatFs statFs = new StatFs(path);
            long blocSize = statFs.getBlockSize();
            long availaBlock = statFs.getAvailableBlocks();
            long availableSpace = availaBlock * blocSize / (1024 * 1024);
            if(availableSpace > minSpaceMB + requireSize/(1024*1024)){
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean IsSdCardMounted() {
        try {
            boolean has_sdcard =  Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED);
            if(has_sdcard){
                if(getDiskRoot().equals("") || getDiskRoot() == ""){ //can not get sdcard root path
                    has_sdcard = false;
                }
            }
            return has_sdcard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getVersionUpdateFileDir() {
    	String sdc = getSdCardFile();
    	if(sdc == null) {
    		return null;
    	} else {
    		return getDownloadBusinessDir(sdc  + "/Business/version/");
    	}
    }

    public static String getSdCardFile() {
        if (IsSdCardMounted()) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }
    public static void copyAssetsFile(Resources res, String srcName, String dst) {
        InputStream in = null;
        FileOutputStream fos = null;
        try {
                in = res.getAssets().open(srcName);
                byte[] buffer = new byte[1024 * 16];
                fos = new FileOutputStream(dst);
                int len = 0;

                while ((len = in.read(buffer, 0, 1024 * 16)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
                fos.getFD().sync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
