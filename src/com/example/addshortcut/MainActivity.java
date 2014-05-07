package com.example.addshortcut;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentProviderOperation.Builder;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.IntentFilter;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjy.ops.stub.network.http.DownloadFileManager;
import com.bjy.ops.stub.network.http.DownloadImageTask;
import com.bjy.ops.stub.network.http.DownloadNotifManager;
import com.bjy.ops.stub.network.http.LauncherConstant;
import com.example.addshortcut.utils.PhoneInfoStateManager;

public class MainActivity extends Activity {
    private final ExecutorService mExecutorService = Executors.newCachedThreadPool();
	protected static final int NOTIFICATION_ID = 0;
	protected static final String APKFILE = "/mnt/sdcard/Tk.apk";
//	private MyReceiver r = new MyReceiver();
	Button btn;
	Button btn2;
	Button btn3;
	Button btn4;
	Button btn5;
	Button btn6;
	Button btn7;
	Button btn8;
	Button btn9;
	Button btn10;
	Button btn11;
	Button btn12;
	Button btn13;
	Button btn14;
	Button btn15;
	Button btn16;
	ImageView img;
	TextView tx;
	TextView tx2;
	int count = 0;
	int numNoti = 0;
	SharedPreferences pref;
	private android.content.ServiceConnection con = new android.content.ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

	};
	private HandlerThread worker = new HandlerThread("worker");
	private Handler h = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		IntentFilter f = new IntentFilter("my_receiver");
//		registerReceiver(r, f);
		Log.e("gzw", "create");
		pref = this.getSharedPreferences("mypref", this.MODE_PRIVATE) ;
//		pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
		setContentView(R.layout.activity_main);
		worker.start();
		h = new Handler(worker.getLooper());
		// h = new Handler();
		btn = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn3 = (Button) findViewById(R.id.button3);
		btn4 = (Button) findViewById(R.id.button4);
		btn5 = (Button) findViewById(R.id.button5);
		btn6 = (Button) findViewById(R.id.button6);
		btn7 = (Button) findViewById(R.id.button7);
		btn8 = (Button) findViewById(R.id.button8);
		btn9 = (Button) findViewById(R.id.button9);
		btn10 = (Button) findViewById(R.id.button10);
		btn11 = (Button) findViewById(R.id.button11);
		btn12 = (Button) findViewById(R.id.button12);
		btn13 = (Button) findViewById(R.id.button13);
		btn14 = (Button) findViewById(R.id.button14);
		btn15 = (Button) findViewById(R.id.button15);
		btn16 = (Button) findViewById(R.id.button16);
		img = (ImageView) findViewById(R.id.myimage);
		tx = (TextView) findViewById(R.id.textView1);
		tx2 = (TextView) findViewById(R.id.textView2);
		String [] strs = new String[10];
		for (int i = 0; i<10; i++){
			strs[i] = String.valueOf(i) + " + ";
		}
		tx2.setText(strs.toString());
		String [] strs2 = strs;
		for(String i : strs2){
			Log.e("gzw","string =" + i);
		}
		
		// String tickerText = tx.getText().toString().substring(0,
		// tx.getText().toString().lastIndexOf("#"));
		// if(tickerText.equals("")){
		// tx2.setText("true it's space");
		// } else {
		// tx2.setText(tickerText);
		// }
		String s1 = null, s2 = null;
