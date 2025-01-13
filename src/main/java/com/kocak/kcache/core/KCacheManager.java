package com.kocak.kcache.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KCacheManager {

    private static final int MAX_CACHE_SIZE = 10;
    private static final long EXPIRATION_TIME = 60000; // 60 saniye
    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();

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
    public void put(String key, Object value, long expireAfter, int expireAfterAccessCount) {
        CacheEntry entry = new CacheEntry(value, expireAfter, expireAfterAccessCount);
        cache.put(key, entry);
    }
    public void put(String key, Object value) {
        CacheEntry entry = new CacheEntry(value, -1, -1);
        cache.put(key, entry);
    }
    public Object get(String key) {
        CacheEntry entry = cache.get(key);
        if (entry == null || entry.isExpired()) {
            cache.remove(key);
            missCount++;
            return null;
        }
        entry.incrementAccessCount();
        if (entry.isAccessLimitReached()) {
            cache.remove(key);
            missCount++;
            return null;
        }

        hitCount++;
        return entry.getValue();
    }
    public CacheEntry getEntry(String key) {
        return cache.get(key);
    }


    public boolean containsKey(String key) {
        return cache.containsKey(key) && !cache.get(key).isExpired();
    }

    public void evict(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public Map<String, Object> getCache() {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, CacheEntry> entry : cache.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getValue().toString());
        }
        return result;
    }

    public String getStatistics() {
        return "Hits: " + hitCount + ", Misses: " + missCount;
    }

}
