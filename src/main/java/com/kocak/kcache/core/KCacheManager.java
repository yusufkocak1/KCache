package com.kocak.kcache.core;

import java.util.*;

public class KCacheManager {

    private static final int MAX_CACHE_SIZE = 10;
    private static final long EXPIRATION_TIME = 60000; // 60 saniye
    private static final LinkedHashMap<String, CacheItem> cache = new LinkedHashMap<>(MAX_CACHE_SIZE, 0.75f, true);

    private static int hitCount = 0;
    private static int missCount = 0;

    private static KCacheManager instance;

    private KCacheManager() {}

    public static KCacheManager getInstance() {
        if (instance == null) {
            instance = new KCacheManager();
        }
        return instance;
    }

    public void put(String key, Object value) {
        if (cache.size() >= MAX_CACHE_SIZE) {
            Iterator<Map.Entry<String, CacheItem>> iterator = cache.entrySet().iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
        CacheItem cacheItem = new CacheItem(value, System.currentTimeMillis());
        cache.put(key, cacheItem);
    }

    public Object get(String key) {
        CacheItem cacheItem = cache.get(key);
        if (cacheItem != null && !isExpired(cacheItem)) {
            hitCount++;
            return cacheItem.getValue();
        }
        missCount++;
        evict(key);
        return null;
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key) && !isExpired(cache.get(key));
    }

    public void evict(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public Map<String, Object> getCache() {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, CacheItem> entry : cache.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getValue());
        }
        return result;
    }

    public String getStatistics() {
        return "Hits: " + hitCount + ", Misses: " + missCount;
    }

    private boolean isExpired(CacheItem cacheItem) {
        return (System.currentTimeMillis() - cacheItem.getTimestamp()) > EXPIRATION_TIME;
    }

    private static class CacheItem {
        private final Object value;
        private final long timestamp;

        public CacheItem(Object value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public Object getValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