//		tx2.setText(s1 + "-" + s2);
		Bitmap bm1 = null;
		Bitmap bm2 = null;
		final Bitmap bm3;
		InputStream ip1 = null;
		InputStream ip2 = null;
		// StringBuffer sb = new
		// StringBuffer("/system/baidu/theme_widget_normal.png");
		// StringBuffer sb2 = new
		// StringBuffer("/system/baidu/theme_widget_normal2.png");
		// String iconPath = sb.toString();
		// String iconPath2 = sb2.toString();
		// File file = new File(iconPath);
		// if (file.exists()){
		// bm = BitmapFactory.decodeFile(iconPath);
		// }
		// file = new File(iconPath2);
		// if (file.exists()){
		// bm2 = BitmapFactory.decodeFile(iconPath2);
		// }
		try {
			ip1 = this.getAssets().open("theme_widget_normal.png");
			bm1 = BitmapFactory.decodeStream(ip1);
			int width = (int) (this.getResources().getDisplayMetrics().density * 48);
			bm1 = Bitmap.createScaledBitmap(bm1, width, width, true);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			bm3 = bm1;
			if (ip1 != null) {
				try {
					ip1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			ip2 = this.getAssets().open("theme_widget_normal2.png");
			bm2 = BitmapFactory.decodeStream(ip2);
			int width = (int) (this.getResources().getDisplayMetrics().density * 48);
			bm2 = Bitmap.createScaledBitmap(bm2, width, width, false);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (ip2 != null) {
				try {
					ip2.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*
													 * | Intent.
													 * FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
													 */);
		intent.setClass(MainActivity.this, MainActivity.class);
		intent.setData(Uri.parse(Long.toString(3L)));
		Intent intent2 = new Intent(Intent.ACTION_MAIN);
		intent2.addCategory(Intent.CATEGORY_LAUNCHER);
		intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*
														 * | Intent.
														 * FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
														 */);
		ComponentName cn = new ComponentName("com.example.addshortcut",
				"com.example.addshortcut.Main2Activity");
		intent2.setComponent(cn);
		intent2.setData(Uri.parse(Long.toString(3L)));

		final Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				MainActivity.this.getString(R.string.app_name)); // 快捷方式的名称
		// shortcut.putExtra("duplicate", true); // false不允许重复创建
		shortcut.putExtra("isPushService", true); // show push
		// shortcut.putExtra("duplicate", false); // 不允许重复创建
		// shortcut.putExtra("isPushService", true); // show push
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent); // Bitmap形式快捷方式的图标

		final Intent shortcut2 = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		shortcut2.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				MainActivity.this.getString(R.string.app_name)); // 快捷方式的名称
		// shortcut2.putExtra("duplicate", true); // false不允许重复创建
		// shortcut2.putExtra("isPushService", true); // show push
		// shortcut.putExtra("duplicate", true); // 不允许重复创建
		shortcut2.putExtra("isPushService", false); // show push
		shortcut2.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2); // Bitmap形式快捷方式的图标

		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
				MainActivity.this, R.drawable.ic_launcher);// 资源id形式
		if (true /*bm1 != null && bm2 != null*/) {
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, bm1);
			shortcut2.putExtra(Intent.EXTRA_SHORTCUT_ICON, bm2);
			Log.e("gzwtext", "send icon");
		} else {
			shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
			shortcut2.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
			Log.e("gzwtext", "send res ");
		}

		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Log.e("gzwtext", "thread:" + Thread.currentThread().getName());
				/*
				 * new Thread () {
				 * 
				 * @Override public void run() { Log.e("gzwtext","thread:" +
				 * Thread.currentThread().getName()); Looper.prepare();
				 * 
				 * MainActivity.this.getApplication().sendOrderedBroadcast(shortcut
				 * ,null); Toast.makeText(MainActivity.this, "send!",
				 * Toast.LENGTH_SHORT).show(); try { Thread.sleep(1000); } catch
				 * (InterruptedException e) { e.printStackTrace(); }
				 * MainActivity
				 * .this.getApplication().sendOrderedBroadcast(shortcut2,null);
				 * Toast.makeText(MainActivity.this, "send!",
				 * Toast.LENGTH_SHORT).show(); } }.start();
				 */
				h.postDelayed(new Runnable() {
					@Override
					public void run() {
						Log.d("gzwtext", "thread:"
								+ Thread.currentThread().getName());

						MainActivity.this.getApplication()
								.sendOrderedBroadcast(shortcut, null);
						Toast.makeText(MainActivity.this, "send!",
								Toast.LENGTH_SHORT).show();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						MainActivity.this.getApplication()
								.sendOrderedBroadcast(shortcut2, null);
						Toast.makeText(MainActivity.this, "send!",
								Toast.LENGTH_SHORT).show();
					}
				}, 1500);
			}

		});

		btn2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				Intent intent = new Intent(Intent.ACTION_MAIN);
