package com.kocak.kcache.core;

import java.util.concurrent.ConcurrentHashMap;

public class CacheStore {
    private final ConcurrentHashMap<String, CacheEntry> cacheMap = new ConcurrentHashMap<>();

    public void put(String key, Object value, long expireAfter, int expireAfterAccessCount) {
        CacheEntry entry = new CacheEntry(value, expireAfter, expireAfterAccessCount);
        cacheMap.put(key, entry);
    }

    public Object get(String key) {
        CacheEntry entry = cacheMap.get(key);
        if (entry == null || entry.isExpired()) {
            cacheMap.remove(key);
            return null;
        }
        entry.incrementAccessCount();
        if (entry.isAccessLimitReached()) {
            cacheMap.remove(key);
            return null;
        }
        return entry.getValue();
    }

    private static class CacheEntry {
        private final Object value;
        private final long expireAfter;
        private final int accessLimit;
        private final long creationTime;
        private int accessCount;

        public CacheEntry(Object value, long expireAfter, int accessLimit) {
            this.value = value;
            this.expireAfter = expireAfter;
            this.accessLimit = accessLimit;
            this.creationTime = System.currentTimeMillis();
            this.accessCount = 0;
        }

        public boolean isExpired() {
            return expireAfter > 0 && (System.currentTimeMillis() - creationTime) >= expireAfter;
        }

        public boolean isAccessLimitReached() {
            return accessLimit > 0 && accessCount >= accessLimit;
        }

        public void incrementAccessCount() {
            accessCount++;
        }

        public Object getValue() {
            return value;
        }
    }
}
