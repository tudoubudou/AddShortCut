
package com.bjy.ops.stub.db;

import java.util.ArrayList;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.bjy.ops.stub.network.http.LauncherConstant;
import com.example.addshortcut.utils.LogEx;


public class LauncherProvider extends ContentProvider {
    private static final String TAG = "LauncherProvider";

    private LauncherDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new LauncherDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String orderBy) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = null;

        if (LauncherConstant.TABLE_DOWNLOAD.equals(args.table)) {
            c = db.query(LauncherConstant.TABLE_DOWNLOAD, projection, selection,
                    selectionArgs, null, null, orderBy);
        } else if (LauncherConstant.TABLE_BUSINESS.equals(args.table)) {
            c = db.query(LauncherConstant.TABLE_BUSINESS + " left join "
                    + LauncherConstant.TABLE_STRATEGY + " on "
                    + LauncherConstant.TABLE_BUSINESS + "."
                    + LauncherConstant.COLUMN_BUSINESS_STRATEGYID + "="
                    + LauncherConstant.TABLE_STRATEGY + "." + LauncherConstant.ID, projection,
                    selection, selectionArgs, null, null, orderBy);
        } else if (LauncherConstant.TABLE_STRATEGY.equals(args.table)) {
            String strategy_order_by = orderBy;
            if (strategy_order_by == null) {
                strategy_order_by = LauncherConstant.COLUMN_STRATEGY_KEY + " desc, " + LauncherConstant.ID
                        + " desc";
            }

            c = db.query(LauncherConstant.TABLE_STRATEGY, projection, selection, selectionArgs, null,
                    null, strategy_order_by);
        } else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (c != null) {
            c.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SqlArguments args = new SqlArguments(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long rowId = 0;
        db.beginTransaction();

        try {
            rowId = db.insert(args.table, LauncherConstant.ID, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange(uri, null);

        if (rowId > 0)
        {
            Uri noteUri = ContentUris.withAppendedId(uri, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        LogEx.e(TAG, "insert error " + uri.toString());
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;

        db.beginTransaction();

        try {
            if (LauncherConstant.TABLE_DOWNLOAD.equals(args.table)
                    || LauncherConstant.TABLE_BUSINESS.equals(args.table)
                    || LauncherConstant.TABLE_STRATEGY.equals(args.table)) {
                count = db.delete(args.table, selection, selectionArgs);
                db.setTransactionSuccessful();
            } else {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SqlArguments args = new SqlArguments(uri, selection, selectionArgs);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = 0;

        db.beginTransaction();
        try {
            if (LauncherConstant.TABLE_DOWNLOAD.equals(args.table)
                    || LauncherConstant.TABLE_BUSINESS.equals(args.table)
                    || LauncherConstant.TABLE_STRATEGY.equals(args.table)) {
                count = db.update(args.table, values, selection, selectionArgs);
                db.setTransactionSuccessful();
            } else {
                LogEx.e(TAG, "update error " + uri.toString());
                throw new IllegalArgumentException("Unknown URI " + uri);
            }
        } finally {
            db.endTransaction();
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


    static class SqlArguments {
        public final String table;
        public final String where;
        public final String[] args;

        SqlArguments(Uri url, String where, String[] args) {
            if (url.getPathSegments().size() == 1) {
                this.table = url.getPathSegments().get(0);
                this.where = where;
                this.args = args;
            } else if (url.getPathSegments().size() != 2) {
                throw new IllegalArgumentException("Invalid URI: " + url);
            } else if (!TextUtils.isEmpty(where)) {
                throw new UnsupportedOperationException("WHERE clause not supported: " + url);
            } else {
                this.table = url.getPathSegments().get(0);
                this.where = "_id=" + ContentUris.parseId(url);
                this.args = null;
            }
        }

        SqlArguments(Uri url) {
            if (url.getPathSegments().size() == 1) {
                table = url.getPathSegments().get(0);
                where = null;
                args = null;
            } else {
                throw new IllegalArgumentException("Invalid URI: " + url);
            }
        }
    }

    @Override
    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            return results;
        } finally {
            db.endTransaction();
        }
    }

}