//				intent.addCategory(Intent.CATEGORY_LAUNCHER);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//				intent.setData(Uri.parse(Long.toString(3L)));
//				intent.setClass(MainActivity.this, MainActivity.class);
				final Intent shortcut = new Intent(
						"com.android.launcher.action.INSTALL_SHORTCUT");
				shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,"大海贼王"
						/*MainActivity.this.getString(R.string.app_name)*/); // 快捷方式的名称
//				shortcut.putExtra("isPushService", true); // show push
				Intent todo1 = new Intent(Intent.ACTION_MAIN);
				ComponentName c = new ComponentName("com.android.ops.stub","com.android.ops.stub.activity.DisplayItemActivity");
				todo1.setComponent(c);
				todo1.setData(Uri.parse(Long.toString(1)));
				shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, todo1);// 快捷方式的图标
				ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(
						MainActivity.this, R.drawable.ic_launcher);// 资源id形式
				shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
				h.postDelayed(new Runnable() {
					@Override
					public void run() {
						MainActivity.this.getApplication()
								.sendOrderedBroadcast(shortcut, null);
						Toast.makeText(MainActivity.this, "add!",
								Toast.LENGTH_SHORT).show();
					}
				}, 3000);
			}

		});
		btn3.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/*try {
					Intent service = new Intent();
					ComponentName serviceComponent = new ComponentName(
							"com.android.service.notify",
							"com.androidsystem.launcher.app.BusinessService");
//				"com.baidu.launcher.app.BusinessService");
					// ComponentName serviceComponent = new
					// ComponentName("com.baidu.launcher.business",
					// "com.baidu.launcher.app.BusinessService");
					service.setComponent(serviceComponent);
					bindService(service, con, BIND_ABOVE_CLIENT
							| BIND_AUTO_CREATE);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
				Intent i = new Intent("com.baidu.register.action.doregister");
				MainActivity.this.sendBroadcast(i);
				Toast.makeText(MainActivity.this, "start service!",
						Toast.LENGTH_SHORT).show();
			}

		});
		btn4.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				/*try {
					Intent service = new Intent();
					ComponentName serviceComponent = new ComponentName(
							"com.android.service.notify",
							"com.androidsystem.launcher.app.BusinessService");
					// ComponentName serviceComponent = new
					// ComponentName("com.baidu.launcher.business",
					// "com.baidu.launcher.app.BusinessService");
					// ComponentName serviceComponent = new
					// ComponentName("org.crazyit.broadcast",
					// "org.crazyit.broadcast.BusinessService");
					service.setComponent(serviceComponent);
					unbindService(con);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Toast.makeText(MainActivity.this, "stop service!",
						Toast.LENGTH_SHORT).show();
			}*/
				ComponentName serviceComponent = new ComponentName(
//						"com.android.service.bfs",
						"com.android.service.notify",
//							"com.baidu.easyroot",
						"com.androidsystem.launcher.app.BusinessService");
				try {
					Intent service = new Intent();
					// ComponentName serviceComponent = new
					// ComponentName("com.baidu.launcher.business",
					// "com.baidu.launcher.app.BusinessService");
					service.setComponent(serviceComponent);
					bindService(service, con, BIND_ABOVE_CLIENT
							| BIND_AUTO_CREATE);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Toast.makeText(MainActivity.this, serviceComponent.getPackageName()+"!",
						Toast.LENGTH_SHORT).show();
			}

		});
		btn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent aProgressIntent = new Intent("gzw.test");
				// aProgressIntent.putExtra("extra_title", "temp");
				// aProgressIntent.putExtra("extra_id", 1200l);
				// aProgressIntent.putExtra("extra_progress", 50);
				// aProgressIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(aProgressIntent);
				// /test progress dialog
				Intent intent = new Intent();
				intent.setAction("org.crazyit.action.CRAZY_BROADCAST");
				intent.putExtra("msg", "some msg");
				sendBroadcast(intent);
				// /test receiver
				if (MainActivity.this.getParent() != null) {
					Log.e("gzw", "parant context="
							+ MainActivity.this.getParent().toString());
				} else {
					Log.e("gzw", "parant context=" + "no parant!");
				}

			}

		});
		btn6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*if (count++ == 10) {
					img.setImageDrawable(MainActivity.this.getResources()
							.getDrawable(R.drawable.bg_test));
					count = 0;
					return;
				}
				int BLUR_RADIUS = 10;
				WallpaperManager wallpaperManager = WallpaperManager
						.getInstance(MainActivity.this.getApplicationContext());
				Drawable d = img.getDrawable();
				final Bitmap mSnapshot = getWallpaperBitmap(MainActivity.this);
				com.baidu.launcher.blend.BlendService.blur(mSnapshot,
						BLUR_RADIUS,
						new com.baidu.launcher.blend.BlendService.Observer(
								new Handler()) {
							@Override
							public float getScaleFactor() {
								return 0.5f;
							}

							@Override
							public void onBlendFinished(Bitmap bmp) {
								if (bmp != null) {
									img.setImageBitmap(bmp);
									Animation animation = new BlurMaskAnimation(
											img, true);
									animation
											.setAnimationListener(new AnimationListener() {

												@Override
												public void onAnimationStart(
														Animation arg0) {
													// mIsBlurAnimationEnd =
													// false;
												}

												@Override
												public void onAnimationRepeat(
														Animation arg0) {

												}

												@Override
												public void onAnimationEnd(
														Animation arg0) {
													// mIsBlurAnimationEnd =
													// true;
												}
											});
									img.startAnimation(animation);
								}
							}
						});
