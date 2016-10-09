package com.yc.BaiSiBuDeJie.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.yc.BaiSiBuDeJie.utils.ApplicationUtil;
import com.yc.BaiSiBuDeJie.utils.FileUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by yangchun on 2015/8/4.
 */
public class LruCacheManager {

    private static LruCache<String, String> mMemoryStringCache;
    private static LruCache<String, Bitmap> mMemoryBitmapCache;
    private static DiskLruCache mDiskCache;

    private static int memoryCacheSize;
    private static int DISK_CACHE_SIZE = 20 * 1024 * 1024;
    // 指定同一个key可以对应多少个缓存文件
    private static int DISK_CACHE_COUNT = 1;


    /**
     * cacheManager 初始化
     * @param context
     * @param diskCachePath     硬盘缓存路径
     */
    public static void init(Context context, String diskCachePath) {
        // 获取剩余内存
        int memClass = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
        memoryCacheSize = 1024 * 1024 * memClass / 8;

        // 初始化字符串内存cache
        mMemoryStringCache = new LruCache<String, String>(memoryCacheSize) {
            @Override
            protected int sizeOf(String key, String string) {
                // The cache size will be measured in bytes rather than number of items.
                return string.length();
            }
        };

        // 初始化bitmap内存cache
        mMemoryBitmapCache = new LruCache<String, Bitmap>(memoryCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in bytes rather than number of items.
                return bitmap.getByteCount();
            }
        };

        // 初始化disk cache
        try {
            mDiskCache = DiskLruCache.open(
                    FileUtil.createDirectory(diskCachePath),
                    ApplicationUtil.getAppVersionCode(),
                    DISK_CACHE_COUNT,
                    DISK_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将string存储到cache
     * @param key
     * @param string
     */
    public static void addStringToCache(String key, String string) {
//        // Add to memory cache as before
//        if (getStringFromMemCache(key) == null) {
//            addStringToMemoryCache(key, string);
//        }
//        // Add to disk cache
//        if (getStringFromDiskCache(key) == null) {
//            addStringToDiskCache(key, string);
//        }
        updateStringToCache(key, string);
    }

    /**
     * 更新key对应的string存储
     * @param key
     * @param string
     */
    public static void updateStringToCache(String key, String string) {
        // Add to memory cache as before
        addStringToMemoryCache(key, string);
        // Add to disk cache
        addStringToDiskCache(key, string);
    }

    /**
     * 根据key从cache中获取string
     * @param key
     * @return
     */
    public static String getStringFromCache(String key) {
        String resultString = getStringFromMemCache(key);
        if (resultString == null) {
            resultString = getStringFromDiskCache(key);
        }
        return resultString;
    }

    /**
     * 将bitmap存储到cache
     * @param key
     * @param bitmap
     */
    public static void addBitmapToCache(String key, Bitmap bitmap) {
        // Add to memory cache as before
        if (getBitmapFromCache(key) == null) {
            addBitmapToMemoryCache(key, bitmap);
        }
        // Add to disk cache
        if (getBitmapFromDiskCache(key) == null) {
            addBitmapToDiskCache(key, bitmap);
        }
    }

    /**
     * 根据key从cache中获取bitmap
     * @param key
     * @return
     */
    public static Bitmap getBitmapFromCache(String key) {
        Bitmap resultBitmap = getBitmapFromMemCache(key);
        if (resultBitmap == null) {
            resultBitmap = getBitmapFromDiskCache(key);
        }
        return resultBitmap;
    }

    /**
     * 将string存储到内存缓存
     * @param key
     * @param string
     */
    private static void addStringToMemoryCache(String key, String string) {
        mMemoryStringCache.put(key, string);
    }

    /**
     * 根据key从内存缓存中获取string
     * @param key
     * @return
     */
    private static String getStringFromMemCache(String key) {
        return mMemoryStringCache.get(key);
    }

    /**
     * 将bitmap存储到内存缓存
     * @param key
     * @param bitmap
     */
    private static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        mMemoryBitmapCache.put(key, bitmap);
    }

    /**
     * 根据key从内存缓存中获取bitmap
     * @param key
     * @return
     */
    private static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryBitmapCache.get(key);
    }

    /**
     * 将string存储到硬盘缓存
     * @param key
     * @param string
     */
    private static void addStringToDiskCache(String key, String string) {
        OutputStream outputStream = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(0);
                outputStream.write(string.getBytes());
                editor.commit();
            }
            mDiskCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据key从硬盘缓存中获取string
     * @param key
     * @return
     */
    private static String getStringFromDiskCache(String key) {
        DiskLruCache.Snapshot snapshot = null;
        InputStream is = null;
        BufferedReader reader = null;
        try {
            snapshot = mDiskCache.get(key);

            if (snapshot != null) {
                StringBuilder result = new StringBuilder();
                is = snapshot.getInputStream(0);
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while((line= reader.readLine()) != null && line.length() > 0) {
                    result.append(line);
                }
                return result.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (snapshot != null) {
                snapshot.close();
            }
        }
        return null;
    }

    /**
     * 将bitmap存储到硬盘缓存
     * @param key
     * @param bitmap
     */
    private static void addBitmapToDiskCache(String key, Bitmap bitmap) {
        OutputStream outputStream = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(0);
                if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
            mDiskCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据key从硬盘缓存中获取bitmap
     * @param key
     * @return
     */
    private static Bitmap getBitmapFromDiskCache(String key) {
        DiskLruCache.Snapshot snapshot  = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            snapshot = mDiskCache.get(key);

            if (snapshot != null) {
                is = snapshot.getInputStream(0);
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (snapshot != null) {
                snapshot.close();
            }
        }
        return null;
    }

}
