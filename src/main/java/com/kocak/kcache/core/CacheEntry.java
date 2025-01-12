package com.kocak.kcache.core;
public class CacheEntry {
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
        if (expireAfter == -1) return false;
        return System.currentTimeMillis() - creationTime > expireAfter;
    }

    public boolean isAccessLimitReached() {
        if (accessLimit == -1) return false;
        return accessCount >= accessLimit;
    }

    public void incrementAccessCount() {
        accessCount++;
    }

    public Object getValue() {
        return value;
    }

    public long getExpireAfter() {
        return expireAfter;
    }

    public int getAccessLimit() {
        return accessLimit;
    }
}