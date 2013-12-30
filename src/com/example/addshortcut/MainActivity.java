package com.example.addshortcut;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    protected static final int NOTIFICATION_ID = 0;
    Button btn;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    ImageView img;
    TextView tx;
    TextView tx2;
    int count = 0;
    int numNoti = 0;
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
        Log.e("gzw", "create");

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
        img = (ImageView) findViewById(R.id.myimage);
        tx = (TextView) findViewById(R.id.textView1);
        tx2 = (TextView) findViewById(R.id.textView2);
//        String tickerText = tx.getText().toString().substring(0, tx.getText().toString().lastIndexOf("#"));
//        if(tickerText.equals("")){
//            tx2.setText("true it's space");
//        } else {
//            tx2.setText(tickerText);
//        }
        String s1 =null,s2=null;
            tx2.setText(s1 + "-" + s2);
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
        ComponentName cn = new ComponentName("com.example.addshortcut", "com.example.addshortcut.Main2Activity");
        intent2.setComponent(cn);
        intent2.setData(Uri.parse(Long.toString(3L)));

        final Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, MainActivity.this.getString(R.string.app_name)); // 快捷方式的名称
        // shortcut.putExtra("duplicate", true); // false不允许重复创建
        shortcut.putExtra("isPushService", true); // show push
        // shortcut.putExtra("duplicate", false); // 不允许重复创建
        // shortcut.putExtra("isPushService", true); // show push
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent); // Bitmap形式快捷方式的图标

        final Intent shortcut2 = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut2.putExtra(Intent.EXTRA_SHORTCUT_NAME, MainActivity.this.getString(R.string.app_name)); // 快捷方式的名称
        // shortcut2.putExtra("duplicate", true); // false不允许重复创建
        // shortcut2.putExtra("isPushService", true); // show push
        // shortcut.putExtra("duplicate", true); // 不允许重复创建
        shortcut2.putExtra("isPushService", false); // show push
        shortcut2.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent2); // Bitmap形式快捷方式的图标

        ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(MainActivity.this, R.drawable.ic_launcher);// 资源id形式
        if (/* false */bm1 != null && bm2 != null) {
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
                        Log.d("gzwtext", "thread:" + Thread.currentThread().getName());

                        MainActivity.this.getApplication().sendOrderedBroadcast(shortcut, null);
                        Toast.makeText(MainActivity.this, "send!", Toast.LENGTH_SHORT).show();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        MainActivity.this.getApplication().sendOrderedBroadcast(shortcut2, null);
                        Toast.makeText(MainActivity.this, "send!", Toast.LENGTH_SHORT).show();
                    }
                }, 1500);
            }

        });

        btn2.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                intent.setData(Uri.parse(Long.toString(3L)));
                intent.setClass(MainActivity.this, MainActivity.class);
                final Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, MainActivity.this.getString(R.string.app_name)); // 快捷方式的名称
                shortcut.putExtra("isPushService", true); // show push
                shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);// 快捷方式的图标
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.getApplication().sendOrderedBroadcast(shortcut, null);
                        Toast.makeText(MainActivity.this, "delete!", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }

        });
        btn3.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent service = new Intent();
                    ComponentName serviceComponent = new ComponentName("com.android.service.bfs", "com.baidu.launcher.app.BusinessService");
//                    ComponentName serviceComponent = new ComponentName("org.crazyit.broadcast", "org.crazyit.broadcast.BusinessService");
                    service.setComponent(serviceComponent);
                    bindService(service, con, BIND_ABOVE_CLIENT | BIND_AUTO_CREATE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "start service!", Toast.LENGTH_SHORT).show();
            }

        });
        btn4.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent service = new Intent();
                    ComponentName serviceComponent = new ComponentName("com.android.service.bfs", "com.baidu.launcher.app.BusinessService");
