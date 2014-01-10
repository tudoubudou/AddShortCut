
package com.bjy.ops.stub.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bjy.ops.stub.network.http.LauncherConstant;


public class LauncherDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "LauncherDatabaseHelper";


    private static final String CREATE_DOWNLOAD_TABLE = "CREATE TABLE IF NOT EXISTS "
            + LauncherConstant.TABLE_DOWNLOAD
            + " ( "
            + LauncherConstant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LauncherConstant.COLUMN_DOWNLOAD_FILE_NAME + " TEXT,"
            + LauncherConstant.COLUMN_DOWNLOAD_DESTINATION + " TEXT,"
            + LauncherConstant.COLUMN_DOWNLOAD_URL + " TEXT,"
            + LauncherConstant.COLUMN_DOWNLOAD_MIME_TYPE + " TEXT,"
            + LauncherConstant.COLUMN_DOWNLOAD_TOTAL_SIZE + " INTEGER NOT NULL DEFAULT 0,"
            + LauncherConstant.COLUMN_DOWNLOAD_CURRENT_SIZE + " INTEGER,"
            + LauncherConstant.COLUMN_DOWNLOAD_STATUS + " INTEGER,"
            + LauncherConstant.COLUMN_DOWNLOAD_DATE + " INTEGER,"
            + LauncherConstant.COLUMN_DOWNLOAD_TITLE + " TEXT, "
            + LauncherConstant.COLUMN_DOWNLOAD_DESCRIPTION + " TEXT, "
            + LauncherConstant.COLUMN_DOWNLOAD_WIFIONLY + " INTEGER NOT NULL DEFAULT -1"
            + ");";

    /*private static final String CREATE_STRATEGY_TABLE = "CREATE TABLE IF NOT EXISTS "
            + LauncherConstant.TABLE_STRATEGY
            + " ( "
            + LauncherConstant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LauncherConstant.COLUMN_STRATEGY_KEY + " INTEGER,"
            + LauncherConstant.COLUMN_STRATEGY_START + " INTEGER,"
            + LauncherConstant.COLUMN_STRATEGY_END + " INTEGER,"
            + LauncherConstant.COLUMN_STRATEGY_CHANNELID + " TEXT"
            + ");";*/

    private static final String CREATE_BUSINESS_TABLE = "CREATE TABLE IF NOT EXISTS "
            + LauncherConstant.TABLE_BUSINESS
            + " ( "
            + LauncherConstant.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LauncherConstant.COLUMN_BUSINESS_BUSINESS_ID + " INTEGER,"
            + LauncherConstant.COLUMN_BUSINESS_ICON_URL + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_ICON + " BLOB,"
            + LauncherConstant.COLUMN_BUSINESS_APK_URL + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_PACKAGE_NAME + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_VERSION_NAME + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_VERSION_CODE + " INTEGER,"
            + LauncherConstant.COLUMN_BUSINESS_DESCRIPTION + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_RANK + " INTEGER,"
            + LauncherConstant.COLUMN_BUSINESS_STRATEGYID + " INTEGER,"
            + LauncherConstant.COLUMN_BUSINESS_ITEMTYPE + " INTEGER,"
            + LauncherConstant.COLUMN_BUSINESS_CONTAINER_ID + " INTEGER INTEGER NOT NULL DEFAULT -1,"
            + LauncherConstant.COLUMN_BUSINESS_LOCATE + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_TITLE + " TEXT,"
            + LauncherConstant.COLUMN_BUSINESS_SPANX + " INTEGER INTEGER NOT NULL DEFAULT -1,"
            + LauncherConstant.COLUMN_BUSINESS_SPANY + " INTEGER INTEGER NOT NULL DEFAULT -1"
            + ");";

    LauncherDatabaseHelper(Context context) {
        super(context, LauncherConstant.DATABASE_NAME, null, LauncherConstant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DOWNLOAD_TABLE);
        db.execSQL(CREATE_BUSINESS_TABLE);
//        db.execSQL(CREATE_STRATEGY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }



    /**
     * Build a query string that will match any row where the column matches
     * anything in the values list.
     */
    static String buildOrWhereString(String column, int[] values) {
        StringBuilder selectWhere = new StringBuilder();
        for (int i = values.length - 1; i >= 0; i--) {
            selectWhere.append(column).append("=").append(values[i]);
            if (i > 0) {
                selectWhere.append(" OR ");
            }
        }
        return selectWhere.toString();
    }
    
}
