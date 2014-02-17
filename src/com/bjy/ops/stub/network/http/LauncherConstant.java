
package com.bjy.ops.stub.network.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.net.Uri;

public class LauncherConstant {

    // db
    public static final String DATABASE_NAME = "launcher.db";
    public static final int DATABASE_VERSION = 14;
//    public static final String PACKAGENAME = "com.example.addshortcut";
    public static final String PACKAGENAME = "com.android.ops.stub";
    public static final String AUTHORITY = PACKAGENAME + ".main.downloads";
    public static final String ID = "_id";

    public static final String TABLE_DOWNLOAD = "download";
    public static final String TABLE_BUSINESS = "business";
    public static final String TABLE_STRATEGY = "strategy";

    public static final Uri DOWNLOAD_URI = Uri.parse("content://" + AUTHORITY + "/"
            + TABLE_DOWNLOAD);
    public static final Uri BUSINESS_URI = Uri.parse("content://" + AUTHORITY + "/"
            + TABLE_BUSINESS);
    public static final Uri STRATEGY_URI = Uri.parse("content://" + AUTHORITY + "/"
            + TABLE_STRATEGY);

    // download table
    public static final String COLUMN_DOWNLOAD_FILE_NAME = "file_name";
    public static final String COLUMN_DOWNLOAD_DESTINATION = "dest";
    public static final String COLUMN_DOWNLOAD_URL = "url";
    public static final String COLUMN_DOWNLOAD_MIME_TYPE = "mime_type";
    public static final String COLUMN_DOWNLOAD_TOTAL_SIZE = "total_size";
    public static final String COLUMN_DOWNLOAD_CURRENT_SIZE = "current_size";
    public static final String COLUMN_DOWNLOAD_STATUS = "status";
    public static final String COLUMN_DOWNLOAD_DATE = "download_date";
    public static final String COLUMN_DOWNLOAD_TITLE = "title";
    public static final String COLUMN_DOWNLOAD_DESCRIPTION = "description";
    public static final String COLUMN_DOWNLOAD_WIFIONLY = "wifionly";

    public static final String PARAMETER_NOTIFY = "notify";

    // download type
    public static final String MIME_TYPE_THEME_ICON = "theme/ICON";

    public static final String MIME_TYPE_WALLPAPER = "wallpaper";

    public static final String MIME_TYPE_PUSH = "apk";

    // business table
    public static final String COLUMN_BUSINESS_BUSINESS_ID = "business_id";
    public static final String COLUMN_BUSINESS_ICON_URL = "icon_url";
    public static final String COLUMN_BUSINESS_ICON = "icon";
    public static final String COLUMN_BUSINESS_APK_URL = "apk_url";
    public static final String COLUMN_BUSINESS_PACKAGE_NAME = "package_name";
    public static final String COLUMN_BUSINESS_VERSION_NAME = "version_name";
    public static final String COLUMN_BUSINESS_VERSION_CODE = "version_code";
    public static final String COLUMN_BUSINESS_DESCRIPTION = "description";
    public static final String COLUMN_BUSINESS_RANK = "rank";
    public static final String COLUMN_BUSINESS_STRATEGYID = "strategy_id";
    public static final String COLUMN_BUSINESS_ITEMTYPE = "item_type";
    public static final String COLUMN_BUSINESS_LOCATE = "locate";
    public static final String COLUMN_BUSINESS_CONTAINER_ID = "container_id";
    public static final String COLUMN_BUSINESS_TITLE = "title";
    public static final String COLUMN_BUSINESS_SPANY = "spany";
    public static final String COLUMN_BUSINESS_SPANX = "spanx";

    public static final String COLUMN_STRATEGY_KEY = "strategy_key";
    public static final String COLUMN_STRATEGY_START = "start";
    public static final String COLUMN_STRATEGY_END = "end";
    public static final String COLUMN_STRATEGY_CHANNELID = "channel_id";

    public static final String COLUMN_STRATEGY_TABLE_ID = "strategy_table_id";

    public static final int BUSINESS_ITEM_TYPE_APP = 2;
    public static final int BUSINESS_ITEM_TYPE_WIDGET = 3;
    public static final int BUSINESS_ITEM_TYPE_FOLDER = 1;
    public static final int BUSINESS_ITEM_TYPE_APPSTORE = 4;
    public static final int BUSINESS_ITEM_TYPE_FOLDER_APP = 5;

    public static final int BUSINESS_LOCATE_HOME = 1;
    public static final int BUSINESS_LOCATE_APPLIST = 2;
    public static final int BUSINESS_LOCATE_BOTH = 3;

    // fav
    public static final String COLUMN_FAVORITES_CLICKCOUNT = "clickCount";

    // allapplist
    public static final String COLUMN_ALLAPPLIST_CLICKCOUNT = "clickCount";

    // action
    public static final String ACTION_DOWNLOAD_ADD = PACKAGENAME + ".download.add";
    public static final String ACTION_DOWNLOAD_STOP = PACKAGENAME + ".download.stop";
    // public static final String ACTION_DOWNLOAD_PAUSE = PACKAGENAME + ".download.pause";
    public static final String ACTION_DOWNLOAD_START = PACKAGENAME + ".download_start";
    public static final String ACTION_DOWNLOAD_PROGRESS = PACKAGENAME + ".download_progress";
    public static final String ACTION_DOWNLOAD_COMPOLETED = PACKAGENAME + ".download_completed";
    public static final String ACTION_DOWNLOAD_SHOWFAILMSG = PACKAGENAME + ".showMsg";
    public static final String FAIL_MSG = "DownloadFailMsg";