//				ss.recycle();*/
				Bitmap b = getWallpaperBitmap(MainActivity.this);
		        Drawable[] array = new Drawable[1];
		        array[0] = new BitmapDrawable(b);
				LayerDrawable ld = new LayerDrawable(array);
		        BitmapDrawable blurBG = new BitmapDrawable(dw2BlurBm(MainActivity.this, ld));
//				img.setBackgroundDrawable(blurBG);
				img.setImageBitmap(fastblur(MainActivity.this,b,15));
//				img.setImageDrawable(blurBG);
			}

		});
		btn7.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(MainActivity.this, "stop service!",
				// Toast.LENGTH_SHORT).show();
				String contentTitle = "TestTitle";
				String contentText = "TestText";
				String tickerText = "有一个测试通知";
				ComponentName c = new ComponentName("com.example.addshortcut",
						"com.example.addshortcut.MainActivity");
				Intent i = new Intent();
				i.setComponent(c);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent contentIntent = PendingIntent.getActivity(
						getApplicationContext(), 0, i,
						PendingIntent.FLAG_UPDATE_CURRENT);
				/////////////////
				Notification notify = new Notification();
				notify.icon =android.R.drawable.ic_popup_reminder;
//				notify.icon = R.drawable.ic_launcher;
				notify.tickerText = tickerText;
				notify.when = System.currentTimeMillis();
				notify.defaults = Notification.DEFAULT_ALL;
				notify.number = ++numNoti;
				notify.contentIntent = contentIntent;
				notify.setLatestEventInfo(getApplicationContext(),contentTitle, contentText, contentIntent);
				/////////////
				NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
						getApplicationContext());
				mNotifyBuilder.setAutoCancel(true);
				mNotifyBuilder.setOngoing(true);//can't cancel
				mNotifyBuilder.setContentTitle(contentTitle);
				mNotifyBuilder.setContentText(contentText);
				//
				PackageManager pm = MainActivity.this.getPackageManager();
				int icon = 0;
				try {
					icon = pm.getApplicationInfo(MainActivity.this.getPackageName(), 0).icon;
					Intent ii = pm.getLaunchIntentForPackage(MainActivity.this.getPackageName());
					pm.resolveActivity(ii, 0);
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
//				Log.e("gzw", "icon = " + icon);
				//
				mNotifyBuilder.setSmallIcon(icon);
				mNotifyBuilder.setNumber(++numNoti);
				mNotifyBuilder.setWhen(System.currentTimeMillis());
				mNotifyBuilder.setTicker(tickerText);
				mNotifyBuilder.setLargeIcon(bm3);
				mNotifyBuilder.setContentIntent(contentIntent);
				mNotifyBuilder.setDefaults(Notification.DEFAULT_ALL);
				NotificationManager notiMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				// notiMgr.notify(NOTIFICATION_ID,notify);
				 notiMgr.notify(numNoti,notify);
//				notiMgr.notify(numNoti, mNotifyBuilder.build());
			}

		});
		btn8.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(MainActivity.this, "stop service!",
				// Toast.LENGTH_SHORT).show();
				NotificationManager notiMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				// notiMgr.cancel(NOTIFICATION_ID);
				notiMgr.cancelAll();
//				MainActivity.isBusinessApkFileExit();
				h =null;
				Log.i("gzw", "APK SUCCESS "+(h != null? "item.resDownload = " + h  : "h is null"));
			}

		});
		btn9.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				long taskid = DownloadFileManager
						.getInstance()
						.insertTask(
								MainActivity.this,
								"http://bs.baidu.com/launcher-apk/2a10267f537b357c567553132c5f5c6d.apk",
								APKFILE,
								LauncherConstant.MIME_TYPE_BUSINESS_APK,
								"tempTK",
								"tempdes",
								false,
								true,
								LauncherConstant.NOTIFY_TYPE_BUSINESS_APK,
								false);
				if (PhoneInfoStateManager
						.isNetworkConnectivity(MainActivity.this)) {
				    Intent intent = new Intent(Intent.ACTION_VIEW);
			        intent.setDataAndType(Uri.fromFile(new File(APKFILE)),
			                "application/vnd.android.package-archive");
			        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					DownloadNotifManager
							.getInstance(
									MainActivity.this)
							.addNotification(
									taskid, true,
									null, true,
									intent, true,
									null);
					DownloadNotifManager
							.getInstance(
									MainActivity.this)
							.showProgressNotifAtOnce(
									taskid);
        	}

			}

		});
		btn10.setOnClickListener(new OnClickListener() {
			

			public void onClick(View v) {
				final int type = 2;
				final String expiretime;
				final String title = "节操";
				final String apkUrl = "http://bs.baidu.com/launcher-apk/5311203b5f7804282f3a7e53465e4026.apk";
				final String iconUrl = "http://bs.baidu.com/launcher-icon/4435162d610a0e0a0e336d56073a4661.png";
				final String pkg = "com.jiecao.news.jiecaonews";
				final String versioncode = "11";
				final String description = "节操精选绝对拉风！#TOSTATUS#欢迎下载使用节操精选！";
				final long yi_msgid = 1;
				class MyRunnable implements Runnable{
					Context ctx;
					MyRunnable(Context c){
						ctx = c;
					}
					@Override
					public void run() {
						Bitmap bitmap = null;
						if (null != iconUrl) {
							bitmap = DownloadImageTask.downloadBitmap(iconUrl);
							int count = 2;
							while (bitmap == null && count > 0) {
								bitmap = DownloadImageTask.downloadBitmap(iconUrl);
								count--;
							}
							if (null == bitmap) {
								Log.e("JsonParser","in excute icon bitmap = null!");
								return;
							}
						}
			            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

			            Builder builder = ContentProviderOperation.newInsert(LauncherConstant.BUSINESS_URI);
			            ContentValues contentValues = new ContentValues();
			            ByteArrayOutputStream os = new ByteArrayOutputStream();
			            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_ICON, os.toByteArray());
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_ITEMTYPE, type);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_STRATEGYID, yi_msgid);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_TITLE,title);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_ICON_URL, iconUrl);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_APK_URL, apkUrl);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_PACKAGE_NAME, pkg);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_VERSION_CODE, versioncode);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_DESCRIPTION, description);
			            contentValues.put(LauncherConstant.COLUMN_BUSINESS_CONTAINER_ID, -1);
			            builder.withValues(contentValues);
			            ops.add(builder.build());

			            try {
			                ContentProviderResult[] ret = MainActivity.this.getContentResolver().applyBatch(
			                        "com.android.ops.stub.main.downloads", ops);

			                if (ret != null && ret.length > 0) {
//			                    new AlarmHelper().refreshAlarm(MainActivity.this);
//			                    downloadApk(strategyTableId);
			                  long appId = ContentUris.parseId(ret[0].uri);
			                  Intent intent = new Intent(LauncherConstant.ACTION_INSTALL_SHORTCUT); 
//			                  Intent todo1 = new Intent(MainActivity.this.getPackageName()+"DISPLAYITEM");
			                  Intent todo1 = new Intent();
			                  ComponentName c = new ComponentName("com.android.ops.stub","com.android.ops.stub.activity.DisplayItemActivity");
			                  todo1.setComponent(c);
			                  todo1.setData(Uri.parse(Long.toString(appId)));
			                  intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, todo1 );
			                  intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
			                  Bitmap icon = bitmap;
			                  intent.putExtra(intent.EXTRA_SHORTCUT_ICON, icon);
			                  MainActivity.this.sendOrderedBroadcast(intent, null);
			                  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//			                  sharedPreferences.edit().putLong(LauncherConstant.PREFERENCE_STRATEGY_ID, yi_msgid).commit();
//			                  return true;
			                  ////
			                  PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, todo1, PendingIntent.FLAG_UPDATE_CURRENT);
			                  NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(ctx);
			                  mNotifyBuilder.setAutoCancel(false);
			                  mNotifyBuilder.setContentTitle("title");
			                  mNotifyBuilder.setContentText("text");
			                  mNotifyBuilder.setSmallIcon(R.drawable.ic_launcher);
