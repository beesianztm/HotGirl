package com.hotgirl.zeptomobile.hotgirl.staggeredgridapp;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.hotgirl.zeptomobile.hotgirl.volley.BitmapLruCache;

public class StaggeredDemoApplication extends Application {

    private static Context applicationContext;
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;
    private static BitmapLruCache mBitmapCache;

    public static boolean INIT_FLAG = true;

    public void onCreate() {
        super.onCreate();

        applicationContext = this.getApplicationContext();

        mRequestQueue = Volley.newRequestQueue(applicationContext);
        //long size = Runtime.getRuntime().maxMemory()/4;
        mBitmapCache = new BitmapLruCache(30);//(int)size);
        mImageLoader = new ImageLoader(mRequestQueue, mBitmapCache);
    }

    public static RequestQueue getRequestQueue(Context mContext) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            throw new IllegalStateException("ImageLoader not initialized");
        }
    }

}