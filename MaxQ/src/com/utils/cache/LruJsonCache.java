package com.utils.cache;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.LruCache;

@SuppressLint("NewApi")
public class LruJsonCache {
	private LruCache<String, String> mMemoryCache;
	private static LruJsonCache instance;

	/**
	 * 效率高，线程安全
	 * @return
	 */
	public static LruJsonCache getIstance() {
		if (instance == null) {
			synchronized (LruJsonCache.class) {
					instance=new LruJsonCache();
			}
		}
		return instance;

	}

	public LruJsonCache() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory() / 10;
		mMemoryCache = new LruCache<String, String>(maxMemory) {
			@Override
			protected int sizeOf(String key, String value) {
				return value.length();
			}
		};
	}

	/**
	 * 
	 * @Title: addJsonToMemoryCache
	 * @Description: TODO 添加json内存
	 * @return void
	 */
	public void addJsonToMemoryCache(String key, String jsonString) {
		if (mMemoryCache == null) {
			return;
		}
		if (TextUtils.isEmpty(key)) {
			return;
		}

		if (getJsonFromMemCache(key) == null && jsonString != null) {
			mMemoryCache.put(key, jsonString);
		}
	}

	/**
	 * 从内存缓存中获取一个Json
	 * 
	 * @param key
	 * @return
	 */
	public String getJsonFromMemCache(String key) {
		if (mMemoryCache == null) {
			return null;
		}
		return mMemoryCache.get(key);
	}
}
