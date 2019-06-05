package com.uy.csi.sige.image;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

/**
 * Created by dtrinidad on 04/08/2016.
 */
public class LruCacheManager {

    private LruCache<String, Bitmap> mMemoryCache;

    private static LruCacheManager instance;

    public static LruCacheManager getInstance() {
        if(instance == null) {
            instance = new LruCacheManager();
            instance.init();
        }

        return instance;
    }

    private void init() {

        // We are declaring a cache of 6Mb for our use.
        // You need to calculate this on the basis of your need
        mMemoryCache = new LruCache<String, Bitmap>(50 * 1024 * 1024) {
            @Override
            protected int sizeOf(String key, Bitmap bitmapDrawable) {

                // The cache size will be measured in kilobytes rather than
                // number of items.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                    return bitmapDrawable.getByteCount() ;
                } else {
                    return bitmapDrawable.getRowBytes() * bitmapDrawable.getHeight();
                }
            }

            @Override
            protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {

                super.entryRemoved(evicted, key, oldValue, newValue);
                //oldValue.setIsCached(false);


            }
        };

    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmapDrawable) {
        if (getBitmapFromMemCache(key) == null) {

            // The removed entry is a recycling drawable, so notify it
            // that it has been added into the memory cache
            //bitmapDrawable.setIsCached(true);
            mMemoryCache.put(key, bitmapDrawable);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public void clear() {
        mMemoryCache.evictAll();
    }
}