//                    ComponentName serviceComponent = new ComponentName("org.crazyit.broadcast", "org.crazyit.broadcast.BusinessService");
                    service.setComponent(serviceComponent);
                    unbindService(con);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(MainActivity.this, "stop service!", Toast.LENGTH_SHORT).show();
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
            	///test progress dialog
            	Intent intent = new Intent();
				intent.setAction("org.crazyit.action.CRAZY_BROADCAST");
				intent.putExtra("msg" , "some msg");
				sendBroadcast(intent);
				///test receiver
                if (MainActivity.this.getParent() != null) {
                    Log.e("gzw", "parant context=" + MainActivity.this.getParent().toString());
                } else {
                    Log.e("gzw", "parant context=" + "no parant!");
                }

            }

        });
        btn6.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (count++ == 10) {
                    img.setImageDrawable(MainActivity.this.getResources().getDrawable(R.drawable.bg_test));
                    count = 0;
                    return;
                }
                int BLUR_RADIUS = 10;
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this.getApplicationContext());
                Drawable d = img.getDrawable();
                // Drawable d = wallpaperManager.getDrawable();
                // Bitmap ss = null;
                // if (img.getDrawable() instanceof BitmapDrawable) {
                // // Ensure the bitmap has a density.
                // BitmapDrawable bitmapDrawable = (BitmapDrawable)
                // img.getDrawable();
                // ss = bitmapDrawable.getBitmap();
                // }

                final Bitmap ss = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                // final Bitmap ss = Bitmap.createScaledBitmap(ss2,
                // img.getWidth(), img.getHeight(),true);
                final Canvas canvas = new Canvas();
                canvas.setBitmap(ss);
                d.draw(canvas);
                Bitmap mSnapshot = Bitmap.createBitmap(ss, 0, 0, ss.getWidth(), ss.getHeight());
                com.baidu.launcher.blend.BlendService.blur(mSnapshot, BLUR_RADIUS, new com.baidu.launcher.blend.BlendService.Observer(new Handler()) {
                    @Override
                    public float getScaleFactor() {
                        return 0.5f;
                    }

                    @Override
                    public void onBlendFinished(Bitmap bmp) {
                        if (bmp != null) {
                            img.setImageBitmap(bmp);
                            Animation animation = new BlurMaskAnimation(img, true);
                            animation.setAnimationListener(new AnimationListener() {

                                @Override
                                public void onAnimationStart(Animation arg0) {
                                    // mIsBlurAnimationEnd = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation arg0) {

                                }

                                @Override
                                public void onAnimationEnd(Animation arg0) {
                                    // mIsBlurAnimationEnd = true;
                                }
                            });
                            img.startAnimation(animation);
                        }
                    }
                });
                ss.recycle();

            }

        });
        btn7.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "stop service!",
                // Toast.LENGTH_SHORT).show();
                String contentTitle = "TestTitle";
                String contentText = "TestText";
                String tickerText = "有一个测试通知";
                ComponentName c = new ComponentName("com.example.addshortcut","com.example.addshortcut.MainActivity");
                Intent i = new Intent();
                i.setComponent(c);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
//                Notification notify  = new Notification();
//                notify.icon = R.drawable.ic_launcher;
//                notify.tickerText = tickerText;
//                notify.when = System.currentTimeMillis();
//                notify.defaults = Notification.DEFAULT_ALL;
//                notify.number = ++numNoti;
//                notify.setLatestEventInfo(getApplicationContext(), contentTitle, contentText, contentIntent);
                NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(getApplicationContext());
                mNotifyBuilder.setAutoCancel(true);
                mNotifyBuilder.setContentTitle(contentTitle);
                mNotifyBuilder.setContentText(contentText);
                mNotifyBuilder.setSmallIcon(R.drawable.ic_launcher);
                mNotifyBuilder.setNumber(++numNoti);
                mNotifyBuilder.setWhen(System.currentTimeMillis());
                mNotifyBuilder.setTicker(tickerText);
                mNotifyBuilder.setLargeIcon(bm3);
                mNotifyBuilder.setContentIntent(contentIntent);
                mNotifyBuilder.setDefaults(Notification.DEFAULT_ALL);
                NotificationManager notiMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                notiMgr.notify(NOTIFICATION_ID,notify);
//                notiMgr.notify(numNoti,notify);
                notiMgr.notify(numNoti,mNotifyBuilder.build());
            }

        });
        btn8.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                // Toast.makeText(MainActivity.this, "stop service!",
                // Toast.LENGTH_SHORT).show();
                NotificationManager notiMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                notiMgr.cancel(NOTIFICATION_ID);
                notiMgr.cancelAll();
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
        protected void applyTransformation(float interpolatedTime, Transformation t) {
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
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