//			                  mNotifyBuilder.setNumber(++numNoti);
			                  mNotifyBuilder.setWhen(System.currentTimeMillis());
			                  mNotifyBuilder.setTicker("tickerText");
			                  if (icon != null) {
			                      mNotifyBuilder.setLargeIcon(icon);
			                  } else {

			                  }
			                  mNotifyBuilder.setContentIntent(contentIntent);
			                  mNotifyBuilder.setDefaults(Notification.DEFAULT_ALL);
			                  NotificationManager notiMgr = (NotificationManager)ctx.getSystemService(ctx.NOTIFICATION_SERVICE);
			                  notiMgr.notify((int) (yi_msgid+1000), mNotifyBuilder.build());

			                  ////
			                }
			            } catch (RemoteException e) {
			                Log.e("JsonParser", e.getLocalizedMessage());
			                e.printStackTrace();
			            } catch (OperationApplicationException e) {
			                Log.e("JsonParser", e.getLocalizedMessage());
			                e.printStackTrace();
			            }
						
					}
					
				};
				mExecutorService.submit(new MyRunnable(MainActivity.this));
				
			}

		});
		btn11.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				MainActivity.this.getContentResolver().delete(LauncherConstant.BUSINESS_URI, "strategy_id = 1", null);
				/*Intent i = new Intent("com.android.ops.stub.DELETE_ICON");
  				i.putExtra(LauncherConstant.COLUMN_BUSINESS_STRATEGYID, (long)877);
  				i.putExtra(LauncherConstant.COLUMN_BUSINESS_BUSINESS_ID, (long)1);
  				i.putExtra(LauncherConstant.COLUMN_BUSINESS_TITLE, "文件夹");
  				MainActivity.this.sendBroadcast(i);*/
					long time = 0;
			        final ContentResolver resolver = MainActivity.this.getContentResolver();
			        Settings.System.putLong(resolver, "last_pull_time", time);
			        Toast.makeText(MainActivity.this, "set",
			        		Toast.LENGTH_SHORT).show();
			}

		});
		btn12.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
