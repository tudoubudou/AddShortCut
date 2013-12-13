package com.baidu.launcher.blend;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;


public class BlendService {

    public abstract static class Observer {
        final Handler mHandler;

        public Observer(Handler handler) {
            mHandler = handler;
        }

        public float getScaleFactor() { return 1.0f; }

        abstract public void onBlendFinished(Bitmap bmp);
    }

    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(1);
    private static BlendTask mCurrentTask;

    private BlendService() {
        
    }

    public static void blur(Bitmap bitmap, int radius, Observer observer) {
        interrupt();
        mCurrentTask = new BlurBlend.Task(bitmap, radius, observer);
        mExecutorService.submit(mCurrentTask);
    }

    public static void interrupt() {
        if (mCurrentTask != null) {
            mCurrentTask.interrupt();
        }
    }

    abstract static class BlendTask implements Runnable {
        protected Bitmap mSrcBitmap;
        private boolean mInterrupted = false;
        private final Observer mObserver;
        
        BlendTask(Bitmap bitmap, Observer observer) {
            mSrcBitmap = bitmap;
            mObserver = observer;
        }

        @Override
        public void run() {
            boolean result = false;
            try {
                final float scale = Math.min(mObserver.getScaleFactor(), 1.0f);
                //mSrcBitmap = scaleBmp(mSrcBitmap, scale);
                result = blend();
            } catch (Exception e) {
                result = false;
            }
            notifyObserver(result ? mSrcBitmap : null);
        }

        private static Bitmap scaleBmp(Bitmap srcBmp, float scale) {
            if (scale < 1.0f) {
                final int w = srcBmp.getWidth();
                final int h = srcBmp.getHeight();
                final Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                Bitmap scaledBmp = Bitmap.createBitmap(srcBmp, 0, 0, w, h, matrix, false);
                srcBmp.recycle();
                return scaledBmp;
            } else {
                return srcBmp;
            }
        }

        abstract protected boolean blend();

        public void interrupt() {
            mInterrupted = true;
        }

        protected boolean isInterrupted() {
            return mInterrupted;
        }
        
        protected void notifyObserver(final Bitmap bmp) {
            if (mCurrentTask == null || 
                mCurrentTask == BlendTask.this || 
                mCurrentTask.mObserver != mObserver) {
                if (mObserver!=null && mObserver.mHandler!=null) {
                    mObserver.mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mObserver.onBlendFinished(bmp);
                        }
                    });
                }
            }
        }
    }

}