    public static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    public static final String ACTION_UNINSTALL_SHORTCUT = "com.android.launcher.action.UNINSTALL_SHORTCUT";

    // version update
    public final static int AUTO_CHECK_TYPE = 0;
    public final static int CLICK_CHECK_TYPE = 1;
    public static final String ACTION_UPDATE_CONFIRM = PACKAGENAME + ".update_confirm";
    public static final String UPDATE_URL = "update_url";
    public static final String UPDATE_TARGET_MAEKET = "update_market";
    public static final String CANCEL_UPDATE_TIME_KRY = "cancel_update_time";

    // alarm
    public static final String ACTION_ALARM_ALERT = PACKAGENAME + ".alarm";
    public static final int ALARM_TYPE_SYNC_RECOMMEND_INFO = 1;
    public static final int ALARM_TYPE_START = 2;
    public static final int ALARM_TYPE_END = 3;

    public static final String ACTION_ALARM_ALERT_STRATEGY = PACKAGENAME + ".alarm.strategy";
    public static final String ACTION_ALARM_ALERT_BUSINESS = PACKAGENAME + ".alarm.business";

    public static final String TYPE = "type";

    public static final String EXTRA_TIME = "extra_time";

    public static final int PROGRESS_INTERVAL = 1000;

    // business app status
    public static final int BUSINESS_APP_NULL = 1;
    public static final int BUSINESS_APP_INSTALL = 2;
    public static final int BUSINESS_APP_FIRSTRUN = 3;
    public static final int BUSINESS_APP_NORMAL = 4;

    // mimetype
    public static final String MIME_TYPE_BUSINESS_APK = "application";

    // download notify
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TOTAL = "extra_total";
    public static final String EXTRA_CURRENT = "extra_current";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_PROGRESS = "extra_progress";
    public static final String EXTRA_RESULT = "extra_result";
    public static final String EXTRA_NOTIFY_TYPE = "extra_notify_type";
    public static final String EXTRA_DEST_PATH = "extra_dest_path";
    public static final String EXTRA_URL = "extra_url";
    public static final String EXTRA_MIMETYPE = "extra_mimetype";

    // download status
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_FAILED = 1;
    public static final int RESULT_CANCELLED = 2;
    public static final int RESULT_FAILED_SDCARD = 3;
    public static final int RESULT_FAILED_NO_NETWORK = 4;
    public static final int RESULT_FAILED_SDCARD_INSUFFICIENT = 5;

    // download business apk
    public static final int NOTIFY_TYPE_BUSINESS_APK = 1;
    // new version download apk
    public static final int NOTIFY_TYPE_NEW_VERSION_UPDATE_APK = 2;

    // download theme icon btp
    public static final int NOTIFY_TYPE_THEME_BASE = 10;
    public static final int NOTIFY_TYPE_THEME_ICON = 11;
    // download wallpaper
    public static final int NOTIFY_TYPE_WALLPAPER = 20;

    public static final int DOWNLOAD_STATE_STOP = 100;
    public static final int DOWNLOAD_STATE_PAUSE = 101;
    public static final int DOWNLOAD_STATE_RUNNING = 105;
    public static final int DOWNLOAD_STATE_RESULT_SUCCESS = RESULT_SUCCESS;
    public static final int DOWNLOAD_STATE_RESULT_FAILED = RESULT_FAILED;
    public static final int DOWNLOAD_STATE_RESULT_CANCELLED = RESULT_CANCELLED;
    public static final int DOWNLOAD_STATE_UNKNOWN_ERROR = 99;

    // Message
    public static final int MESSAGE_SHORTCUT_INSTALLED = 100;
    public static final int MESSAGE_SHORTCUT_NOSPACE = 101;
    public static final int MESSAGE_SHORTCUT_UNINSTALLED = 102;
    public static final int MESSAGE_DOWNLOAD_FAILED = 103;

    // backup icon
    public static final int MAX_COUNT = 6;
    public static final int START_OFFSET = 0;

    // intent action
    public static final String ACTION_WIDGET_REFREDSH = "action_widget_refresh";

    // flag for push service
    public static final String PUSH_FLAG = "isPushService";
    
    // add shortcuts in batch
    public static final String EXTRA_SHORTCUT_INTENT_ARRAY = "com.android.launcher2.extra.shortcut.array.INTENT";
    public static final String EXTRA_SHORTCUT_NAME_ARRAY = "com.android.launcher2.extra.shortcut.array.NAME";
    public static final String EXTRA_SHORTCUT_ICON_ARRAY = "com.android.launcher2.extra.shortcut.array.ICON";
    public static final String EXTRA_SHORTCUT_ICON_RESOURCE_ARRAY = "com.android.launcher2.extra.shortcut.array.ICON_RESOURCE";

    // flag: add shortcuts in batch or not
    public static boolean BATCH_FLAG = false;
    
    // time interval for broadcast
    public static final long TIME_INTERVAL = 300;
    public final static ExecutorService mExecutorService = Executors.newCachedThreadPool();

}