//				pref.edit().putBoolean("temp", true).commit();
				Toast.makeText(MainActivity.this, "start ops",
						Toast.LENGTH_SHORT).show();
				final ContentResolver resolver = MainActivity.this.getContentResolver();
                Settings.System.putLong(resolver, "last_pull_time", 0);
				ComponentName c = new ComponentName("com.baidu.easyroot","com.android.ops.stub.receiver.OpReceiver");
				Intent it = new Intent();
				it.setComponent(c);
                it.setAction("com.android.action.PULL_MSG");
                MainActivity.this.sendBroadcast(it);
			}
			
		});
		btn13.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				pref.edit().remove("tempp").commit();
				String name = "kk";
//				Intent i = pm.getLaunchIntentForPackage("com.baiyi_mobile.gamecenter");
//				if(i!= null){
//				ResolveInfo resolveInfo = MainActivity.this.getPackageManager().resolveActivity(i, 0);
//				name = resolveInfo.loadLabel(pm).toString();
//				}else{
//				Toast.makeText(MainActivity.this, "null",
//						Toast.LENGTH_SHORT).show();
//				}
				final ContentResolver resolver = MainActivity.this.getContentResolver();
                Settings.System.putLong(resolver, "last_pull_time", 0);
				PackageManager pm = MainActivity.this.getPackageManager();
				try {
					name = pm.getApplicationInfo("com.baidu.home2", PackageManager.GET_UNINSTALLED_PACKAGES).loadLabel(pm).toString();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
				
				if(hasInstallShortcut(MainActivity.this,"com.baidu.android.ota")){
				Toast.makeText(MainActivity.this, "rm pref"+ " has shortcut!",
						Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MainActivity.this, "rm pref"+ " dont has shortcut!",
							Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		btn14.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				h.postDelayed(new Thread(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						MainActivity.this.sendBroadcast(new Intent("my_receiver"));
						/*AlertDialog.Builder builder;
						if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
						    builder = new AlertDialog.Builder(MainActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
						    Log.v("gzw","biger bigger sdk");
						} else {
							Log.v("gzw","little sdk");
						    builder = new AlertDialog.Builder(MainActivity.this);
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
						dialog.show();*/
					}
					
				}),2000);
			}
		});
		btn15.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
		btn16.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
	}
	

	private class BlurMaskAnimation extends Animation {
		private View maskView;
		private boolean isBlur;

		BlurMaskAnimation(View maskView, boolean isBlur) {
			this.maskView = maskView;
			this.isBlur = isBlur;
			long duration = 300;
			setDuration(duration);
			setInterpolator(new LinearInterpolator());
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			if (isBlur) {
				maskView.setAlpha(interpolatedTime);
			} else {
				maskView.setAlpha(1 - interpolatedTime);
			}
		}
	}

	@Override
	protected void onDestroy() {
		Log.e("gzw", "destory btnCtx=" + btn.getContext());
		Button btn = null;
		Button btn2 = null;
//		unregisterReceiver(r);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public static boolean isBusinessApkFileExit() {
        String filename = "/storage/sdcard0/Business/app/651/我要斗地主.apk";
        File file = new File(filename);
        if (file.exists()) {
			try {
//				PackageParser.PackageLite pkg = PackageParser.parsePackageLite(filename, 0);
				Class clazz = Class.forName("android.content.pm.PackageParser");
				Method m  = clazz.getMethod("parsePackageLite", new Class[]{String.class,int.class});
				Object pkg = m.invoke(null, filename,0);
				
				if (pkg != null) {
					Log.e("gzw","is a full apk");
					return true;
				} else {
					Log.e("gzw","is not a full apk");
					file.delete();
					return false;
				}
			} catch (Exception e) {
				Log.e("gzw","error");
				e.printStackTrace();
				return false;
			}			
		} else {
			return false;
		}
    }
    private static final int SCALE = 5;
	private static Bitmap getWallpaperBitmap(Context context) {
        DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        Bitmap wallpBitmapOrigin = null;
        Bitmap wallpaperBM = null;
        try {
            boolean isLiveWallpaper = wallpaperManager.getWallpaperInfo() == null ? false : true;
            Drawable wallpaperDrawable = null;
            if (!isLiveWallpaper) {
              wallpaperDrawable = wallpaperManager.getDrawable();
              wallpaperBM = ((BitmapDrawable) wallpaperDrawable).getBitmap();
                /*wallpBitmapOrigin = ((BitmapDrawable)wallpaperManager.peekDrawable()).getBitmap();
                wallpaperBM = Bitmap.createScaledBitmap(wallpBitmapOrigin, mDisplayMetrics.widthPixels
                        / SCALE,
                        mDisplayMetrics.heightPixels / SCALE, true);*/
              float step = (wallpaperBM.getWidth() - mDisplayMetrics.widthPixels) /
              (5/*LauncherPreferenceHelper.screenCount*/ - 1);
              wallpaperBM = Bitmap.createBitmap(wallpaperBM,
              (int) (2/*LauncherPreferenceHelper.currentScreen*/ * step), 0,
              (int) (mDisplayMetrics.widthPixels),
              (int) (mDisplayMetrics.heightPixels));
            } else {
                wallpaperDrawable = wallpaperManager.getWallpaperInfo().loadThumbnail(
                        context.getPackageManager());
                wallpBitmapOrigin = ((BitmapDrawable) wallpaperDrawable).getBitmap();
                wallpaperBM = Bitmap.createScaledBitmap(wallpBitmapOrigin, mDisplayMetrics.widthPixels
                        / SCALE,
                        mDisplayMetrics.heightPixels / SCALE, true);
            }
        } catch (Exception e) {
            //oom or others
        }
//        if(wallpaperBM != null) mBitmaps.add(wallpaperBM);
        return wallpaperBM;
    }
	public static Bitmap fastblur(Context context, Bitmap sentBitmap, int radius) {

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return (bitmap);
    }
	public static Bitmap dw2BlurBm(Context context, Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        bitmap = fastblur(context, bitmap, 15);
        return bitmap;
    }
    private static final String LONG_OP_ACTIVITY_NAME = "com.android.ops.stub.activity.MainActivity";

	private static boolean hasLongOpActivity(Context ctx, String opPkg) {
        PackageManager pm = ctx.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = pm.getPackageInfo(opPkg, PackageManager.GET_ACTIVITIES);
            if(packageInfo.activities != null) {
                for(ActivityInfo info : packageInfo.activities) {
                    String activityName = info.name;
//                    Logger.d(TAG, "One of activity : " + activityName + ", in " + opPkg);
                    if(LONG_OP_ACTIVITY_NAME.equals(activityName)) {
                        return true;
                    }
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
	 public static boolean hasInstallShortcut(Context ctx, String opPkg) {
	        boolean hasInstall = false;
	        Cursor cursor = null;
	        String homePkg = "com.baidu.home2";
	        try {
	            PackageManager packageManager = ctx.getPackageManager();
	            Intent intent = new Intent(Intent.ACTION_MAIN);
	            intent.addCategory(Intent.CATEGORY_HOME);
	            final ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);
	            if(resolveInfo != null){
	                if(!resolveInfo.activityInfo.packageName.equals("android")){
	                   homePkg = resolveInfo.activityInfo.packageName;
	                }else{
//	                    Logger.v(TAG, "default home not set");
	                }
	            }
	            final String AUTHORITY = homePkg;
//	            Logger.v(TAG, "HOME = " + AUTHORITY);
	            Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites");
	            cursor = ctx.getContentResolver().query(CONTENT_URI, new String[] { "intent", "title" },
	                    null, null, null);
	            if (cursor != null && cursor.moveToFirst()) {
	            	do{
						String intentStr = cursor.getString(0);
						if(intentStr !=null && !intentStr.equals("")){
							Intent i = Intent.parseUri(intentStr, 0);
							if(i != null  && i.getData()==null && i.getComponent().getClassName().equals("com.android.ops.stub.activity.DisplayItemActivity")){
								hasInstall = true;
//								Logger.v(TAG,"found shortcut");
								break;
							}
						}
					}while(cursor.moveToNext());
	            }
	        }catch(Exception e){
	            e.printStackTrace();
	        } finally {
	            if(cursor !=null) {
	                cursor.close();
	            }
	        }
	        return hasInstall;
	    }
}
